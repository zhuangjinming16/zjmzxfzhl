package com.zjmzxfzhl.modules.flowable.common.cmd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import com.google.common.collect.Sets;
import com.zjmzxfzhl.modules.flowable.util.FlowableUtils;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public class BackUserTaskCmd implements Command<String>, Serializable {

	public static final long serialVersionUID = 1L;

	protected RuntimeService runtimeService;
	protected String taskId;
	protected String targetActivityId;

	public BackUserTaskCmd(RuntimeService runtimeService, String taskId, String targetActivityId) {
		this.runtimeService = runtimeService;
		this.taskId = taskId;
		this.targetActivityId = targetActivityId;
	}

	@Override
	public String execute(CommandContext commandContext) {
		if (targetActivityId == null || targetActivityId.length() == 0) {
			throw new FlowableException("TargetActivityId cannot be empty");
		}
		TaskEntity task = CommandContextUtil.getTaskService().getTask(taskId);
		if (task == null) {
			throw new FlowableObjectNotFoundException("task " + taskId + " doesn't exist", Task.class);
		}
		String sourceActivityId = task.getTaskDefinitionKey();
		String processInstanceId = task.getProcessInstanceId();
		String processDefinitionId = task.getProcessDefinitionId();

		Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);

		FlowNode sourceFlowElement = (FlowNode) process.getFlowElement(sourceActivityId, true);
		// 只支持从用户任务退回
		if (!(sourceFlowElement instanceof UserTask)) {
			throw new FlowableException("Task with id:" + taskId + " is not a UserTask");
		}

		FlowNode targetFlowElement = (FlowNode) process.getFlowElement(targetActivityId, true);
		// 退回节点到当前节点不可达到，不允许退回
		if (!FlowableUtils.isReachable(process, targetFlowElement, sourceFlowElement)) {
			throw new FlowableException("Cannot back to [" + targetActivityId + "]");
		}

		String[] sourceAndTargetRealActivityId = FlowableUtils.getSourceAndTargetRealActivityId(sourceFlowElement, targetFlowElement);
		// 实际应操作的当前节点ID
		String sourceRealActivityId = sourceAndTargetRealActivityId[0];
		// 实际应操作的目标节点ID
		String targetRealActivityId = sourceAndTargetRealActivityId[1];

		Map<String, Set<String>> specialGatewayNodes = FlowableUtils.getSpecialGatewayElements(process);
		// 当前节点处在的并行网关list
		List<String> sourceInSpecialGatewayList = new ArrayList<>();
		// 目标节点处在的并行网关list
		List<String> targetInSpecialGatewayList = new ArrayList<>();
		setSpecialGatewayList(sourceRealActivityId, targetRealActivityId, specialGatewayNodes, sourceInSpecialGatewayList,
				targetInSpecialGatewayList);

		// 实际应筛选的节点ID
		Set<String> sourceRealAcitivtyIds = null;
		// 若退回目标节点相对当前节点在并行网关中，则应退回到并行网关起点
		String targetRealSpecialGateway = null;

		// 1.目标节点和当前节点都不在并行网关中
		if (targetInSpecialGatewayList.isEmpty() && sourceInSpecialGatewayList.isEmpty()) {
			sourceRealAcitivtyIds = Sets.newHashSet(sourceRealActivityId);
		}
		// 2.目标节点不在并行网关中、当前节点在并行网关中
		else if (targetInSpecialGatewayList.isEmpty() && !sourceInSpecialGatewayList.isEmpty()) {
			sourceRealAcitivtyIds = specialGatewayNodes.get(sourceInSpecialGatewayList.get(0));
		}
		// 3.目标节点在并行网关中、当前节点不在并行网关中
		else if (!targetInSpecialGatewayList.isEmpty() && sourceInSpecialGatewayList.isEmpty()) {
			sourceRealAcitivtyIds = Sets.newHashSet(sourceRealActivityId);
			targetRealSpecialGateway = targetInSpecialGatewayList.get(0);
		}
		// 4.目标节点和当前节点都在并行网关中
		else {
			int diffSpecialGatewayLevel = FlowableUtils.getDiffLevel(sourceInSpecialGatewayList, targetInSpecialGatewayList);
			// 在并行网关同一层且在同一分支
			if (diffSpecialGatewayLevel == -1) {
				sourceRealAcitivtyIds = Sets.newHashSet(sourceRealActivityId);
			} else {
				// 当前节点最内层并行网关不被目标节点最内层并行网关包含
				// 或理解为当前节点相对目标节点在并行网关外
				// 只筛选当前节点的execution
				if (sourceInSpecialGatewayList.size() == diffSpecialGatewayLevel) {
					sourceRealAcitivtyIds = Sets.newHashSet(sourceRealActivityId);
				}
				// 当前节点相对目标节点在并行网关内，应筛选相对目标节点最近的并行网关的所有节点的execution
				else {
					sourceRealAcitivtyIds = specialGatewayNodes.get(sourceInSpecialGatewayList.get(diffSpecialGatewayLevel));
				}
				// 目标节点最内层并行网关包含当前节点最内层并行网关
				// 或理解为目标节点相对当前节点在并行网关外
				// 不做处理
				if (targetInSpecialGatewayList.size() == diffSpecialGatewayLevel) {
				}
				// 目标节点相对当前节点在并行网关内，则应退回到相对当前节点最近的并行网关的begin节点
				else {
					targetRealSpecialGateway = targetInSpecialGatewayList.get(diffSpecialGatewayLevel);
				}
			}
		}

		// 筛选需要处理的execution
		List<ExecutionEntity> realExecutions = this.getRealExecutions(commandContext, processInstanceId, task.getExecutionId(), sourceRealActivityId,
				sourceRealAcitivtyIds);
		// 不需要跳转到并行网关begin节点，直接跳转到实际的 targetRealActivityId
		if (targetRealSpecialGateway == null) {
			List<String> realExecutionIds = realExecutions.stream().map(ExecutionEntity::getId).collect(Collectors.toList());
			runtimeService.createChangeActivityStateBuilder().processInstanceId(processInstanceId)
					.moveExecutionsToSingleActivityId(realExecutionIds, targetRealActivityId).changeState();
		} else {// 需要跳转到并行网关begin节点
			moveToActivityInSpecialGateway(commandContext, processInstanceId, realExecutions, targetRealSpecialGateway);
		}

		return targetRealActivityId;
	}

	private void setSpecialGatewayList(String sourceActivityId, String targetActivityId, Map<String, Set<String>> specialGatewayNodes,
			List<String> sourceInSpecialGatewayList, List<String> targetInSpecialGatewayList) {
		for (Map.Entry<String, Set<String>> entry : specialGatewayNodes.entrySet()) {
			if (entry.getValue().contains(sourceActivityId)) {
				sourceInSpecialGatewayList.add(entry.getKey());
			}
			if (entry.getValue().contains(targetActivityId)) {
				targetInSpecialGatewayList.add(entry.getKey());
			}
		}
	}

	private void moveToActivityInSpecialGateway(CommandContext commandContext, String processInstanceId, List<ExecutionEntity> excutionEntitys,
			String targetSpecialGateway) {
		if (excutionEntitys != null && excutionEntitys.size() > 0) {
			String firstExecutionId = excutionEntitys.iterator().next().getId();
			// 跳过第一个，手动删除其余多余excution，因为跳到并行网关的起点只需要一个excution，否则多个excution跳回这个发散并行网关节点会出现多条记录
			// 原因可以查看6.4.1版本源码 AbstractDynamicStateManager.createEmbeddedSubProcessAndExecutions 619行
			// 对网关做了特殊处理，但貌似是为了支持跳转到汇聚并行网关而处理的
			if (excutionEntitys.size() > 1) {
				excutionEntitys.stream().skip(1).forEach(e -> this.deleteExecution(commandContext, e, targetActivityId));
			}
			runtimeService.createChangeActivityStateBuilder().processInstanceId(processInstanceId)
					.moveExecutionsToSingleActivityId(Arrays.asList(firstExecutionId), targetSpecialGateway + "_begin").changeState();
		}
	}

	private void deleteExecution(CommandContext commandContext, ExecutionEntity executionEntity, String targetActivityId) {
		ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
		executionEntityManager.deleteChildExecutions(executionEntity, null, null, "Change activity to " + targetActivityId, true, null);
		executionEntityManager.deleteExecutionAndRelatedData(executionEntity, "Change activity to " + targetActivityId, false, true,
				executionEntity.getCurrentFlowElement());
	}

	private List<ExecutionEntity> getRealExecutions(CommandContext commandContext, String processInstanceId, String taskExecutionId,
			String sourceRealActivityId, Set<String> activityIds) {
		ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
		ExecutionEntity taskExecution = executionEntityManager.findById(taskExecutionId);
		List<ExecutionEntity> executions = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
		Set<String> parentExecutionIds = FlowableUtils.getParentExecutionIdsByActivityId(executions, sourceRealActivityId);
		String realParentExecutionId = FlowableUtils.getParentExecutionIdFromParentIds(taskExecution, parentExecutionIds);

		List<ExecutionEntity> childExecutions = executionEntityManager.findExecutionsByParentExecutionAndActivityIds(realParentExecutionId,
				activityIds);
		return childExecutions;
	}
}