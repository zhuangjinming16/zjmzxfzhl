package com.zjmzxfzhl.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.sys.entity.SysRolePermission;
import com.zjmzxfzhl.modules.sys.mapper.SysRolePermissionMapper;
import com.zjmzxfzhl.modules.sys.service.SysRolePermissionService;

/**
 * 操作权限Service
 *
 * @author 庄金明
 */
@Service
public class SysRolePermissionServiceImpl extends BaseServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {
    @Override
    public IPage<SysRolePermission> list(IPage<SysRolePermission> page, SysRolePermission sysRolePermission) {
        return page.setRecords(baseMapper.list(page, sysRolePermission));
    }
}
