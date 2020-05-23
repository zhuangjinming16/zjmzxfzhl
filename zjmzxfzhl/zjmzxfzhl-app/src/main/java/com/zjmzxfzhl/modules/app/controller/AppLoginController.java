package com.zjmzxfzhl.modules.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.app.base.AppConstants;
import com.zjmzxfzhl.common.app.base.AppSessionObject;
import com.zjmzxfzhl.common.util.DateUtil;
import com.zjmzxfzhl.common.util.IpUtils;
import com.zjmzxfzhl.common.util.JwtUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.form.AppLoginForm;
import com.zjmzxfzhl.modules.app.service.AppUserService;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
@RequestMapping("/app")
public class AppLoginController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public Result login(@Valid @RequestBody AppLoginForm form, HttpServletRequest request) {
        AppUser appUser = appUserService.login(form);
        String userId = appUser.getUserId();
        String token = JwtUtil.sign(userId, appUser.getPassword());

        AppSessionObject appSessionObject = new AppSessionObject();
        // password不存缓存
        appUser.setPassword(null);
        // 密码盐不缓存
        appUser.setSalt(null);
        appSessionObject.setUserId(userId);
        appSessionObject.setUserName(appUser.getUserName());
        appSessionObject.setLoginTime(DateUtil.getNow());
        appSessionObject.setIpAddr(IpUtils.getIpAddr(request));
        appSessionObject.setToken(token);
        redisUtil.set(AppConstants.PREFIX_USER_APP_SESSION_OBJECT + userId, appSessionObject, JwtUtil.EXPIRE_TIME);

        Map<String, Object> map = new HashMap<>(1);
        map.put("token", token);
        return Result.ok(map);
    }

}
