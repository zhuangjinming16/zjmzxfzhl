package com.zjmzxfzhl.modules.flowable.service.impl;

import com.zjmzxfzhl.common.core.util.ObjectUtils;
import com.zjmzxfzhl.modules.flowable.common.cmd.DeployModelCmd;
import com.zjmzxfzhl.modules.flowable.common.cmd.GetProcessDefinitionInfoCmd;
import com.zjmzxfzhl.modules.flowable.common.cmd.SaveModelEditorCmd;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.service.FlowableFormService;
import com.zjmzxfzhl.modules.flowable.service.ProcessDefinitionService;
import com.zjmzxfzhl.modules.flowable.vo.IdentityRequest;
import com.zjmzxfzhl.modules.flowable.vo.ProcessDefinitionRequest;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.job.api.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    private FlowableFormService flowableFormService;

    @Override
    public ProcessDefinition getProcessDefinitionById(String processDefinitionId) {
        return managementService.executeCommand(new GetProcessDefinitionInfoCmd(processDefinitionId, null, null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String processDefinitionId, Boolean cascade) {
        ProcessDefinition processDefinition = getProcessDefinitionById(processDefinitionId);
        if (processDefinition.getDeploymentId() == null) {
            throw new FlowableException("Process definition has not deployed with id " + processDefinitionId);
        }
        if (cascade) {
            List<Job> jobs = managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).list();
            for (Job job : jobs) {
                managementService.deleteTimerJob(job.getId());
            }
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        } else {
            long processCount =
                    runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).count();
            if (processCount > 0) {
                throw new FlowableException("There are running instances with process definition id " + processDefinitionId);
            }
            long jobCount = managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).count();
            if (jobCount > 0) {
                throw new FlowableException("There are running time jobs with process definition id " + processDefinitionId);
            }
            repositoryService.deleteDeployment(processDefinition.getDeploymentId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activate(ProcessDefinitionRequest actionRequest) {
        String processDefinitionId = actionRequest.getProcessDefinitionId();
        ProcessDefinition processDefinition = getProcessDefinitionById(processDefinitionId);
        if (!processDefinition.isSuspended()) {
            throw new FlowableException("Process definition is not suspended with id " + processDefinitionId);
        }
        repositoryService.activateProcessDefinitionById(processDefinitionId,
                actionRequest.isIncludeProcessInstances(), actionRequest.getDate());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspend(ProcessDefinitionRequest actionRequest) {
        String processDefinitionId = actionRequest.getProcessDefinitionId();
        ProcessDefinition processDefinition = getProcessDefinitionById(processDefinitionId);
        if (processDefinition.isSuspended()) {
            throw new FlowableException("Process definition is already suspended with id " + processDefinitionId);
        }
        repositoryService.suspendProcessDefinitionById(processDefinition.getId(),
                actionRequest.isIncludeProcessInstances(), actionRequest.getDate());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doImport(String tenantId, HttpServletRequest request) {
        if (!(request instanceof MultipartHttpServletRequest)) {
            throw new IllegalArgumentException("request must instance of MultipartHttpServletRequest");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest.getFileMap().size() == 0) {
            throw new IllegalArgumentException("request file is empty");
        }
        MultipartFile file = multipartRequest.getFileMap().values().iterator().next();
        String fileName = file.getOriginalFilename();
        boolean isFileNameInValid =
                ObjectUtils.isEmpty(fileName) || !(fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn") || fileName.toLowerCase().endsWith(".bar") || fileName.toLowerCase().endsWith(".zip"));
        if (isFileNameInValid) {
            throw new IllegalArgumentException("Request file must end with .bpmn20.xml,.bpmn|,.bar,.zip");
        }
        try {
            boolean isBpmnFile = fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn");
            if (isBpmnFile) {
                String modelId = managementService.executeCommand(new SaveModelEditorCmd(SaveModelEditorCmd.TYPE_2,
                        null, null, null, null, null, file.getBytes(), tenantId));
                managementService.executeCommand(new DeployModelCmd(modelId));
            } else if (fileName.toLowerCase().endsWith(FlowableConstant.FILE_EXTENSION_BAR) || fileName.toLowerCase().endsWith(FlowableConstant.FILE_EXTENSION_ZIP)) {
                try {
                    ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
                    ZipEntry entry = zipInputStream.getNextEntry();
                    while (entry != null) {
                        if (!entry.isDirectory()) {
                            String entryName = entry.getName();
                            byte[] bytes = IoUtil.readInputStream(zipInputStream, entryName);
                            String modelId =
                                    managementService.executeCommand(new SaveModelEditorCmd(SaveModelEditorCmd.TYPE_2
                                            , null, null, null, null, null, bytes, tenantId));
                            managementService.executeCommand(new DeployModelCmd(modelId));
                        }
                        entry = zipInputStream.getNextEntry();
                    }
                } catch (Exception e) {
                    throw new FlowableException("problem reading zip input stream", e);
                }
            }
        } catch (FlowableObjectNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new FlowableException("Process definition import error", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProcessDefinitionIdentityLink(IdentityRequest identityRequest) {
        ProcessDefinition processDefinition = getProcessDefinitionById(identityRequest.getProcessDefinitionId());
        validateIdentityArguments(identityRequest.getIdentityId(), identityRequest.getIdentityType());
        if (FlowableConstant.IDENTITY_GROUP.equals(identityRequest.getIdentityType())) {
            repositoryService.addCandidateStarterGroup(processDefinition.getId(), identityRequest.getIdentityId());
        } else if (FlowableConstant.IDENTITY_USER.equals(identityRequest.getIdentityType())) {
            repositoryService.addCandidateStarterUser(processDefinition.getId(), identityRequest.getIdentityId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcessDefinitionIdentityLink(String processDefinitionId, String identityId, String type) {
        validateIdentityArguments(identityId, type);
        ProcessDefinition processDefinition = getProcessDefinitionById(processDefinitionId);
        if (FlowableConstant.IDENTITY_GROUP.equals(type)) {
            repositoryService.deleteCandidateStarterGroup(processDefinition.getId(), identityId);
        } else if (FlowableConstant.IDENTITY_USER.equals(type)) {
            repositoryService.deleteCandidateStarterUser(processDefinition.getId(), identityId);
        }
    }

    private void validateIdentityArguments(String identityId, String type) {
        if (identityId == null || identityId.length() == 0) {
            throw new FlowableException("IdentityId may not be null");
        }
        if (!FlowableConstant.IDENTITY_GROUP.equals(type) && !FlowableConstant.IDENTITY_USER.equals(type)) {
            throw new FlowableException("Type must be " + FlowableConstant.IDENTITY_GROUP + " or " + FlowableConstant.IDENTITY_USER);
        }
    }
}
