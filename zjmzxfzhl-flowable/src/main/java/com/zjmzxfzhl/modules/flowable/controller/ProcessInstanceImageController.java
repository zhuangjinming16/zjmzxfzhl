package com.zjmzxfzhl.modules.flowable.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.config.CustomProcessDiagramGenerator;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
public class ProcessInstanceImageController extends BaseFlowableController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @GetMapping(value = "/flowable/processInstanceImage")
    public ResponseEntity<byte[]> image(@RequestParam String processInstanceId) {
        HistoricProcessInstance processInstance =
                permissionService.validateReadPermissionOnProcessInstance(SecurityUtils.getUserId(), processInstanceId);
        ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        if (pde == null || !pde.hasGraphicalNotation()) {
            throw new FlowableException(messageFormat("Process instance image is not found with id {0}",
                    processInstanceId));
        }
        List<String> highLightedFlows = new ArrayList<>();
        List<String> highLightedActivities = new ArrayList<>();
        List<HistoricActivityInstance> allHistoricActivityIntances =
                historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        allHistoricActivityIntances.forEach(historicActivityInstance -> {
            if (BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW.equals(historicActivityInstance.getActivityType())) {
                highLightedFlows.add(historicActivityInstance.getActivityId());
            } else {
                highLightedActivities.add(historicActivityInstance.getActivityId());
            }
        });

        List<String> runningActivitiIdList = null;
        // 流程已结束
        if (processInstance != null && processInstance.getEndTime() != null) {
            runningActivitiIdList = Arrays.asList();
        } else {
            runningActivitiIdList = runtimeService.getActiveActivityIds(processInstanceId);
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
        CustomProcessDiagramGenerator diagramGenerator =
                (CustomProcessDiagramGenerator) processEngineConfiguration.getProcessDiagramGenerator();
        InputStream resource = diagramGenerator.generateCustomDiagram(bpmnModel, "png", highLightedActivities,
                runningActivitiIdList, highLightedFlows, processEngineConfiguration.getActivityFontName(),
                processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName(),
                processEngineConfiguration.getClassLoader(), 1.0, true);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_PNG);
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new FlowableException("Process instance image read error", e);
        }
    }
}
