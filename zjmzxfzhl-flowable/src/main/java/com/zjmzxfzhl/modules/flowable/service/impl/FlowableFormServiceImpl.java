package com.zjmzxfzhl.modules.flowable.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.flowable.entity.FlowableForm;
import com.zjmzxfzhl.modules.flowable.mapper.FlowableFormMapper;
import com.zjmzxfzhl.modules.flowable.service.FlowableFormService;

/**
 * 流程Service
 *
 * @author 庄金明
 */
@Service
public class FlowableFormServiceImpl extends BaseServiceImpl<FlowableFormMapper, FlowableForm> implements FlowableFormService {
    @Override
    public IPage<FlowableForm> list(IPage<FlowableForm> page, FlowableForm flowableForm) {
        return page.setRecords(baseMapper.list(page, flowableForm));
    }
}
