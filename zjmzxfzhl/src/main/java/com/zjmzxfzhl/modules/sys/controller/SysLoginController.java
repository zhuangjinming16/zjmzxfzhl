package com.zjmzxfzhl.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.JwtUtil;
import com.zjmzxfzhl.common.util.PasswordUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.SysLoginForm;
import com.zjmzxfzhl.modules.sys.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
@RequestMapping("/sys")
@Slf4j
@Api(tags = "后台用户登录")
public class SysLoginController {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private Producer producer;

	@GetMapping("/captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 保存到 redis,60秒
		redisUtil.set(Constants.PREFIX_USER_CAPTCHA + uuid, text, 60);
		// 获取图片验证码
		BufferedImage image = producer.createImage(text);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(image, "jpg", out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@SysLogAuto(value = "用户登录", logType = "1")
	@PostMapping(value = "/login")
	@ApiOperation("用户登录")
	@ApiResponses({ @ApiResponse(code = 200, message = "登录成功，返回数据data包含token:\ndata={\"token\":\"xxx\"}") })
	public Result login(@RequestBody SysLoginForm sysLoginForm) {
		CommonUtil.isEmptyStr(sysLoginForm.getUserId(), "用户名不能为空");
		CommonUtil.isEmptyStr(sysLoginForm.getPassword(), "密码不能为空");
		CommonUtil.isEmptyStr(sysLoginForm.getUuid(), "验证码uuid不能为空");
		CommonUtil.isEmptyStr(sysLoginForm.getCaptcha(), "验证码不能为空");

		String cacheCaptcha = (String) redisUtil.get(Constants.PREFIX_USER_CAPTCHA + sysLoginForm.getUuid());
		if (!sysLoginForm.getCaptcha().equals(cacheCaptcha)) {
			return Result.error("验证码错误或已失效");
		}
		// 验证码验证通过后，应立即删除缓存，防止恶意暴力破解密码
		redisUtil.del(Constants.PREFIX_USER_CAPTCHA + sysLoginForm.getUuid());

		String userId = sysLoginForm.getUserId();
		SysUser sysUser = sysUserService.getById(userId);
		CommonUtil.isEmptyObject(sysUser, "该用户不存在");

		String password = PasswordUtil.encrypt(sysLoginForm.getPassword(), sysUser.getSalt());
		if (!password.equals(sysUser.getPassword())) {
			return Result.error("用户名或密码错误");
		}
		// 生成token,不传入token过期时间，在使用JwtUtil.verify时不会校验过期时间
		String token = JwtUtil.sign(userId, password);
		// 使用redis管理token过期时间
		redisUtil.set(Constants.PREFIX_USER_TOKEN + userId, token, JwtUtil.EXPIRE_TIME);
		HashMap<String, String> obj = new HashMap<>(1);
		obj.put("token", token);
		return Result.ok(obj);
	}

	/**
	 * 注销登录
	 * 
	 * @param username
	 * @return
	 */
	@SysLogAuto(value = "用户注销")
	@RequestMapping(value = "/logout")
	public Result logout(HttpServletRequest request, HttpServletResponse response) {
		// 用户注销逻辑
		Subject subject = ShiroUtils.getSubject();
		SessionObject sessionObject = (SessionObject) subject.getPrincipal();
		if (sessionObject == null) {
			return Result.ok("注销成功！");
		}
		SysUser sysUser = sessionObject.getSysUser();
		log.info("用户名:" + sysUser.getUserName() + ",注销成功！ ");
		subject.logout();
		// 清空用户Token缓存
		redisUtil.del(Constants.PREFIX_USER_TOKEN + sysUser.getUserId());
		// 清空用户sessionObject缓存
		redisUtil.del(Constants.PREFIX_USER_SESSION_OBJECT + sysUser.getUserId());
		return Result.ok("注销成功！");
	}
}
