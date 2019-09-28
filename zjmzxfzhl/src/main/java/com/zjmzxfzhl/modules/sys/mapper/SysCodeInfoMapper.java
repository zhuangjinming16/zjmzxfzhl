package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysCodeInfo;

/**
 * 代码信息Mapper
 * 
 * @author 庄金明
 */
public interface SysCodeInfoMapper extends BaseMapper<SysCodeInfo> {
	public List<SysCodeInfo> list(IPage<SysCodeInfo> page, @Param("entity") SysCodeInfo entity);
}
