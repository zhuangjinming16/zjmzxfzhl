package com.zjmzxfzhl.modules.sys.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.mapper.SysPostUserMapper;

/**
 * 岗位和用户关系Service
 * 
 * @author 庄金明
 */
@Service
public class SysPostUserService extends BaseService<SysPostUserMapper, SysPostUser> {
	public IPage<SysPostUser> list(IPage<SysPostUser> page, SysPostUser sysPostUser) {
		return page.setRecords(baseMapper.list(page, sysPostUser));
	}
}
