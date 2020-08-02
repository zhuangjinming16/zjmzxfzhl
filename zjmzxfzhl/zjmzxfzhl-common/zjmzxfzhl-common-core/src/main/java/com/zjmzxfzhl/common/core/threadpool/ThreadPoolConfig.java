package com.zjmzxfzhl.common.core.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author 庄金明
 **/
@EnableScheduling
@EnableAsync
@Configuration
@EnableConfigurationProperties(AsyncThreadExecutorProperties.class)
@ConditionalOnProperty(name = "zjmzxfzhl.async-thread-executor.enabled", havingValue = "true")
public class ThreadPoolConfig {
    public final static String ASYNC_SERVICE_EXECUTOR = "asyncServiceExecutor";

    @Autowired
    private AsyncThreadExecutorProperties asyncThreadExecutorProperties;

    @Bean(name = ASYNC_SERVICE_EXECUTOR)
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        /// 若需要打印线程池工作情况使用，可能会影响性能
        /// ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(asyncThreadExecutorProperties.getMaxPoolSize());
        executor.setCorePoolSize(asyncThreadExecutorProperties.getCorePoolSize());
        executor.setQueueCapacity(asyncThreadExecutorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncThreadExecutorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(asyncThreadExecutorProperties.getNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setTaskDecorator(new ContextCopyDecorator());
        executor.initialize();
        return executor;
    }

}
