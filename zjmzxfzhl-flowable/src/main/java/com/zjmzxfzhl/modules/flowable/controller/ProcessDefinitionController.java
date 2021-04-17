package com.zjmzxfzhl.modules.flowable.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.modules.flowable.vo.query.ProcessDefinitionQueryVo;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.impl.ProcessDefinitionQueryProperty;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.util.ObjectUtils;
import com.zjmzxfzhl.common.log.annotation.Log;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.service.ProcessDefinitionService;
import com.zjmzxfzhl.modules.flowable.vo.ProcessDefinitionRequest;
import com.zjmzxfzhl.modules.flowable.vo.ProcessDefinitionResponse;
import com.zjmzxfzhl.modules.flowable.wapper.ProcDefListWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@RestController
@RequestMapping("/flowable/processDefinition")
@Slf4j
public class ProcessDefinitionController extends BaseFlowableController {
    private static final Map<String, QueryProperty> ALLOWED_SORT_PROPERTIES = new HashMap<>();
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    static {
        ALLOWED_SORT_PROPERTIES.put(FlowableConstant.ID, ProcessDefinitionQueryProperty.PROCESS_DEFINITION_ID);
        ALLOWED_SORT_PROPERTIES.put(FlowableConstant.KEY, ProcessDefinitionQueryProperty.PROCESS_DEFINITION_KEY);
        ALLOWED_SORT_PROPERTIES.put(FlowableConstant.CATEGORY,
                ProcessDefinitionQueryProperty.PROCESS_DEFINITION_CATEGORY);
        ALLOWED_SORT_PROPERTIES.put(FlowableConstant.NAME, ProcessDefinitionQueryProperty.PROCESS_DEFINITION_NAME);
        ALLOWED_SORT_PROPERTIES.put(FlowableConstant.VERSION,
                ProcessDefinitionQueryProperty.PROCESS_DEFINITION_VERSION);
        ALLOWED_SORT_PROPERTIES.put(FlowableConstant.TENANT_ID,
                ProcessDefinitionQueryProperty.PROCESS_DEFINITION_TENANT_ID);
    }

