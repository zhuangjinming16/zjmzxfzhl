package com.zjmzxfzhl.modules.flowable.common.cmd;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.repository.Model;
import org.flowable.image.ProcessDiagramGenerator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.Collections;

/**
 * 保存模型，同时生成图片
 *
 * @author 庄金明
 * @date 2020年8月30日
 */
public class SaveModelEditorCmd implements Command<String>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_1 = "1";
    public static final String TYPE_2 = "2";
    public static final String TYPE_3 = "3";
    /**
     * type=1,模型流程图维护
     * type=2,导入模型
     * type=3,复制模型
     */
    private String type = TYPE_1;
    private String modelId;
    private String key;
    private String name;
    private String category;
    private String description;
    private byte[] editor;
    private String tenantId;

    public SaveModelEditorCmd(String type, String modelId, String key, String name, String category,
                              String description, byte[] editor, String tenantId) {
        this.type = type;
        this.modelId = modelId;
        this.key = key;
        this.name = name;
        this.category = category;
        this.description = description;
        this.editor = editor;
        this.tenantId = tenantId;
    }

    @Override
    public String execute(CommandContext commandContext) {
        if (TYPE_1.equals(this.type)) {
            return executeType1(commandContext);
        } else if (TYPE_2.equals(this.type) || TYPE_3.equals(this.type)) {
            return executeType2And3(commandContext);
        } else {
            throw new FlowableException("SaveModelEditorCmd error type=" + type);
        }
    }

    private String executeType1(CommandContext commandContext) {
        if (modelId == null || modelId.length() == 0 || editor == null || editor.length == 0) {
            throw new FlowableException("ModelId or editor is null");
        }
        ProcessEngineConfiguration processEngineConfiguration =
                CommandContextUtil.getProcessEngineConfiguration(commandContext);
        RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            throw new FlowableException("Cannot find model by id:" + modelId);
        }
        if (model.getDeploymentId() != null && model.getDeploymentId().length() > 0) {
            throw new FlowableException("The published model cannot be modified");
        }

        BpmnModel bpmnModel = generateBpmnModel(editor);
        key = bpmnModel.getMainProcess().getId();
        name = bpmnModel.getMainProcess().getName();
        category = bpmnModel.getTargetNamespace();
        description = bpmnModel.getMainProcess().getDocumentation();

        if (key == null || key.length() == 0) {
            throw new FlowableException("process id is null");
        }
        if (!key.equals(model.getKey())) {
            throw new FlowableException("Cannot modify process id");
        }

        model.setName(name);
        model.setCategory(category);
        model.setMetaInfo(description);

        repositoryService.saveModel(model);

        addModelEditorSourceAndSourceExtra(processEngineConfiguration, repositoryService, editor, bpmnModel,
                model.getId());

        return model.getId();
    }

    private String executeType2And3(CommandContext commandContext) {
        if (editor == null || editor.length == 0) {
            throw new FlowableException("editor is null");
        }
        ProcessEngineConfiguration processEngineConfiguration =
                CommandContextUtil.getProcessEngineConfiguration(commandContext);
        RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

        BpmnModel bpmnModel = generateBpmnModel(editor);
        key = bpmnModel.getMainProcess().getId();
        name = bpmnModel.getMainProcess().getName();
        category = bpmnModel.getTargetNamespace();
        description = bpmnModel.getMainProcess().getDocumentation();

        if (key == null || key.length() == 0) {
            throw new FlowableException("process id is null");
        }

        Model saveModel = null;
        Model model = null;
        if (tenantId == null || tenantId.length() == 0) {
            model = repositoryService.createModelQuery().modelKey(key).latestVersion().singleResult();
        } else {
            model = repositoryService.createModelQuery().modelKey(key).modelTenantId(tenantId).latestVersion().singleResult();
        }

        // 导入操作、复制操作，若没有查询到最新版本信息，则产生一个新的版本，版本号=1
        if (model == null) {
            saveModel = repositoryService.newModel();
            saveModel.setVersion(1);
        } else {
            // 导入操作：已发布的重新产生一个版本，模型复制的也要重新产生一个版本
            if (TYPE_3.equals(type) || (model.getDeploymentId() != null && model.getDeploymentId().length() > 0)) {
                saveModel = repositoryService.newModel();
                saveModel.setVersion(model.getVersion() + 1);
            }
            // 导入操作：未发布的直接修改旧的版本
            else {
                saveModel = model;
            }
        }
        saveModel.setKey(key);
        saveModel.setName(name);
        saveModel.setCategory(category);
        saveModel.setMetaInfo(description);
        if (tenantId != null && tenantId.length() == 0) {
            saveModel.setTenantId(tenantId);
        }

        repositoryService.saveModel(saveModel);

        addModelEditorSourceAndSourceExtra(processEngineConfiguration, repositoryService, editor, bpmnModel,
                saveModel.getId());

        return saveModel.getId();
    }

    private BpmnModel generateBpmnModel(byte[] bytes) {
        BpmnModel bpmnModel = null;
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
            bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
        } catch (Exception e) {
            throw new FlowableException("SaveModelEditorCmd error :" + e.getMessage());
        }
        return bpmnModel;
    }

    private void addModelEditorSourceAndSourceExtra(ProcessEngineConfiguration processEngineConfiguration,
                                                    RepositoryService repositoryService, byte[] bytes,
                                                    BpmnModel bpmnModel, String modelId) {
        if (bytes != null) {
            repositoryService.addModelEditorSource(modelId, bytes);

            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", Collections.emptyList(),
                    Collections.emptyList(), processEngineConfiguration.getActivityFontName(),
                    processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName()
                    , processEngineConfiguration.getClassLoader(), 1.0, true);

            try {
                repositoryService.addModelEditorSourceExtra(modelId, IOUtils.toByteArray(resource));
            } catch (IOException e) {
                throw new FlowableException("SaveModelEditorCmd error :" + e.getMessage());
            }
        }
    }
}