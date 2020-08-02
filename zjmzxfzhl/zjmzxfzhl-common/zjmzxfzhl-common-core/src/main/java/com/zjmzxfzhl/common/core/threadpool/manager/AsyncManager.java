package com.zjmzxfzhl.common.core.threadpool.manager;

import com.zjmzxfzhl.common.core.threadpool.ThreadPoolConfig;
import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 异步任务管理器
 *
 * @author 庄金明
 */
public class AsyncManager {
    /**
     * 异步操作任务调度线程池
     */
    private ThreadPoolTaskExecutor asyncServiceExecutor =
            SpringContextUtils.getBean(ThreadPoolConfig.ASYNC_SERVICE_EXECUTOR);

    /**
     * 单例模式
     */
    private AsyncManager() {
    }

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

    public void execute(Runnable task) {
        asyncServiceExecutor.execute(task);
    }

    public Future<?> submit(Runnable task) {
        return asyncServiceExecutor.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return asyncServiceExecutor.submit(task);
    }

    public ListenableFuture<?> submitListenable(Runnable task) {
        return asyncServiceExecutor.submitListenable(task);
    }

    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return asyncServiceExecutor.submitListenable(task);
    }
}
