package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;

/**
 * 机构Mapper
 * 
 * @author 庄金明
 */
public interface SysOrgMapper extends BaseMapper<SysOrg> {
	public List<SysOrg> list(IPage<SysOrg> page, @Param("entity") SysOrg entity);
}
