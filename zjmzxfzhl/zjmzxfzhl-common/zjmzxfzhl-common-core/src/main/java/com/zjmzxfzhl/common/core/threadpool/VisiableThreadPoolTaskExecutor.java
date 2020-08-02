package com.zjmzxfzhl.common.core.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 庄金明
 */
@Slf4j
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    private static final long serialVersionUID = 1L;

    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        if (null == threadPoolExecutor) {
            return;
        }
        log.info(String.format("%s, %s,taskCount [%s], completedTaskCount [%s], activeCount [%s], queueSize [%s]",
                this.getThreadNamePrefix(), prefix, threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(), threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size()));
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("1. do execute");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("2. do execute");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("1. do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("2. do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("1. do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }
}