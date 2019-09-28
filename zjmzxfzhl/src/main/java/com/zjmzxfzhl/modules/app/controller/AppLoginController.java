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

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.util.DateUtil;
import com.zjmzxfzhl.common.util.IPUtils;
import com.zjmzxfzhl.common.util.JwtUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.modules.app.common.AppConstants;
import com.zjmzxfzhl.modules.app.common.AppSessionObject;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.form.AppLoginForm;
import com.zjmzxfzhl.modules.app.service.AppUserService;

@RestController
@RequestMapping("/app")
public class AppLoginController {

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private RedisUtil redisUtil;

	@PostMapping("/login")
	public R login(@Valid @RequestBody AppLoginForm form, HttpServletRequest request) {
		AppUser appUser = appUserService.login(form);
		String userId = appUser.getUserId();
		String token = JwtUtil.sign(userId, appUser.getPassword());

		AppSessionObject appSessionObject = new AppSessionObject();
		appUser.setPassword(null);// password不存缓存
		appUser.setSalt(null);// 密码盐不缓存
		appSessionObject.setAppUser(appUser);
		appSessionObject.setUserId(userId);
		appSessionObject.setLoginTime(DateUtil.getNow());
		appSessionObject.setIpAddr(IPUtils.getIpAddr(request));
		appSessionObject.setToken(token);
		redisUtil.set(AppConstants.PREFIX_USER_APP_SESSION_OBJECT + userId, appSessionObject, JwtUtil.EXPIRE_TIME);

		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		return R.ok(map);
	}

}
