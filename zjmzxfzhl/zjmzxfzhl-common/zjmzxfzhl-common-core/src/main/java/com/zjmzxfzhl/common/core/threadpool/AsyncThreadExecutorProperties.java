package com.zjmzxfzhl.common.core.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 庄金明
 */
@Data
@ConfigurationProperties(prefix = "zjmzxfzhl.async-thread-executor")
public class AsyncThreadExecutorProperties {
    /**
     * 是否开启线程池
     */
    private Boolean enabled = false;

    /**
     * 核心线程池大小
     */
    private int corePoolSize = 10;
    /**
     * 最大可创建的线程数
     */
    private int maxPoolSize = 20;
    /**
     * 队列最大长度
     */
    private int queueCapacity = 2000;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds = 60;
    /**
     * 线程名称
     */
    private String namePrefix = "async-service-";

}
