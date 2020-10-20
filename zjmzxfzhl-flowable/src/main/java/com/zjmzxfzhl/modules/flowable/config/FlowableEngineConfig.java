package com.zjmzxfzhl.modules.flowable.config;

import com.google.common.collect.Maps;
import com.zjmzxfzhl.modules.flowable.common.listener.TaskBeforeListener;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Configuration
public class FlowableEngineConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    @Value("${zjmzxfzhl.flowable.font-name}")
    private String flowableFontName;

    @Autowired
    private TaskBeforeListener taskBeforeListener;

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setProcessDiagramGenerator(processDiagramGenerator());
        engineConfiguration.setActivityFontName(flowableFontName);
        engineConfiguration.setLabelFontName(flowableFontName);
        engineConfiguration.setAnnotationFontName(flowableFontName);
        engineConfiguration.setTypedEventListeners(customFlowableListeners());
    }

    @Bean
    public ProcessDiagramGenerator processDiagramGenerator() {
        return new CustomProcessDiagramGenerator();
    }

    private Map<String, List<FlowableEventListener>> customFlowableListeners () {
        Map<String, List<FlowableEventListener>> listenerMap = Maps.newHashMap();
        listenerMap.put(FlowableEngineEventType.TASK_CREATED.name(),
                new ArrayList<>(Collections.singletonList(taskBeforeListener)));
        return listenerMap;
    }
}
