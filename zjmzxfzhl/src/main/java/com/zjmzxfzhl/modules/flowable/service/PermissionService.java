package com.zjmzxfzhl.modules.flowable.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.idm.api.Group;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.flowable.common.cmd.GetProcessDefinitionInfoCmd;
import com.zjmzxfzhl.modules.flowable.common.exception.FlowableNoPermissionException;

@Service
@Transactional
public class PermissionService {
	@Autowired
	private IdentityService identityService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected ManagementService managementService;

	/**
	 * Check if the given user is allowed to read the task.
	 */
	public HistoricTaskInstance validateReadPermissionOnTask(String taskId, String userId, boolean validateReadProcessInstance,
			boolean validateReadParentTask) {
		if (isAdmin(userId)) {
			return historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		}
		HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().taskId(taskId);
		List<String> groupIds = getGroupIdsForUser(userId);
		if (!groupIds.isEmpty()) {
			taskQuery.or().taskInvolvedUser(userId).taskCandidateGroupIn(groupIds).endOr();
		} else {
			taskQuery.taskInvolvedUser(userId);
		}
		HistoricTaskInstance task = taskQuery.singleResult();
		if (task != null) {
			return task;
		}
		// Last resort: user has access to process instance or parent task -> can see task
		task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		if (task != null) {
			if (validateReadProcessInstance && task.getProcessInstanceId() != null && task.getProcessInstanceId().length() > 0) {
				boolean hasReadPermissionOnProcessInstance = hasReadPermissionOnProcessInstance(userId, task.getProcessInstanceId());
				if (hasReadPermissionOnProcessInstance) {
					return task;
				}
			}
			if (validateReadParentTask && task.getParentTaskId() != null && task.getParentTaskId().length() > 0) {
				validateReadPermissionOnTask(task.getParentTaskId(), userId, validateReadProcessInstance, validateReadParentTask);
				return task;
			}
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	public Task validateReadPermissionOnTask2(String taskId, String userId, boolean validateReadProcessInstance, boolean validateReadParentTask) {
		if (isAdmin(userId)) {
			return taskService.createTaskQuery().taskId(taskId).singleResult();
		}

		Task task = taskService.createTaskQuery().taskId(taskId).or().taskCandidateOrAssigned(userId).taskOwner(userId).endOr().singleResult();
		if (task != null) {
			return task;
		}

		// Last resort: user has access to process instance or parent task -> can see task
		task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task != null) {
			if (validateReadProcessInstance && task.getProcessInstanceId() != null && task.getProcessInstanceId().length() > 0) {
				boolean hasReadPermissionOnProcessInstance = hasReadPermissionOnProcessInstance(userId, task.getProcessInstanceId());
				if (hasReadPermissionOnProcessInstance) {
					return task;
				}

			}
			if (validateReadParentTask && task.getParentTaskId() != null && task.getParentTaskId().length() > 0) {
				validateReadPermissionOnTask2(task.getParentTaskId(), userId, validateReadProcessInstance, validateReadParentTask);
				return task;
			}
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	private List<String> getGroupIdsForUser(String userId) {
		List<String> groupIds = new ArrayList<>();

		List<Group> userGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : userGroups) {
			groupIds.add(String.valueOf(group.getId()));
		}
		return groupIds;
	}

	public boolean isTaskOwnerOrAssignee(String currentUser, Task task) {
		return currentUser.equals(task.getOwner()) || currentUser.equals(task.getAssignee());
	}

	public boolean isTaskOwnerOrAssignee(String currentUser, String taskId) {
		return isTaskOwnerOrAssignee(currentUser, taskService.createTaskQuery().taskId(taskId).singleResult());
	}

	public boolean validateIfUserIsInitiatorAndCanCompleteTask(String userId, TaskInfo task) {
		boolean canCompleteTask = false;
		if (task.getProcessInstanceId() != null) {
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).singleResult();
			if (historicProcessInstance != null && StringUtils.isNotEmpty(historicProcessInstance.getStartUserId())) {
				String processInstanceStartUserId = historicProcessInstance.getStartUserId();
				if (userId.equals(processInstanceStartUserId) && validateIfInitiatorCanCompleteTask(task)) {
					return true;
				}
			}

		}
		return canCompleteTask;
	}

	public boolean validateIfInitiatorCanCompleteTask(TaskInfo task) {
		boolean canCompleteTask = false;
		BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
		FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
		if (flowElement instanceof UserTask) {
			UserTask userTask = (UserTask) flowElement;
			List<ExtensionElement> extensionElements = userTask.getExtensionElements().get("initiator-can-complete");
			if (CollectionUtils.isNotEmpty(extensionElements)) {
				String value = extensionElements.get(0).getElementText();
				if (StringUtils.isNotEmpty(value) && Boolean.valueOf(value)) {
					canCompleteTask = true;
				}
			}
		}
		return canCompleteTask;
	}

	public boolean isInvolved(String userId, String taskId) {
		return historyService.createHistoricTaskInstanceQuery().taskId(taskId).taskInvolvedUser(userId).count() == 1;
	}

	public HistoricProcessInstance validateReadPermissionOnProcessInstance(String userId, String processInstanceId) {
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		boolean hasPermission = hasReadPermissionOnProcessInstance(userId, historicProcessInstance, processInstanceId);
		if (hasPermission) {
			return historicProcessInstance;
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	/**
	 * Check if the given user is allowed to read the process instance.
	 */
	public boolean hasReadPermissionOnProcessInstance(String userId, String processInstanceId) {
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		return hasReadPermissionOnProcessInstance(userId, historicProcessInstance, processInstanceId);
	}

	/**
	 * Check if the given user is allowed to read the process instance.
	 */
	public boolean hasReadPermissionOnProcessInstance(String userId, HistoricProcessInstance historicProcessInstance, String processInstanceId) {
		if (historicProcessInstance == null) {
			throw new FlowableObjectNotFoundException("ProcessInstance with id: " + processInstanceId + " does not exist");
		}

		if (isAdmin(userId)) {
			return true;
		}

		// Start user check
		if (historicProcessInstance.getStartUserId() != null && historicProcessInstance.getStartUserId().equals(userId)) {
			return true;
		}

		// check if the user is involved in the task
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		historicProcessInstanceQuery.processInstanceId(processInstanceId);
		historicProcessInstanceQuery.involvedUser(userId);
		if (historicProcessInstanceQuery.count() > 0) {
			return true;
		}

		// Visibility: check if there are any tasks for the current user
		HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
		historicTaskInstanceQuery.processInstanceId(processInstanceId);
		historicTaskInstanceQuery.taskInvolvedUser(userId);
		if (historicTaskInstanceQuery.count() > 0) {
			return true;
		}

		List<String> groupIds = getGroupIdsForUser(userId);
		if (!groupIds.isEmpty()) {
			historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
			historicTaskInstanceQuery.processInstanceId(processInstanceId).taskCandidateGroupIn(groupIds);
			return historicTaskInstanceQuery.count() > 0;
		}

		return false;
	}

	public ProcessDefinition validateReadPermissionOnProcessDefinition(String userId, String processDefinitionId, String processDefinitionKey,
			String tenantId) {
		ProcessDefinition definition = managementService
				.executeCommand(new GetProcessDefinitionInfoCmd(processDefinitionId, processDefinitionKey, tenantId));
		validateReadPermissionOnProcessDefinition(userId, definition.getId());
		return definition;
	}

	public void validateReadPermissionOnProcessDefinition(String userId, String processDefinitionId) {
		if (isAdmin(userId)) {
			return;
		}
		List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(processDefinitionId);
		List<String> startUserIds = getPotentialStarterUserIds(identityLinks);
		List<String> startGroupIds = getPotentialStarterGroupIds(identityLinks);
		if (startUserIds.isEmpty() && startGroupIds.isEmpty()) {
			return;
		}
		if (startUserIds.contains(userId)) {
			return;
		}
		List<String> groupsIds = getGroupIdsForUser(userId);
		for (String startGroupId : startGroupIds) {
			if (groupsIds.contains(startGroupId)) {
				return;
			}
		}
		throw new FlowableNoPermissionException("User is not listed as potential starter");
	}

	protected List<String> getPotentialStarterGroupIds(List<IdentityLink> identityLinks) {
		List<String> groupIds = new ArrayList<>();
		for (IdentityLink identityLink : identityLinks) {
			if (identityLink.getGroupId() != null && identityLink.getGroupId().length() > 0) {
				if (!groupIds.contains(identityLink.getGroupId())) {
					groupIds.add(identityLink.getGroupId());
				}
			}
		}
		return groupIds;
	}

	protected List<String> getPotentialStarterUserIds(List<IdentityLink> identityLinks) {
		List<String> userIds = new ArrayList<>();
		for (IdentityLink identityLink : identityLinks) {
			if (identityLink.getUserId() != null && identityLink.getUserId().length() > 0) {

				if (!userIds.contains(identityLink.getUserId())) {
					userIds.add(identityLink.getUserId());
				}
			}
		}
		return userIds;
	}

	public boolean isTaskPending(Task task) {
		return DelegationState.PENDING.equals(task.getDelegationState());
	}

	/**
	 * 是否可以转办任务
	 * 
	 * 1.任务所有人可以转办
	 * 
	 * 2.任务执行人可以转办，但要求任务非委派状态
	 * 
	 * 3.被转办人不能是任务所有人和当前任务执行人
	 * 
	 * @param loginUser
	 * @param task
	 * @param assignee
	 * @return
	 */
	// public boolean canAssignTask(String loginUserId, Task task, String assignee) {
	// String owner = task.getOwner();
	// String oldAssignee = task.getAssignee();
	// if (loginUserId.equals(owner) || (loginUserId.equals(oldAssignee) && !isTaskPending(task))) {
	// if (assignee == null || assignee.length() == 0 || assignee.equals(owner) || assignee.equals(oldAssignee)) {
	// return false;
	// }
	// return true;
	// }
	// return false;
	// }

	/**
	 * 是否可以转办任务
	 * 
	 * 1.任务所有人可以转办
	 * 
	 * 2.任务执行人可以转办，但要求任务非委派状态
	 * 
	 * 3.被转办人不能是当前任务执行人
	 * 
	 * @param taskId
	 * @param userId
	 * @param assignee
	 * @return
	 */
	public Task validateAssignPermissionOnTask(String taskId, String userId, String assignee) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new FlowableObjectNotFoundException("Task with id: " + taskId + " does not exist");
		}
		String owner = task.getOwner();
		String oldAssignee = task.getAssignee();
		if (isAdmin(userId) || userId.equals(owner) || (userId.equals(oldAssignee) && !isTaskPending(task))) {
			if (assignee == null || assignee.length() == 0) {
				throw new FlowableException("Assignee cannot be empty");
			} else if (assignee.equals(oldAssignee)) {
				throw new FlowableException("The assignee is already " + assignee);
			}
			return task;
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	/**
	 * 是否可以委派任务
	 * 
	 * 1.任务所有人可以委派
	 * 
	 * 2.任务执行人可以委派
	 * 
	 * 3.被委派人不能是任务所有人和当前任务执行人
	 * 
	 * @param taskId
	 * @param userId
	 * @param delegater
	 * @return
	 */
	public Task validateDelegatePermissionOnTask(String taskId, String userId, String delegater) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new FlowableObjectNotFoundException("Task with id: " + taskId + " does not exist");
		}
		if (isAdmin(userId) || isTaskOwnerOrAssignee(userId, task)) {
			String owner = task.getOwner();
			String oldAssignee = task.getAssignee();
			if (delegater == null || delegater.length() == 0) {
				throw new FlowableException("Assignee cannot be empty");
			} else if (delegater.equals(owner)) {
				throw new FlowableException("Cannot delegate to owner");
			} else if (delegater.equals(oldAssignee)) {
				throw new FlowableException("The executor is already " + delegater);
			}
			return task;
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	public Task validateExcutePermissionOnTask(String taskId, String userId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new FlowableObjectNotFoundException("Task with id: " + taskId + " does not exist");
		}
		if (isAdmin(userId) || isTaskOwnerOrAssignee(userId, taskId)) {
			return task;
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	public ProcessInstance validateStopProcessInstancePermissionOnTask(String taskId, String userId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new FlowableObjectNotFoundException("Task with id: " + taskId + " does not exist");
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		if (processInstance == null) {
			throw new FlowableObjectNotFoundException("ProcessInstance with id: " + task.getProcessInstanceId() + " does not exist");
		}
		if (isAdmin(userId) || (userId != null && userId.length() > 0 && userId.equals(processInstance.getStartUserId()))) {
			return processInstance;
		}
		throw new FlowableNoPermissionException("User does not have permission");
	}

	public boolean isAdmin(String userId) {
		if (userId == null || userId.length() == 0) {
			userId = ShiroUtils.getUserId();
		}
		return "admin".equals(userId);
	}
}
