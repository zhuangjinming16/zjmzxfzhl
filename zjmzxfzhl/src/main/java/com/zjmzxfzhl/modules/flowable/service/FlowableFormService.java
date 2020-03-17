package com.zjmzxfzhl.modules.flowable.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.flowable.entity.FlowableForm;
import com.zjmzxfzhl.modules.flowable.mapper.FlowableFormMapper;

/**
 * 流程Service
 * 
 * @author 庄金明
 */
@Service
public class FlowableFormService extends BaseService<FlowableFormMapper, FlowableForm> {
	public IPage<FlowableForm> list(IPage<FlowableForm> page, FlowableForm flowableForm) {
		return page.setRecords(baseMapper.list(page, flowableForm));
	}
}
