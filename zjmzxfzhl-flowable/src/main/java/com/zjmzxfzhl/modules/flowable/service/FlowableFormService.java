package com.zjmzxfzhl.modules.flowable.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.flowable.entity.FlowableForm;

/**
 * 流程表单Service
 *
 * @author 庄金明
 */
public interface FlowableFormService extends BaseService<FlowableForm> {
    /**
     * 分页查询流程表单
     *
     * @param page
     * @param flowableForm
     * @return
     */
    IPage<FlowableForm> list(IPage<FlowableForm> page, FlowableForm flowableForm);
}
