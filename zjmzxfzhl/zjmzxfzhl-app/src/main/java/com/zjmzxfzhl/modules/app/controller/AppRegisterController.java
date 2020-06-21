package com.zjmzxfzhl.modules.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.exception.AppException;
import com.zjmzxfzhl.common.core.util.PasswordUtil;
import com.zjmzxfzhl.common.security.annotation.AnonymousAccess;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.form.AppRegisterForm;
import com.zjmzxfzhl.modules.app.service.AppUserService;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
@RequestMapping("/app")
public class AppRegisterController {
    @Autowired
    private AppUserService appUserService;

    @AnonymousAccess
    @PostMapping("/register")
    public Result register(@RequestBody AppRegisterForm form) {
        AppUser appUser = this.appUserService.getById(form.getMobile());
        if (appUser != null) {
            throw new AppException("用户已存在");
        }
        // 默认密码
        String password = PasswordUtil.encryptPassword(form.getPassword());
        appUser = new AppUser();
        appUser.setUserId(form.getMobile());
        appUser.setMobile(form.getMobile());
        appUser.setUserName(form.getMobile());
        appUser.setPassword(password);
        appUser.setCreateBy(form.getMobile());
        appUserService.save(appUser);
        return Result.ok();
    }
}
