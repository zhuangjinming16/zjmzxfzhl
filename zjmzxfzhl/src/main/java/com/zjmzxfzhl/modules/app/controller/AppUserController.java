package com.zjmzxfzhl.modules.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.modules.app.annotation.WithoutLogin;
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
	public R getUserInfo(@RequestAttribute(AppLoginInterceptor.APP_SESSION_OBJECT) AppSessionObject appSessionObject) {
		return R.ok(appSessionObject);
	}

	@GetMapping("/getUserId")
	public R getUserId(@RequestAttribute(AppLoginInterceptor.APP_SESSION_OBJECT) AppSessionObject appSessionObject) {
		String userId = null;
		if (appSessionObject != null) {
			userId = appSessionObject.getUserId();
		}
		return R.ok(null, userId);
	}

	@WithoutLogin
	@GetMapping("/withoutLogin")
	public R withoutLogin() {
		return R.ok("不需要登陆，访问的交易，在Controller的Mapping方法使用@WithoutLogin");
	}
}
