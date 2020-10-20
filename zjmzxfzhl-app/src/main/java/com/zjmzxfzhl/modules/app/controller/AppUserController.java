package com.zjmzxfzhl.modules.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseController;
import com.zjmzxfzhl.common.core.security.SecurityUser;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.service.AppUserService;

/**
 * 用户Controller
 *
 * @author 庄金明
 */
@RestController
@RequestMapping("/app")
public class AppUserController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/getUserInfo")
    public Result getUserInfo() {
        SecurityUser user = (SecurityUser) SecurityUtils.getUserDetails();
        AppUser appUser = appUserService.getById(user.getUsername());
        return Result.ok(appUser);
    }
}
