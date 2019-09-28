package com.zjmzxfzhl.modules.sys.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.mapper.SysRoleUserMapper;

/**
 * 角色和用户关系Service
 * 
 * @author 庄金明
 */
@Service
public class SysRoleUserService extends BaseService<SysRoleUserMapper, SysRoleUser> {
	public IPage<SysRoleUser> list(IPage<SysRoleUser> page, SysRoleUser sysRoleUser) {
		return page.setRecords(baseMapper.list(page, sysRoleUser));
	}
}
