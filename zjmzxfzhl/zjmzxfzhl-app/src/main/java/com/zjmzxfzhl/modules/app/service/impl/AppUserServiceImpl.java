package com.zjmzxfzhl.modules.app.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseServiceImpl;
import com.zjmzxfzhl.common.exception.AppException;
import com.zjmzxfzhl.common.util.PasswordUtil;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.form.AppLoginForm;
import com.zjmzxfzhl.modules.app.mapper.AppUserMapper;
import com.zjmzxfzhl.modules.app.service.AppUserService;

/**
 * 用户Service
 * 
 * @author 庄金明
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUserMapper, AppUser> implements AppUserService {
    @Override
    public IPage<AppUser> list(IPage<AppUser> page, AppUser appUser) {
        return page.setRecords(baseMapper.list(page, appUser));
    }

    @Override
    public AppUser login(AppLoginForm form) {
        AppUser appUser = baseMapper.selectOne(new QueryWrapper<AppUser>().eq("mobile", form.getMobile()));
        if (appUser == null) {
            throw new AppException("手机号或密码错误");
        }
        if (PasswordUtil.matchesPassword(form.getPassword(), appUser.getPassword())) {
            throw new AppException("手机号或密码错误");
        }
        return appUser;
    }
}
