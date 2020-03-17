package com.zjmzxfzhl.modules.flowable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.flowable.entity.FlowableForm;

/**
 * 流程Mapper
 * 
 * @author 庄金明
 */
public interface FlowableFormMapper extends BaseMapper<FlowableForm> {
	public List<FlowableForm> list(IPage<FlowableForm> page, @Param("entity") FlowableForm entity);
}
