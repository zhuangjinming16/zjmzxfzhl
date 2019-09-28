package com.zjmzxfzhl.modules.sys.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysRolePermission;
import com.zjmzxfzhl.modules.sys.mapper.SysRolePermissionMapper;

/**
 * 操作权限Service
 * 
 * @author 庄金明
 */
@Service
public class SysRolePermissionService extends BaseService<SysRolePermissionMapper, SysRolePermission> {
	public IPage<SysRolePermission> list(IPage<SysRolePermission> page, SysRolePermission sysRolePermission) {
		return page.setRecords(baseMapper.list(page, sysRolePermission));
	}
}
