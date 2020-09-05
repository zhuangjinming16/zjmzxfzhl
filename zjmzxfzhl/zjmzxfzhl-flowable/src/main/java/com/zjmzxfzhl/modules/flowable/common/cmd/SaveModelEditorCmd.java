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
import org.flowable.image.ProcessDiagramGenerator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;

/**
 * 保存模型，同时生成图片
 *
 * @author 庄金明
 * @date 2020年8月30日
 */
public class SaveModelEditorCmd implements Command<Void>, Serializable {

    private static final long serialVersionUID = 1L;
    private String modelId;
    private String editor;

    public SaveModelEditorCmd(String modelId, String editor) {
        this.modelId = modelId;
        this.editor = editor;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfiguration processEngineConfiguration =
                CommandContextUtil.getProcessEngineConfiguration(commandContext);
        RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

        try {
            byte[] bytes = editor.getBytes("utf-8");
            repositoryService.addModelEditorSource(modelId, bytes);
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", Collections.emptyList(),
                    Collections.emptyList(), processEngineConfiguration.getActivityFontName(),
                    processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName()
                    , processEngineConfiguration.getClassLoader(), 1.0, true);

            repositoryService.addModelEditorSourceExtra(modelId, IOUtils.toByteArray(resource));
        } catch (Exception e) {
            throw new FlowableException("create model exception :" + e.getMessage());
        }
        return null;
    }

}