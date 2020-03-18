package com.zjmzxfzhl.modules.flowable.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.ObjectUtils;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.flowable.common.CommentTypeEnum;
import com.zjmzxfzhl.modules.flowable.common.ResponseFactory;
import com.zjmzxfzhl.modules.flowable.common.cmd.BackUserTaskCmd;
import com.zjmzxfzhl.modules.flowable.common.exception.FlowableNoPermissionException;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.util.FlowableUtils;
import com.zjmzxfzhl.modules.flowable.vo.FlowNodeResponse;
import com.zjmzxfzhl.modules.flowable.vo.IdentityRequest;
import com.zjmzxfzhl.modules.flowable.vo.TaskRequest;
import com.zjmzxfzhl.modules.flowable.vo.TaskResponse;
import com.zjmzxfzhl.modules.flowable.vo.TaskUpdateRequest;

@Service
@Transactional
public class FlowableTaskService {
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected PermissionService permissionService;
	@Autowired
	protected ResponseFactory responseFactory;
	@Autowired
	protected ManagementService managementService;
	@Autowired
	protected FormService formService;

	public TaskResponse getTask(String taskId) {
		String userId = ShiroUtils.getUserId();
		HistoricTaskInstance taskHis = permissionService.validateReadPermissionOnTask(taskId, userId, true, true);
		TaskResponse rep = null;
		ProcessDefinition processDefinition = null;
		String formKey = null;
		Object renderedTaskForm = null;
		HistoricTaskInstance parentTask = null;
		if (StringUtils.isNotEmpty(taskHis.getProcessDefinitionId())) {
			processDefinition = repositoryService.getProcessDefinition(taskHis.getProcessDefinitionId());
			formKey = formService.getTaskFormKey(processDefinition.getId(), taskHis.getTaskDefinitionKey());
			if (taskHis.getEndTime() == null && formKey != null && formKey.length() > 0) {
				renderedTaskForm = formService.getRenderedTaskForm(taskId);
			}
		}
		if (StringUtils.isNotEmpty(taskHis.getParentTaskId())) {
			parentTask = historyService.createHistoricTaskInstanceQuery().taskId(taskHis.getParentTaskId()).singleResult();
		}
		rep = new TaskResponse(taskHis, processDefinition, parentTask, null);
		rep.setFormKey(formKey);
		rep.setRenderedTaskForm(renderedTaskForm);

		fillPermissionInformation(rep, taskHis, userId);
		// Populate the people
		populateAssignee(taskHis, rep);
		rep.setInvolvedPeople(getInvolvedUsers(taskId));

		Task task = null;
		if (taskHis.getEndTime() == null) {
			task = taskService.createTaskQuery().taskId(taskId).singleResult();
			rep.setSuspended(task.isSuspended());
			rep.setDelegationState(task.getDelegationState());
		}
		rep.setOwnerName(this.getUserName(taskHis.getOwner()));
		rep.setAssigneeName(this.getUserName(taskHis.getAssignee()));
		return rep;
	}

	public List<TaskResponse> getSubTasks(String taskId) {
		String userId = ShiroUtils.getUserId();
		HistoricTaskInstance parentTask = permissionService.validateReadPermissionOnTask(taskId, userId, true, true);
		List<Task> subTasks = this.taskService.getSubTasks(taskId);
		List<TaskResponse> subTasksRepresentations = new ArrayList<>(subTasks.size());
		for (Task subTask : subTasks) {
			TaskResponse representation = new TaskResponse(subTask, parentTask);
			fillPermissionInformation(representation, subTask, userId);
			populateAssignee(subTask, representation);
			representation.setInvolvedPeople(getInvolvedUsers(subTask.getId()));
			subTasksRepresentations.add(representation);
		}
		return subTasksRepresentations;
	}

	@Transactional
	public TaskResponse updateTask(TaskUpdateRequest taskUpdateRequest) {
		String userId = ShiroUtils.getUserId();
		permissionService.validateReadPermissionOnTask(taskUpdateRequest.getId(), userId, false, false);
		Task task = getTaskNotNull(taskUpdateRequest.getId());
		task.setName(taskUpdateRequest.getName());
		task.setDescription(taskUpdateRequest.getDescription());
		task.setAssignee(taskUpdateRequest.getAssignee());
		task.setOwner(taskUpdateRequest.getOwner());
		task.setDueDate(taskUpdateRequest.getDueDate());
		task.setPriority(taskUpdateRequest.getPriority());
		task.setCategory(taskUpdateRequest.getCategory());
		taskService.saveTask(task);
		return new TaskResponse(task);
	}