    @PreAuthorize("@elp.single('flowable:processDefinition:list')")
    @GetMapping(value = "/list")
    public Result list(ProcessDefinitionQueryVo processDefinitionQueryVo) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getProcessDefinitionId())) {
            processDefinitionQuery.processDefinitionId(processDefinitionQueryVo.getProcessDefinitionId());
        }
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getProcessDefinitionCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike(ObjectUtils.convertToLike(processDefinitionQueryVo.getProcessDefinitionCategory()));
        }
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getProcessDefinitionKey())) {
            processDefinitionQuery.processDefinitionKeyLike(ObjectUtils.convertToLike(processDefinitionQueryVo.getProcessDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getProcessDefinitionName())) {
            processDefinitionQuery.processDefinitionNameLike(ObjectUtils.convertToLike(processDefinitionQueryVo.getProcessDefinitionName()));
        }
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getProcessDefinitionVersion())) {
            processDefinitionQuery.processDefinitionVersion(processDefinitionQueryVo.getProcessDefinitionVersion());
        }

        Boolean suspended = CommonUtil.isEmptyDefault(processDefinitionQueryVo.getSuspended(), false);
        Boolean active = CommonUtil.isEmptyDefault(processDefinitionQueryVo.getActive(), false);
        if (!suspended.equals(active)) {
            if (suspended) {
                processDefinitionQuery.suspended();
            }
            if (active) {
                processDefinitionQuery.active();
            }
        }
        if (processDefinitionQueryVo.getLatestVersion()) {
            processDefinitionQuery.latestVersion();
        }
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getStartableByUser())) {
            processDefinitionQuery.startableByUser(processDefinitionQueryVo.getStartableByUser());
        }
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getTenantId())) {
            processDefinitionQuery.processDefinitionTenantId(processDefinitionQueryVo.getTenantId());
        }
        FlowablePage page = this.pageList(processDefinitionQueryVo, processDefinitionQuery, ProcDefListWrapper.class,
                ALLOWED_SORT_PROPERTIES);
        return Result.ok(page);
    }

    @GetMapping(value = "/listMyself")
    public Result listMyself(ProcessDefinitionQueryVo processDefinitionQueryVo) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (ObjectUtils.isNotEmpty(processDefinitionQueryVo.getProcessDefinitionName())) {
            processDefinitionQuery.processDefinitionNameLike(ObjectUtils.convertToLike(processDefinitionQueryVo.getProcessDefinitionName()));
        }
        processDefinitionQuery.latestVersion().active().startableByUser(SecurityUtils.getUserId());
        FlowablePage page = this.pageList(processDefinitionQueryVo, processDefinitionQuery, ProcDefListWrapper.class,
                ALLOWED_SORT_PROPERTIES);
        return Result.ok(page);
    }

    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String processDefinitionId) {
        permissionService.validateReadPermissionOnProcessDefinition(SecurityUtils.getUserId(), processDefinitionId);
        ProcessDefinition processDefinition = processDefinitionService.getProcessDefinitionById(processDefinitionId);
        String formKey = null;
        if (processDefinition.hasStartFormKey()) {
            formKey = formService.getStartFormKey(processDefinitionId);
        }
        ProcessDefinitionResponse processDefinitionResponse =
                responseFactory.createProcessDefinitionResponse(processDefinition, formKey);
        return Result.ok(processDefinitionResponse);
    }

    @GetMapping(value = "/renderedStartForm")
    public Result renderedStartForm(@RequestParam String processDefinitionId) {
        permissionService.validateReadPermissionOnProcessDefinition(SecurityUtils.getUserId(), processDefinitionId);
        Object renderedStartForm = formService.getRenderedStartForm(processDefinitionId);
        boolean showBusinessKey = this.isShowBusinessKey(processDefinitionId);
        return Result.ok(ImmutableMap.of("renderedStartForm", renderedStartForm != null ? renderedStartForm : "",
                "showBusinessKey", showBusinessKey));
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam String processDefinitionId) {
        permissionService.validateReadPermissionOnProcessDefinition(SecurityUtils.getUserId(), processDefinitionId);
        ProcessDefinition processDefinition = processDefinitionService.getProcessDefinitionById(processDefinitionId);
        InputStream imageStream = repositoryService.getProcessDiagram(processDefinition.getId());
        if (imageStream == null) {
            throw new FlowableException(messageFormat("Process definition image is not found with id {0}",
                    processDefinitionId));
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_PNG);
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(imageStream), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new FlowableException(messageFormat("Process definition image read error with id {0}",
                    processDefinitionId), e);
        }
    }

    @PreAuthorize("@elp.single('flowable:processDefinition:xml')")
    @GetMapping(value = "/xml")
    public ResponseEntity<byte[]> xml(@RequestParam String processDefinitionId) {
        permissionService.validateReadPermissionOnProcessDefinition(SecurityUtils.getUserId(), processDefinitionId);
        ProcessDefinition processDefinition = processDefinitionService.getProcessDefinitionById(processDefinitionId);
        String deploymentId = processDefinition.getDeploymentId();
        String resourceId = processDefinition.getResourceName();
        if (deploymentId == null || deploymentId.length() == 0) {
            throw new FlowableException(messageFormat("Process definition deployment id is not found with id {0}",
                    processDefinitionId));
        }
        if (resourceId == null || resourceId.length() == 0) {
            throw new FlowableException(messageFormat("Process definition resource id is not found with id {0}",
                    processDefinitionId));
        }
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null) {
            throw new FlowableException(messageFormat("Process definition deployment is not found with deploymentId " + "{0}", deploymentId));
        }

        List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);
        if (ObjectUtils.isEmpty(resourceList) || !resourceList.contains(resourceId)) {
            throw new FlowableException(messageFormat("Process definition resourceId {0} is not found with " +
                    "deploymentId {1}", resourceId, deploymentId));
        }
        InputStream resourceStream = repositoryService.getResourceAsStream(deploymentId, resourceId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_XML);
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(resourceStream), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("获取流程定义XML信息异常", e);
            throw new FlowableException(messageFormat("ProcessDefinition xml read error with id {0}", deploymentId), e);
        }
    }

    @Log(value = "删除流程定义")
    @PreAuthorize("@elp.single('flowable:processDefinition:delete')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String processDefinitionId, @RequestParam(required = false, defaultValue =
            "false") Boolean cascade) {
        processDefinitionService.delete(processDefinitionId, cascade);
        return Result.ok();
    }

    @Log(value = "激活流程定义")
    @PreAuthorize("@elp.single('flowable:processDefinition:suspendOrActivate')")
    @PutMapping(value = "/activate")
    public Result activate(@RequestBody ProcessDefinitionRequest actionRequest) {
        processDefinitionService.activate(actionRequest);
        return Result.ok();
    }

    @Log(value = "挂起流程定义")
    @PreAuthorize("@elp.single('flowable:processDefinition:suspendOrActivate')")
    @PutMapping(value = "/suspend")
    public Result suspend(@RequestBody ProcessDefinitionRequest actionRequest) {
        processDefinitionService.suspend(actionRequest);
        return Result.ok();
    }

    /**
     * 导入流程定义
     *
     * @param request
     * @return
     */
    @Log(value = "导入流程定义")
    @PreAuthorize("@elp.single('flowable:processDefinition:import')")
    @PostMapping(value = "/import")
    public Result doImport(@RequestParam(required = false) String tenantId, HttpServletRequest request) {
        processDefinitionService.doImport(tenantId, request);
        return Result.ok();
    }
}
