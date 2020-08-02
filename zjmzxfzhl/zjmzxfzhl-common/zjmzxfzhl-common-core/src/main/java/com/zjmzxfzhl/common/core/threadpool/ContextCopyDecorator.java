package com.zjmzxfzhl.common.core.threadpool;

import com.zjmzxfzhl.common.core.util.DateUtil;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 线程上下文信息复制
 *
 * @author 庄金明
 */
public class ContextCopyDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                SecurityContextHolder.setContext(securityContext);
                runnable.run();
            } finally {
                DateUtil.clearNow();
                SecurityContextHolder.clearContext();
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }
}