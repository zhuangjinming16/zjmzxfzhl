package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysDataPermission;

/**
 * 数据权限Service
 *
 * @author 庄金明
 */
public interface SysDataPermissionService extends BaseService<SysDataPermission> {
    /**
     * 分页查询数据权限
     *
     * @param page
     * @param sysDataPermission
     * @return
     */
    IPage<SysDataPermission> list(IPage<SysDataPermission> page, SysDataPermission sysDataPermission);
}
