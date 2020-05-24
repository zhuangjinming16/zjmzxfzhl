package com.zjmzxfzhl.modules.flowable.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.HistoricProcessInstanceQueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.ObjectUtils;
import com.zjmzxfzhl.framework.config.shiro.util.ShiroUtils;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.service.ProcessInstanceService;
import com.zjmzxfzhl.modules.flowable.vo.ProcessInstanceDetailResponse;
import com.zjmzxfzhl.modules.flowable.vo.ProcessInstanceRequest;
import com.zjmzxfzhl.modules.flowable.wapper.CommentListWrapper;
import com.zjmzxfzhl.modules.flowable.wapper.ProcInsListWrapper;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
@RequestMapping("/flowable/processInstance")
public class ProcessInstanceController extends BaseFlowableController {

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    @Autowired
    private ProcessInstanceService processInstanceService;

    static {
        allowedSortProperties.put(FlowableConstant.ID, HistoricProcessInstanceQueryProperty.PROCESS_INSTANCE_ID_);
        allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_ID,
                HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_KEY,
                HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_KEY);
        allowedSortProperties.put(FlowableConstant.BUSINESS_KEY, HistoricProcessInstanceQueryProperty.BUSINESS_KEY);
        allowedSortProperties.put("startTime", HistoricProcessInstanceQueryProperty.START_TIME);
        allowedSortProperties.put("endTime", HistoricProcessInstanceQueryProperty.END_TIME);
        allowedSortProperties.put("duration", HistoricProcessInstanceQueryProperty.DURATION);
        allowedSortProperties.put(FlowableConstant.TENANT_ID, HistoricProcessInstanceQueryProperty.TENANT_ID);
    }

    @RequiresPermissions("flowable:processInstance:list")
    @GetMapping(value = "/list")
    public Result list(@RequestParam Map<String, String> requestParams) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.PROCESS_INSTANCE_ID))) {
            query.processInstanceId(requestParams.get(FlowableConstant.PROCESS_INSTANCE_ID));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.PROCESS_INSTANCE_NAME))) {
            query.processInstanceNameLike(requestParams.get(FlowableConstant.PROCESS_INSTANCE_NAME));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.PROCESS_DEFINITION_NAME))) {
            query.processDefinitionName(requestParams.get(FlowableConstant.PROCESS_DEFINITION_NAME));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.PROCESS_DEFINITION_KEY))) {
            query.processDefinitionKey(requestParams.get(FlowableConstant.PROCESS_DEFINITION_KEY));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.PROCESS_DEFINITION_ID))) {
            query.processDefinitionId(requestParams.get(FlowableConstant.PROCESS_DEFINITION_ID));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.BUSINESS_KEY))) {
            query.processInstanceBusinessKey(requestParams.get(FlowableConstant.BUSINESS_KEY));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.INVOLVED_USER))) {
            query.involvedUser(requestParams.get(FlowableConstant.INVOLVED_USER));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.FINISHED))) {
            boolean isFinished = ObjectUtils.convertToBoolean(requestParams.get(FlowableConstant.FINISHED));
            if (isFinished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.SUPER_PROCESS_INSTANCE_ID))) {
            query.superProcessInstanceId(requestParams.get(FlowableConstant.SUPER_PROCESS_INSTANCE_ID));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.EXCLUDE_SUBPROCESSES))) {
            query.excludeSubprocesses(
                    ObjectUtils.convertToBoolean(requestParams.get(FlowableConstant.EXCLUDE_SUBPROCESSES)));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.FINISHED_AFTER))) {
            query.finishedAfter(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.FINISHED_AFTER)));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.FINISHED_BEFORE))) {
            query.finishedBefore(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.FINISHED_BEFORE)));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.STARTED_AFTER))) {
            query.startedAfter(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.STARTED_AFTER)));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.STARTED_BEFORE))) {
            query.startedBefore(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.STARTED_BEFORE)));
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.STARTED_BY))) {
            query.startedBy(requestParams.get(FlowableConstant.STARTED_BY));
        }
        // startByMe 覆盖 startedBy
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.START_BY_ME))) {
            boolean isStartByMe = ObjectUtils.convertToBoolean(requestParams.get(FlowableConstant.START_BY_ME));
            if (isStartByMe) {
                query.startedBy(ShiroUtils.getUserId());
            }
        }
        if (CommonUtil.isNotEmptyAfterTrim(requestParams.get(FlowableConstant.TENANT_ID))) {
            query.processInstanceTenantIdLike(requestParams.get(FlowableConstant.TENANT_ID));
        }

        FlowablePage page = this.pageList(requestParams, query, ProcInsListWrapper.class, allowedSortProperties,
                HistoricProcessInstanceQueryProperty.START_TIME);
        return Result.ok(page);
    }

    @GetMapping(value = "/listMyInvolved")
    public Result listMyInvolved(@RequestParam Map<String, String> requestParams) {
        requestParams.put(FlowableConstant.INVOLVED_USER, ShiroUtils.getUserId());
        return list(requestParams);
    }

    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String processInstanceId) {
        permissionService.validateReadPermissionOnProcessInstance(ShiroUtils.getUserId(), processInstanceId);
        ProcessInstance processInstance = null;
        HistoricProcessInstance historicProcessInstance = processInstanceService
                .getHistoricProcessInstanceById(processInstanceId);
        if (historicProcessInstance.getEndTime() == null) {
            processInstance = processInstanceService.getProcessInstanceById(processInstanceId);
        }
        ProcessInstanceDetailResponse pidr = responseFactory
                .createProcessInstanceDetailResponse(historicProcessInstance, processInstance);
        return Result.ok(pidr);
    }

    @SysLogAuto(value = "启动流程实例")
    @PostMapping(value = "/start")
    @Transactional(rollbackFor = Exception.class)
    public Result start(@RequestBody ProcessInstanceRequest processInstanceRequest) {
        processInstanceService.start(processInstanceRequest);
        return Result.ok();
    }

    @SysLogAuto(value = "删除流程实例")
    @RequiresPermissions("flowable:processInstance:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String processInstanceId, @RequestParam(required = false) boolean cascade,
            @RequestParam(required = false) String deleteReason) {
        processInstanceService.delete(processInstanceId, cascade, deleteReason);
        return Result.ok();
    }

    @SysLogAuto(value = "挂起流程实例")
    @RequiresPermissions("flowable:processInstance:suspendOrActivate")
    @PutMapping(value = "/suspend")
    public Result suspend(@RequestBody ProcessInstanceRequest processInstanceRequest) {
        processInstanceService.suspend(processInstanceRequest.getProcessInstanceId());
        return Result.ok();
    }

    @SysLogAuto(value = "激活流程实例")
    @RequiresPermissions("flowable:processInstance:suspendOrActivate")
    @PutMapping(value = "/activate")
    public Result activate(@RequestBody ProcessInstanceRequest processInstanceRequest) {
        processInstanceService.activate(processInstanceRequest.getProcessInstanceId());
        return Result.ok();
    }

    @GetMapping(value = "/comments")
    public Result comments(@RequestParam String processInstanceId) {
        permissionService.validateReadPermissionOnProcessInstance(ShiroUtils.getUserId(), processInstanceId);
        List<Comment> datas = taskService.getProcessInstanceComments(processInstanceId);
        Collections.reverse(datas);
        return Result.ok(this.listWrapper(CommentListWrapper.class, datas));
    }

    @GetMapping(value = "/formData")
    public Result formData(@RequestParam String processInstanceId) {
        HistoricProcessInstance processInstance = permissionService
                .validateReadPermissionOnProcessInstance(ShiroUtils.getUserId(), processInstanceId);
        Object renderedStartForm = formService.getRenderedStartForm(processInstance.getProcessDefinitionId());
        Map<String, Object> variables = null;
        if (processInstance.getEndTime() == null) {
            variables = runtimeService.getVariables(processInstanceId);
        } else {
            List<HistoricVariableInstance> hisVals = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(processInstanceId).list();
            variables = new HashMap<>(16);
            for (HistoricVariableInstance variableInstance : hisVals) {
                variables.put(variableInstance.getVariableName(), variableInstance.getValue());
            }
        }
        Map<String, Object> ret = new HashMap<String, Object>(4);
        boolean showBusinessKey = isShowBusinessKey(processInstance.getProcessDefinitionId());
        ret.put("showBusinessKey", showBusinessKey);
        ret.put(FlowableConstant.BUSINESS_KEY, processInstance.getBusinessKey());
        ret.put("renderedStartForm", renderedStartForm);
        ret.put("variables", variables);
        return Result.ok(ret);
    }
}
