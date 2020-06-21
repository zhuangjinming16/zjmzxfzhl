package com.zjmzxfzhl.common.core.config.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author 庄金明
 **/
@EnableScheduling
@EnableAsync
@Configuration
public class ThreadPoolConfig {
    public final static String ASYNC_SERVICE_EXECUTOR = "asyncServiceExecutor";

    /**
     * 核心线程池大小
     */
    @Value("${async.executor.thread.core-pool-size}")
    private int corePoolSize = 10;
    /**
     * 最大可创建的线程数
     */
    @Value("${async.executor.thread.max-pool-size}")
    private int maxPoolSize = 20;
    /**
     * 队列最大长度
     */
    @Value("${async.executor.thread.queue-capacity}")
    private int queueCapacity = 2000;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    @Value("${async.executor.thread.keep-alive-seconds}")
    private int keepAliveSeconds = 60;
    /**
     * 线程名称
     */
    @Value("${async.executor.thread.name-prefix}")
    private String namePrefix = "async-service-";

    @Bean(name = ASYNC_SERVICE_EXECUTOR)
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        /// 若需要打印线程池工作情况使用，可能会影响性能
        /// ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("async-service-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setTaskDecorator(new ContextCopyDecorator());
        executor.initialize();
        return executor;
    }

}
