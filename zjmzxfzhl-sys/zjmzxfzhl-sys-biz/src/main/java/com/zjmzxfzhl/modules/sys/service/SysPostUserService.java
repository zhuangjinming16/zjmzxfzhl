package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;

/**
 * 岗位和用户关系Service
 *
 * @author 庄金明
 */
public interface SysPostUserService extends BaseService<SysPostUser> {
    /**
     * 分页查询岗位和用户关系
     *
     * @param page
     * @param sysPostUser
     * @return
     */
    IPage<SysPostUser> list(IPage<SysPostUser> page, SysPostUser sysPostUser);
}
