package com.zjmzxfzhl.modules.flowable.common.listener;

import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

/**
 * 任务节点前置监听处理类
 *
 * @author: 庄金明
 **/
@Component
public class TaskBeforeListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        TaskEntity taskEntity = (TaskEntity) ((FlowableEntityEventImpl) event).getEntity();
        if (taskEntity.getCategory() == null) {
            taskEntity.setCategory(FlowableConstant.CATEGORY_TODO);
        }
    }

    /**
     * 该isFailOnException()方法确定onEvent(..)方法在调度事件时抛出异常时的行为。
     * 当false返回，异常被忽略。当true返回，异常不会被忽略
     *
     * @return
     */
    @Override
    public boolean isFailOnException() {
        return true;
    }

    /**
     * 该isFireOnTransactionLifecycleEvent()方法确定此事件侦听器是在事件发生时立即触发,还是由getOnTransaction()方法确定的事务生命周期事件触发。
     * 支持的事务生命周期事件的值是：COMMITTED，ROLLED_BACK，COMMITTING，ROLLINGBACK。
     *
     * @return
     */
    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}