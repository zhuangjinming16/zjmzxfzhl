package com.zjmzxfzhl.modules.flowable.common.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.repository.Model;
import org.flowable.image.ProcessDiagramGenerator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 保存模型，同时生成图片
 *
 * @author 庄金明
 * @date 2020年8月30日
 */
public class SaveModelEditorCmd implements Command<Void>, Serializable {

    private static final long serialVersionUID = 1L;
    private String modelId;
    private String tenantId;
    private String editor;
    private String key;
    private String name;
    private String category;
    private String description;

    public SaveModelEditorCmd(String modelId, String editor) {
        this.modelId = modelId;
        this.editor = editor;
    }

    public SaveModelEditorCmd(String modelId, String tenantId, String editor) {
        this.modelId = modelId;
        this.tenantId = tenantId;
        this.editor = editor;
    }

    public SaveModelEditorCmd(String modelId, String key, String name, String category, String description) {
        this.modelId = modelId;
        this.key = key;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfiguration processEngineConfiguration =
                CommandContextUtil.getProcessEngineConfiguration(commandContext);
        RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

        if (modelId == null && editor == null) {
            throw new FlowableException("Both modelId and editor is null");
        }
        try {
            // 【1】若有送 editor，则以 editor 为准设置 key、name、category、description，否则修改模型的值
            BpmnModel bpmnModel = null;
            byte[] bytes = null;
            if (editor != null && editor.length() > 0) {
                bytes = editor.getBytes("utf-8");

                XMLInputFactory xif = XMLInputFactory.newInstance();
                InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8");
                XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

                key = bpmnModel.getMainProcess().getId();
                name = bpmnModel.getMainProcess().getName();
                category = bpmnModel.getTargetNamespace();
                description = bpmnModel.getMainProcess().getDocumentation();
            } else {
                byte[] oldBytes = repositoryService.getModelEditorSource(modelId);
                if (oldBytes != null) {
                    XMLInputFactory xif = XMLInputFactory.newInstance();
                    InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(oldBytes), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                    BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
                    bpmnModel = bpmnXMLConverter.convertToBpmnModel(xtr);
                    bpmnModel.setTargetNamespace(category);
                    bpmnModel.getMainProcess().setId(key);
                    bpmnModel.getMainProcess().setName(name);
                    bpmnModel.getMainProcess().setDocumentation(description);
                    bytes = bpmnXMLConverter.convertToXML(bpmnModel, "UTF-8");
                }
            }

            // 【2】获取Model并修改 对应 key 的 name、category
            Model model = null;
            if (modelId == null) {
                model = repositoryService.newModel();
                if (tenantId != null) {
                    model.setTenantId(tenantId);
                }
            } else {
                model = repositoryService.getModel(modelId);
                model.setVersion(model.getVersion() + 1);
            }

            if (!key.equals(model.getKey())) {
                long count = repositoryService.createModelQuery().modelKey(key).count();
                if (count > 0) {
                    throw new FlowableObjectNotFoundException("ModelKey already exists with id " + key);
                }
            }

            List<Model> models = repositoryService.createModelQuery().modelKey(key).list();
            for (Model updateModel : models) {
                if (!updateModel.getId().equals(modelId)) {
                    updateModel.setKey(key);
                    updateModel.setName(name);
                    updateModel.setCategory(category);
                    repositoryService.saveModel(updateModel);
                }
            }

            // 【3】数据入库
            model.setKey(key);
            model.setName(name);
            model.setCategory(category);
            model.setMetaInfo(getMetaInfo(name, description));
            repositoryService.saveModel(model);

            if (bytes != null) {
                repositoryService.addModelEditorSource(model.getId(), bytes);

                ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
                InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", Collections.emptyList(),
                        Collections.emptyList(), processEngineConfiguration.getActivityFontName(),
                        processEngineConfiguration.getLabelFontName(),
                        processEngineConfiguration.getAnnotationFontName(),
                        processEngineConfiguration.getClassLoader(), 1.0, true);

                repositoryService.addModelEditorSourceExtra(model.getId(), IOUtils.toByteArray(resource));
            }

        } catch (Exception e) {
            throw new FlowableException("SaveModelEditorCmd error :" + e.getMessage());
        }
        return null;
    }

    protected String getMetaInfo(String name, String description) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode metaInfo = objectMapper.createObjectNode();
        metaInfo.put("name", name);
        metaInfo.put("description", description);
        return metaInfo.toString();
    }
}