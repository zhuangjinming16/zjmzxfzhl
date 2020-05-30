package com.zjmzxfzhl.framework.config.threadpool;

import java.util.Map;

import org.apache.shiro.util.ThreadContext;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.zjmzxfzhl.common.util.DateUtil;

/**
 * 线程上下文信息复制
 * 
 * @author 庄金明
 */
public class ContextCopyDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Map<Object, Object> resources = ThreadContext.getResources();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                ThreadContext.setResources(resources);
                runnable.run();
            } finally {
                DateUtil.clearNow();
                ThreadContext.remove();
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }
}