package com.zjmzxfzhl.modules.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.app.entity.AppUser;

/**
 * 用户Service
 *
 * @author 庄金明
 */
public interface AppUserService extends BaseService<AppUser> {
    /**
     * 分页查询用户
     *
     * @param page
     * @param appUser
     * @return
     */
    IPage<AppUser> list(IPage<AppUser> page, AppUser appUser);
}
