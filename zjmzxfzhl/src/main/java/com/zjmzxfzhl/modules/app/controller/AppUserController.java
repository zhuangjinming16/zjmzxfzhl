package com.zjmzxfzhl.modules.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.modules.app.common.AppSessionObject;
import com.zjmzxfzhl.modules.app.interceptor.AppLoginInterceptor;

/**
 * 用户Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/app")
public class AppUserController extends BaseController {

    @GetMapping("/getUserInfo")
    public Result getUserInfo(
            @RequestAttribute(AppLoginInterceptor.APP_SESSION_OBJECT) AppSessionObject appSessionObject) {
        return Result.ok(appSessionObject);
    }

    @GetMapping("/getUserId")
    public Result getUserId(
            @RequestAttribute(AppLoginInterceptor.APP_SESSION_OBJECT) AppSessionObject appSessionObject) {
        String userId = null;
        if (appSessionObject != null) {
            userId = appSessionObject.getUserId();
        }
        return Result.ok(null, userId);
    }
}
