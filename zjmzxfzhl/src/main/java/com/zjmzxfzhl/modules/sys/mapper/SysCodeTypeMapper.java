package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysCodeType;

/**
 * 代码类别Mapper
 * 
 * @author 庄金明
 */
public interface SysCodeTypeMapper extends BaseMapper<SysCodeType> {
	public List<SysCodeType> list(IPage<SysCodeType> page, @Param("entity") SysCodeType entity);
}
