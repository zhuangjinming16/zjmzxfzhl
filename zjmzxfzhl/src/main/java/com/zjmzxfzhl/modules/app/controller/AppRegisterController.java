package com.zjmzxfzhl.modules.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.util.PasswordUtil;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.form.AppRegisterForm;
import com.zjmzxfzhl.modules.app.service.AppUserService;

@RestController
@RequestMapping("/app")
public class AppRegisterController {
	@Autowired
	private AppUserService appUserService;

	@PostMapping("/register")
	public R register(@RequestBody AppRegisterForm form) {
		String salt = PasswordUtil.randomGen(8);
		String password = PasswordUtil.encrypt(form.getPassword(), salt);// 默认密码
		AppUser appUser = new AppUser();
		appUser.setUserId(form.getMobile());
		appUser.setMobile(form.getMobile());
		appUser.setUserName(form.getMobile());
		appUser.setSalt(salt);
		appUser.setPassword(password);
		appUser.setCreateBy(form.getMobile());
		appUserService.save(appUser);
		return R.ok();
	}
}
