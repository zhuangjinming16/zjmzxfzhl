package com.zjmzxfzhl.modules.flowable.common.cmd;

import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.modules.flowable.entity.FlowableForm;
import com.zjmzxfzhl.modules.flowable.service.FlowableFormService;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.Model;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 部署模型
 *
 * @author 庄金明
 * @date 2020/08/30
 */
public class DeployModelCmd implements Command<Deployment>, Serializable {

    private static final long serialVersionUID = 1L;
    protected String modelId;

    public DeployModelCmd(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public Deployment execute(CommandContext commandContext) {
        Deployment deployment;

        RepositoryService repositoryService = CommandContextUtil.getProcessEngineConfiguration(commandContext).getRepositoryService();
        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            throw new FlowableObjectNotFoundException("Could not find a model with id '" + modelId + "'.", Model.class);
        }
        if (!model.hasEditorSource()) {
            throw new FlowableObjectNotFoundException("Model with id '" + modelId + "' does not have source available.", String.class);
        }
        byte[] bpmnBytes = CommandContextUtil.getProcessEngineConfiguration(commandContext).getModelEntityManager().findEditorSourceByModelId(modelId);
        if (bpmnBytes == null) {
            throw new FlowableObjectNotFoundException("Model with id '" + modelId + "' does not have source available.", String.class);
        }

        try {
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
            String fileName = model.getId() + ".bpmn20.xml";
            ByteArrayInputStream bis = new ByteArrayInputStream(bpmnBytes);
            deploymentBuilder.addInputStream(fileName, bis);
            deploymentBuilder.name(fileName);
            deploymentBuilder.category(model.getCategory());

            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(bpmnBytes), "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
            org.flowable.bpmn.model.Process process = bpmnModel.getMainProcess();
            Collection<FlowElement> flowElements = process.getFlowElements();
            Map<String, String> formKeyMap = new HashMap<String, String>(16);
            for (FlowElement flowElement : flowElements) {
                String formKey = null;
                if (flowElement instanceof StartEvent) {
                    StartEvent startEvent = (StartEvent) flowElement;
                    if (startEvent.getFormKey() != null && startEvent.getFormKey().length() > 0) {
                        formKey = startEvent.getFormKey();
                    }
                } else if (flowElement instanceof UserTask) {
                    UserTask userTask = (UserTask) flowElement;
                    if (userTask.getFormKey() != null && userTask.getFormKey().length() > 0) {
                        formKey = userTask.getFormKey();
                    }
                }
                if (formKey != null && formKey.length() > 0) {
                    if (formKeyMap.containsKey(formKey)) {
                        continue;
                    } else {
                        String formKeyDefinition = formKey.replace(".form", "");
                        FlowableFormService flowableFormService =
                                SpringContextUtils.getBean(FlowableFormService.class);
                        FlowableForm form = flowableFormService.getById(formKeyDefinition);
                        if (form != null && form.getFormJson() != null && form.getFormJson().length() > 0) {
                            byte[] formJson = form.getFormJson().getBytes("UTF-8");
                            ByteArrayInputStream bi = new ByteArrayInputStream(formJson);
                            deploymentBuilder.addInputStream(formKey, bi);
                            formKeyMap.put(formKey, formKey);
                        } else {
                            throw new FlowableObjectNotFoundException("Cannot find formJson with formKey " + formKeyDefinition);
                        }
                    }
                }
            }

            if (model.getTenantId() != null) {
                deploymentBuilder.tenantId(model.getTenantId());
            }
            deployment = deploymentBuilder.deploy();
            if(deployment!=null){
                model.setDeploymentId(deployment.getId());
            }
        } catch (Exception e) {
            if (e instanceof FlowableException) {
                throw (FlowableException) e;
            }
            throw new FlowableException(e.getMessage(), e);
        }

        return deployment;
    }

}