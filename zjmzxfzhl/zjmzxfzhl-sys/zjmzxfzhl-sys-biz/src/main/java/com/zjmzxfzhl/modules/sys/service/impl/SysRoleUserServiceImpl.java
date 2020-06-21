package com.zjmzxfzhl.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.mapper.SysRoleUserMapper;
import com.zjmzxfzhl.modules.sys.service.SysRoleUserService;

/**
 * 角色和用户关系Service
 * 
 * @author 庄金明
 */
@Service
public class SysRoleUserServiceImpl extends BaseServiceImpl<SysRoleUserMapper, SysRoleUser>
        implements SysRoleUserService {
    @Override
    public IPage<SysRoleUser> list(IPage<SysRoleUser> page, SysRoleUser sysRoleUser) {
        return page.setRecords(baseMapper.list(page, sysRoleUser));
    }
}
