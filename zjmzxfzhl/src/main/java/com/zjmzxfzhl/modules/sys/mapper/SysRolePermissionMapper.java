package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysRolePermission;

/**
 * 操作权限Mapper
 * 
 * @author 庄金明
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
	public List<SysRolePermission> list(IPage<SysRolePermission> page, @Param("entity") SysRolePermission entity);
}
