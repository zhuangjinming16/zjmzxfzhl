package com.zjmzxfzhl.modules.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.form.AppLoginForm;

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

    /**
     * 登录App
     * 
     * @param form
     * @return
     */
    public AppUser login(AppLoginForm form);
}
