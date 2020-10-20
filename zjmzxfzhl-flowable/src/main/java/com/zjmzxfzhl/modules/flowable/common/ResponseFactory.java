package com.zjmzxfzhl.modules.flowable.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjmzxfzhl.modules.flowable.vo.*;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Component
public class ResponseFactory {
    private final IdentityService identityService;
    private final FormService formService;
    private final HistoryService historyService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ResponseFactory(IdentityService identityService, FormService formService, HistoryService historyService,ObjectMapper objectMapper) {
        this.identityService = identityService;
        this.formService = formService;
        this.historyService = historyService;
        this.objectMapper = objectMapper;
    }

    public List<ProcessDefinitionResponse> createProcessDefinitionResponseList(List<ProcessDefinition> processDefinitions) {
        List<ProcessDefinitionResponse> responseList = new ArrayList<>();
        for (ProcessDefinition instance : processDefinitions) {
            responseList.add(createProcessDefinitionResponse(instance));
        }
        return responseList;
    }

    public ProcessDefinitionResponse createProcessDefinitionResponse(ProcessDefinition processDefinition) {
        if (processDefinition.hasStartFormKey()) {
            String startFormKey = formService.getStartFormKey(processDefinition.getId());
            return createProcessDefinitionResponse(processDefinition, startFormKey);
        } else {
            return createProcessDefinitionResponse(processDefinition, null);
        }
    }

    public ProcessDefinitionResponse createProcessDefinitionResponse(ProcessDefinition processDefinition,
                                                                     String formKey) {
        ProcessDefinitionResponse response = new ProcessDefinitionResponse();
        response.setId(processDefinition.getId());
        response.setKey(processDefinition.getKey());
        response.setVersion(processDefinition.getVersion());
        response.setCategory(processDefinition.getCategory());
        response.setName(processDefinition.getName());
        response.setDescription(processDefinition.getDescription());
        response.setSuspended(processDefinition.isSuspended());
        response.setGraphicalNotationDefined(processDefinition.hasGraphicalNotation());
        response.setTenantId(processDefinition.getTenantId());
        response.setFormKey(formKey);
        return response;
    }

    public List<ProcessInstanceResponse> createProcessInstanceResponseList(List<ProcessInstance> processInstances) {
        List<ProcessInstanceResponse> responseList = new ArrayList<>();
        for (ProcessInstance instance : processInstances) {
            responseList.add(createProcessInstanceResponse(instance));
        }
        return responseList;
    }

    private ProcessInstanceResponse createProcessInstanceResponse(ProcessInstance processInstance) {
        ProcessInstanceResponse result = new ProcessInstanceResponse();
        result.setId(processInstance.getId());
        result.setSuspended(processInstance.isSuspended());
        result.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        result.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        result.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        result.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
        result.setStartTime(processInstance.getStartTime());
        result.setStartUserId(processInstance.getStartUserId());
        result.setCurrentActivityId(processInstance.getActivityId());
        result.setBusinessKey(processInstance.getBusinessKey());
        result.setTenantId(processInstance.getTenantId());
        return result;
    }

    public List<HistoricProcessInstanceResponse> createHistoricProcessInstanceResponseList(List<HistoricProcessInstance> processInstances) {
        List<HistoricProcessInstanceResponse> responseList = new ArrayList<>();
        for (HistoricProcessInstance instance : processInstances) {
            responseList.add(createHistoricProcessInstanceResponse(instance));
        }
        return responseList;
    }

    private HistoricProcessInstanceResponse createHistoricProcessInstanceResponse(HistoricProcessInstance processInstance) {
        HistoricProcessInstanceResponse result = new HistoricProcessInstanceResponse();
        createHistoricProcessInstanceResponse(result, processInstance);
        return result;
    }

    public ProcessInstanceDetailResponse createProcessInstanceDetailResponse(HistoricProcessInstance hisProcessInstance, ProcessInstance processInstance) {
        ProcessInstanceDetailResponse result = new ProcessInstanceDetailResponse();
        createHistoricProcessInstanceResponse(result, hisProcessInstance);
        result.setStartUserName(getUserName(hisProcessInstance.getStartUserId()));
        result.setDeleteReason(hisProcessInstance.getDeleteReason());
        if (processInstance != null) {
            result.setSuspended(processInstance.isSuspended());
        }
        return result;
    }

    private void createHistoricProcessInstanceResponse(HistoricProcessInstanceResponse result,
                                                       HistoricProcessInstance processInstance) {
        result.setId(processInstance.getId());
        result.setName(processInstance.getName());
        result.setBusinessKey(processInstance.getBusinessKey());
        result.setStartTime(processInstance.getStartTime());
        result.setEndTime(processInstance.getEndTime());
        result.setDurationInMillis(processInstance.getDurationInMillis());
        result.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        result.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
        result.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        result.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
        result.setStartActivityId(processInstance.getStartActivityId());
        result.setStartUserId(processInstance.getStartUserId());
        result.setSuperProcessInstanceId(processInstance.getSuperProcessInstanceId());
        result.setTenantId(processInstance.getTenantId());
    }

