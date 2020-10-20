package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysRolePermission;

/**
 * 操作权限Service
 *
 * @author 庄金明
 */
public interface SysRolePermissionService extends BaseService<SysRolePermission> {
    /**
     * 分页查询操作权限
     *
     * @param page
     * @param sysRolePermission
     * @return
     */
    IPage<SysRolePermission> list(IPage<SysRolePermission> page, SysRolePermission sysRolePermission);
}
