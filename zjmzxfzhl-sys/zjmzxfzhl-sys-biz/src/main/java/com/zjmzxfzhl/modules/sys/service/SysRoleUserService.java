package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;

/**
 * 角色和用户关系Service
 *
 * @author 庄金明
 */
public interface SysRoleUserService extends BaseService<SysRoleUser> {
    /**
     * 分页查询角色和用户关系
     *
     * @param page
     * @param sysRoleUser
     * @return
     */
    IPage<SysRoleUser> list(IPage<SysRoleUser> page, SysRoleUser sysRoleUser);
}