	@Transactional
	public void assignTask(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String assignee = taskRequest.getUserId();
		String userId = ShiroUtils.getUserId();
		Task task = permissionService.validateAssignPermissionOnTask(taskId, userId, assignee);
		this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.ZB, taskRequest.getMessage());
		taskService.setAssignee(task.getId(), assignee);
		// 暂时转办人员不作为参与者
		// String oldAssignee = task.getAssignee();
		// // If the old assignee user wasn't part of the involved users yet, make it so
		// addIdentiyLinkForUser(task, oldAssignee, IdentityLinkType.PARTICIPANT);
		// // If the current user wasn't part of the involved users yet, make it so
		// addIdentiyLinkForUser(task, userId, IdentityLinkType.PARTICIPANT);
	}

	@Transactional
	public void involveUser(String taskId, String involveUserId) {
		Task task = getTaskNotNull(taskId);
		String userId = ShiroUtils.getUserId();
		permissionService.validateReadPermissionOnTask(task.getId(), userId, false, false);
		if (involveUserId != null && involveUserId.length() > 0) {
			taskService.addUserIdentityLink(taskId, involveUserId, IdentityLinkType.PARTICIPANT);
		} else {
			throw new FlowableException("User id is required");
		}
	}

	@Transactional
	public void removeInvolvedUser(String taskId, String involveUserId) {
		Task task = getTaskNotNull(taskId);
		String userId = ShiroUtils.getUserId();
		permissionService.validateReadPermissionOnTask(task.getId(), userId, false, false);
		if (involveUserId != null && involveUserId.length() > 0) {
			taskService.deleteUserIdentityLink(taskId, involveUserId, IdentityLinkType.PARTICIPANT);
		} else {
			throw new FlowableException("User id is required");
		}
	}

	@Transactional
	public void claimTask(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String userId = ShiroUtils.getUserId();
		TaskInfo task = permissionService.validateReadPermissionOnTask2(taskId, userId, false, false);
		if (task.getAssignee() != null && task.getAssignee().length() > 0) {
			throw new FlowableNoPermissionException("User does not have permission");
		}
		this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.RL, taskRequest.getMessage());
		taskService.claim(taskId, userId);
	}

	@Transactional
	public void unclaimTask(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String userId = ShiroUtils.getUserId();
		TaskInfo task = this.getTaskNotNull(taskId);
		if (!userId.equals(task.getAssignee())) {
			throw new FlowableNoPermissionException("User does not have permission");
		}
		if (FlowableConstant.__INITIATOR__.equals(task.getTaskDefinitionKey())) {
			throw new FlowableNoPermissionException("Initiator cannot unclaim the task");
		}

		this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.QXRL, taskRequest.getMessage());
		taskService.unclaim(taskId);
	}

	@Transactional
	protected void addIdentiyLinkForUser(Task task, String userId, String linkType) {
		List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
		boolean isOldUserInvolved = false;
		for (IdentityLink identityLink : identityLinks) {
			if (userId.equals(identityLink.getUserId())
					&& (identityLink.getType().equals(IdentityLinkType.PARTICIPANT) || identityLink.getType().equals(IdentityLinkType.CANDIDATE))) {
				isOldUserInvolved = true;
			}
		}
		if (!isOldUserInvolved) {
			taskService.addUserIdentityLink(task.getId(), userId, linkType);
		}
	}

	@Transactional
	public void delegateTask(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String delegater = taskRequest.getUserId();
		String userId = ShiroUtils.getUserId();
		Task task = permissionService.validateDelegatePermissionOnTask(taskId, userId, delegater);
		this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.WP, taskRequest.getMessage());
		taskService.delegateTask(task.getId(), delegater);
		// 暂时委派人员不作为参与者
		// String oldAssignee = task.getAssignee();
		// // If the old assignee user wasn't part of the involved users yet, make it so
		// addIdentiyLinkForUser(task, oldAssignee, IdentityLinkType.PARTICIPANT);
		// // If the current user wasn't part of the involved users yet, make it so
		// addIdentiyLinkForUser(task, userId, IdentityLinkType.PARTICIPANT);
	}

	@Transactional
	public void completeTask(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String currUserId = ShiroUtils.getUserId();
		Task task = getTaskNotNull(taskId);
		if (!permissionService.isTaskOwnerOrAssignee(currUserId, task)) {
			if (StringUtils.isEmpty(task.getScopeType()) && !permissionService.validateIfUserIsInitiatorAndCanCompleteTask(currUserId, task)) {
				throw new FlowableNoPermissionException("User does not have permission");
			}
		}

		this.addComment(taskId, task.getProcessInstanceId(), currUserId, taskRequest.isInitiator() ? CommentTypeEnum.CXTJ : CommentTypeEnum.WC,
				taskRequest.getMessage());

		Map<String, Object> completeVariables = null;
		if (taskRequest.getValues() != null && !taskRequest.getValues().isEmpty()) {
			completeVariables = taskRequest.getValues();
		}
		if (task.getAssignee() == null || !task.getAssignee().equals(currUserId)) {
			taskService.setAssignee(taskId, currUserId);
		}
		// 判断是否是协办完成还是正常流转
		if (permissionService.isTaskPending(task)) {
			taskService.resolveTask(taskId, completeVariables);
			if (currUserId.equals(task.getOwner())) {// 如果当前执行人是任务所有人，直接完成任务
				taskService.complete(taskId, completeVariables);
			}
		} else {
			taskService.complete(taskId, completeVariables);
		}
	}

	@Transactional
	public void deleteTask(String taskId) {
		HistoricTaskInstance task = this.getHistoricTaskInstanceNotNull(taskId);
		if (task.getEndTime() == null) {
			throw new FlowableException("Cannot delete task that is running");
		}
		historyService.deleteHistoricTaskInstance(task.getId());
	}

	@Transactional
	public void stopProcessInstance(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String userId = ShiroUtils.getUserId();
		ProcessInstance processInstance = permissionService.validateStopProcessInstancePermissionOnTask(taskId, userId);
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		if (bpmnModel != null) {
			Process process = bpmnModel.getMainProcess();
			List<EndEvent> endNodes = process.findFlowElementsOfType(EndEvent.class, false);
			if (endNodes != null && endNodes.size() > 0) {
				this.addComment(taskId, processInstance.getProcessInstanceId(), userId, CommentTypeEnum.ZZ, taskRequest.getMessage());
				String endId = endNodes.get(0).getId();
				List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstance.getProcessInstanceId()).list();
				List<String> executionIds = new ArrayList<>();
				executions.forEach(execution -> executionIds.add(execution.getId()));
				runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, endId).changeState();
			}
		}
	}

	public List<FlowNodeResponse> getBackNodes(String taskId) {
		TaskEntity taskEntity = (TaskEntity) permissionService.validateExcutePermissionOnTask(taskId, ShiroUtils.getUserId());
		String processInstanceId = taskEntity.getProcessInstanceId();
		String currActId = taskEntity.getTaskDefinitionKey();
		String processDefinitionId = taskEntity.getProcessDefinitionId();
		Process process = repositoryService.getBpmnModel(processDefinitionId).getMainProcess();
		FlowNode currentFlowElement = (FlowNode) process.getFlowElement(currActId, true);
		List<ActivityInstance> activitys = runtimeService.createActivityInstanceQuery().processInstanceId(processInstanceId).finished()
				.orderByActivityInstanceStartTime().asc().list();
		List<String> activityIds = activitys.stream().filter(activity -> activity.getActivityType().equals(BpmnXMLConstants.ELEMENT_TASK_USER))
				.filter(activity -> !activity.getActivityId().equals(currActId)).map(ActivityInstance::getActivityId).distinct()
				.collect(Collectors.toList());
		List<FlowNodeResponse> result = new ArrayList<>();
		for (String activityId : activityIds) {
			FlowNode toBackFlowElement = (FlowNode) process.getFlowElement(activityId, true);
			if (FlowableUtils.isReachable(process, toBackFlowElement, currentFlowElement)) {
				FlowNodeResponse vo = new FlowNodeResponse();
				vo.setNodeId(activityId);
				vo.setNodeName(toBackFlowElement.getName());
				result.add(vo);
			}
		}
		return result;
	}

	@Transactional
	public void backTask(TaskRequest taskRequest) {
		String taskId = taskRequest.getTaskId();
		String userId = ShiroUtils.getUserId();
		Task task = permissionService.validateExcutePermissionOnTask(taskId, userId);
		String backSysMessage = "退回到" + taskRequest.getActivityName() + "。";
		this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.TH, backSysMessage + taskRequest.getMessage());
		String targetRealActivityId = managementService
				.executeCommand(new BackUserTaskCmd(runtimeService, taskRequest.getTaskId(), taskRequest.getActivityId()));
		// 退回发起者处理
		if (FlowableConstant.__INITIATOR__.equals(targetRealActivityId)) {// 退回到发起者,默认设置任务执行人为发起者
			String __initiator__ = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult()
					.getStartUserId();
			List<Task> newTasks = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
			for (Task newTask : newTasks) {
				if (FlowableConstant.__INITIATOR__.equals(newTask.getTaskDefinitionKey())) {// 约定：发起者节点为 __initiator__
					if (ObjectUtils.isEmpty(newTask.getAssignee())) {
						taskService.setAssignee(newTask.getId(), __initiator__);
					}
				}
			}
		}
	}

	private void fillPermissionInformation(TaskResponse taskResponse, TaskInfo task, String userId) {
		verifyProcessInstanceStartUser(taskResponse, task);
		List<HistoricIdentityLink> taskIdentityLinks = historyService.getHistoricIdentityLinksForTask(task.getId());
		verifyCandidateGroups(taskResponse, userId, taskIdentityLinks);
		verifyCandidateUsers(taskResponse, userId, taskIdentityLinks);
	}

	private void verifyProcessInstanceStartUser(TaskResponse taskResponse, TaskInfo task) {
		if (task.getProcessInstanceId() != null) {
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).singleResult();
			if (historicProcessInstance != null && StringUtils.isNotEmpty(historicProcessInstance.getStartUserId())) {
				taskResponse.setProcessInstanceStartUserId(historicProcessInstance.getStartUserId());
				BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
				FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
				if (flowElement instanceof UserTask) {
					UserTask userTask = (UserTask) flowElement;
					List<ExtensionElement> extensionElements = userTask.getExtensionElements().get("initiator-can-complete");
					if (CollectionUtils.isNotEmpty(extensionElements)) {
						String value = extensionElements.get(0).getElementText();
						if (StringUtils.isNotEmpty(value)) {
							taskResponse.setInitiatorCanCompleteTask(value);
						}
					}
				}
			}
		}
	}

	private void verifyCandidateGroups(TaskResponse taskResponse, String userId, List<HistoricIdentityLink> taskIdentityLinks) {
		List<Group> userGroups = identityService.createGroupQuery().groupMember(userId).list();
		taskResponse.setMemberOfCandidateGroup(String.valueOf(userGroupsMatchTaskCandidateGroups(userGroups, taskIdentityLinks)));
	}

	private boolean userGroupsMatchTaskCandidateGroups(List<Group> userGroups, List<HistoricIdentityLink> taskIdentityLinks) {
		for (Group group : userGroups) {
			for (HistoricIdentityLink identityLink : taskIdentityLinks) {
				if (identityLink.getGroupId() != null && identityLink.getType().equals(IdentityLinkType.CANDIDATE)
						&& group.getId().equals(identityLink.getGroupId())) {
					return true;
				}
			}
		}
		return false;
	}

	private void verifyCandidateUsers(TaskResponse taskResponse, String userId, List<HistoricIdentityLink> taskIdentityLinks) {
		taskResponse.setMemberOfCandidateUsers(String.valueOf(currentUserMatchesTaskCandidateUsers(userId, taskIdentityLinks)));
	}

	private boolean currentUserMatchesTaskCandidateUsers(String userId, List<HistoricIdentityLink> taskIdentityLinks) {
		for (HistoricIdentityLink identityLink : taskIdentityLinks) {
			if (identityLink.getUserId() != null && identityLink.getType().equals(IdentityLinkType.CANDIDATE)
					&& identityLink.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}

	private String getUserName(String userId) {
		if (CommonUtil.isEmptyStr(userId)) {
			return null;
		}
		User user = identityService.createUserQuery().userId(userId).singleResult();
		if (user != null) {
			return user.getFirstName();
		}
		return null;
	}

	private List<String> getInvolvedUsers(String taskId) {
		List<HistoricIdentityLink> idLinks = historyService.getHistoricIdentityLinksForTask(taskId);
		List<String> result = new ArrayList<>(idLinks.size());

		for (HistoricIdentityLink link : idLinks) {
			// Only include users and non-assignee links
			if (link.getUserId() != null && !IdentityLinkType.ASSIGNEE.equals(link.getType())) {
				result.add(link.getUserId());
			}
		}
		return result;
	}

	private void populateAssignee(TaskInfo task, TaskResponse rep) {
		if (task.getAssignee() != null) {
			rep.setAssignee(task.getAssignee());
		}
	}

	public Task getTaskNotNull(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new FlowableObjectNotFoundException("Task with id: " + taskId + " does not exist");
		}
		return task;
	}

	public HistoricTaskInstance getHistoricTaskInstanceNotNull(String taskId) {
		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new FlowableObjectNotFoundException("Task with id: " + taskId + " does not exist");
		}
		return task;
	}

	public void addComment(String taskId, String processInstanceId, String userId, CommentTypeEnum type, String message) {
		Authentication.setAuthenticatedUserId(userId);
		type = type == null ? CommentTypeEnum.SP : type;
		message = (message == null || message.length() == 0) ? type.getName() : message;
		taskService.addComment(taskId, processInstanceId, type.toString(), message);
	}

	public List<Comment> getComments(String taskId, String processInstanceId, String type, String userId) {
		List<Comment> comments = null;
		if (type == null || type.length() == 0) {
			if (taskId != null && taskId.length() > 0) { // 以taskId为优先
				comments = taskService.getTaskComments(taskId);
			} else if (processInstanceId != null && processInstanceId.length() > 0) {
				comments = taskService.getProcessInstanceComments(processInstanceId);
			} else {
				throw new FlowableIllegalArgumentException("taskId processInstanceId type are all empty");
			}
		} else {
			if (taskId != null && taskId.length() > 0) {// 以taskId为优先
				comments = taskService.getTaskComments(taskId, type);
			} else if (processInstanceId != null && processInstanceId.length() > 0) {
				comments = taskService.getProcessInstanceComments(processInstanceId, type);
			} else {
				comments = taskService.getCommentsByType(type);
			}
		}
		if (userId != null && userId.length() > 0 && comments != null && comments.size() > 0) {
			comments = comments.stream().filter(comment -> userId.equals(comment.getUserId())).collect(Collectors.toList());
		}
		return comments;
	}

	private void validateIdentityLinkArguments(String identityId, String identityType) {
		if (identityId == null || identityId.length() == 0) {
			throw new FlowableIllegalArgumentException("identityId is null");
		}
		if (!FlowableConstant.IDENTITY_GROUP.equals(identityType) && !FlowableConstant.IDENTITY_USER.equals(identityType)) {
			throw new FlowableIllegalArgumentException("type must be group or user");
		}
	}

	@Transactional
	public void saveTaskIdentityLink(IdentityRequest taskIdentityRequest) {
		Task task = getTaskNotNull(taskIdentityRequest.getTaskId());
		validateIdentityLinkArguments(taskIdentityRequest.getIdentityId(), taskIdentityRequest.getIdentityType());
		if (FlowableConstant.IDENTITY_GROUP.equals(taskIdentityRequest.getIdentityType())) {
			taskService.addGroupIdentityLink(task.getId(), taskIdentityRequest.getIdentityId(), IdentityLinkType.CANDIDATE);
		} else if (FlowableConstant.IDENTITY_USER.equals(taskIdentityRequest.getIdentityType())) {
			taskService.addUserIdentityLink(task.getId(), taskIdentityRequest.getIdentityId(), IdentityLinkType.CANDIDATE);
		}
	}

	@Transactional
	public void deleteTaskIdentityLink(String taskId, String identityId, String identityType) {
		Task task = getTaskNotNull(taskId);
		validateIdentityLinkArguments(identityId, identityType);
		if (FlowableConstant.IDENTITY_GROUP.equals(identityType)) {
			taskService.deleteGroupIdentityLink(task.getId(), identityId, IdentityLinkType.CANDIDATE);
		} else if (FlowableConstant.IDENTITY_USER.equals(identityType)) {
			taskService.deleteUserIdentityLink(task.getId(), identityId, IdentityLinkType.CANDIDATE);
		}
	}
}