    public List<TaskResponse> createTaskResponseList(List<TaskInfo> tasks) {
        Map<String, String> processInstanceNames = new HashMap<>(16);
        Set<String> processInstanceIds = new HashSet<>();
        for (TaskInfo task : tasks) {
            if (task.getProcessInstanceId() != null) {
                processInstanceIds.add(task.getProcessInstanceId());
            }
        }
        if (processInstanceIds != null && processInstanceIds.size() > 0) {
            historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds).list().forEach(processInstance -> processInstanceNames.put(processInstance.getId(), processInstance.getName()));
        }
        List<TaskResponse> responseList = new ArrayList<>();
        for (TaskInfo task : tasks) {
            TaskResponse result = new TaskResponse(task, processInstanceNames.get(task.getProcessInstanceId()));
            responseList.add(result);
        }
        return responseList;
    }

    public TaskResponse createTaskResponse(Task taskInstance) {
        TaskResponse result = new TaskResponse();
        createTaskResponse(result, taskInstance);
        return result;
    }

    private void createTaskResponse(TaskResponse result, Task taskInstance) {
        result.setId(taskInstance.getId());
        result.setName(taskInstance.getName());
        result.setOwner(taskInstance.getOwner());
        result.setTaskDefinitionKey(taskInstance.getTaskDefinitionKey());
        result.setCreateTime(taskInstance.getCreateTime());
        result.setAssignee(taskInstance.getAssignee());
        result.setDescription(taskInstance.getDescription());
        result.setDueDate(taskInstance.getDueDate());
        result.setDelegationState(taskInstance.getDelegationState());
        result.setFormKey(taskInstance.getFormKey());
        result.setParentTaskId(taskInstance.getParentTaskId());
        result.setPriority(taskInstance.getPriority());
        result.setSuspended(taskInstance.isSuspended());
        result.setTenantId(taskInstance.getTenantId());
        result.setCategory(taskInstance.getCategory());
        result.setProcessDefinitionId(taskInstance.getProcessDefinitionId());
        result.setProcessInstanceId(taskInstance.getProcessInstanceId());
    }

    public List<IdentityResponse> createTaskIdentityResponseList(List<HistoricIdentityLink> historicIdentityLinks) {
        List<IdentityResponse> responseList = new ArrayList<>();
        for (HistoricIdentityLink identityLink : historicIdentityLinks) {
            if (IdentityLinkType.CANDIDATE.equals(identityLink.getType())) {
                responseList.add(createIdentityResponse(identityLink));
            }
        }
        return responseList;
    }

    public List<IdentityResponse> createIdentityResponseList(List<IdentityLink> identityLinks) {
        List<IdentityResponse> responseList = new ArrayList<>();
        for (IdentityLinkInfo identityLink : identityLinks) {
            responseList.add(createIdentityResponse(identityLink));
        }
        return responseList;
    }

    private IdentityResponse createIdentityResponse(IdentityLinkInfo identityLink) {
        IdentityResponse identityResponse = new IdentityResponse();
        if (identityLink.getGroupId() != null) {
            identityResponse.setIdentityType(FlowableConstant.IDENTITY_GROUP);
            identityResponse.setIdentityId(identityLink.getGroupId());
            identityResponse.setIdentityName(getGroupName(identityLink.getGroupId()));
        } else if (identityLink.getUserId() != null) {
            identityResponse.setIdentityType(FlowableConstant.IDENTITY_USER);
            identityResponse.setIdentityId(identityLink.getUserId());
            identityResponse.setIdentityName(getUserName(identityLink.getUserId()));
        }
        return identityResponse;
    }

    public List<CommentResponse> createCommentResponseList(List<Comment> comments) {
        List<CommentResponse> responseList = new ArrayList<>();
        for (Comment comment : comments) {
            if(CommonUtil.isNotEmptyAfterTrim(comment.getTaskId())){
                responseList.add(createCommentResponse(comment));
            }
        }
        return responseList;
    }

    public CommentResponse createCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setUserId(comment.getUserId());
        commentResponse.setUserName(getUserName(comment.getUserId()));
        commentResponse.setType(comment.getType());
        commentResponse.setTypeDesc(CommentTypeEnum.getEnumMsgByType(comment.getType()));
        commentResponse.setTime(comment.getTime());
        commentResponse.setProcessInstanceId(comment.getProcessInstanceId());
        commentResponse.setTaskId(comment.getTaskId());
        TaskInfo task = historyService.createHistoricTaskInstanceQuery().taskId(comment.getTaskId()).singleResult();
        commentResponse.setTaskName(task.getName());
        commentResponse.setFullMessage(comment.getFullMessage());
        return commentResponse;
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

    private String getGroupName(String groupId) {
        if (CommonUtil.isEmptyStr(groupId)) {
            return null;
        }
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group != null) {
            return group.getName();
        }
        return null;
    }

    public List<ModelResponse> createModelResponseList(List<Model> models) {
        List<ModelResponse> responseList = new ArrayList<>();
        for (Model instance : models) {
            responseList.add(createModelResponse(instance));
        }
        return responseList;
    }

    public ModelResponse createModelResponse(Model model) {
        ModelResponse response = new ModelResponse();
        response.setCategory(model.getCategory());
        response.setCreateTime(model.getCreateTime());
        response.setId(model.getId());
        response.setKey(model.getKey());
        response.setLastUpdateTime(model.getLastUpdateTime());
        try {
            JsonNode modelNode = objectMapper.readTree(model.getMetaInfo());
            response.setDescription(modelNode.get("description").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setName(model.getName());
        response.setVersion(model.getVersion());
        if (model.getDeploymentId() != null) {
            response.setDeployed(true);
        } else {
            response.setDeployed(false);
        }
        response.setTenantId(model.getTenantId());
        return response;
    }

}