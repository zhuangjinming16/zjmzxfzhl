package com.zjmzxfzhl.modules.sys.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysDataPermission;
import com.zjmzxfzhl.modules.sys.mapper.SysDataPermissionMapper;

/**
 * 数据权限Service
 * 
 * @author 庄金明
 */
@Service
public class SysDataPermissionService extends BaseService<SysDataPermissionMapper, SysDataPermission> {
	public IPage<SysDataPermission> list(IPage<SysDataPermission> page, SysDataPermission sysDataPermission) {
		return page.setRecords(baseMapper.list(page, sysDataPermission));
	}
}
