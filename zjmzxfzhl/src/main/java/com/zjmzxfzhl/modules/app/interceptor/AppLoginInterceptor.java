package com.zjmzxfzhl.modules.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zjmzxfzhl.common.exception.AppException;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.JwtUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.modules.app.annotation.WithoutLogin;
import com.zjmzxfzhl.modules.app.common.AppConstants;
import com.zjmzxfzhl.modules.app.common.AppSessionObject;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.service.AppUserService;

@Component
public class AppLoginInterceptor extends HandlerInterceptorAdapter {
	public static final String APP_SESSION_OBJECT = "appSessionObject";

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		String token = request.getHeader(AppConstants.X_ACCESS_TOKEN);
		if (CommonUtil.isEmptyStr(token)) { // 用户未送token，需要验证是否是免登接口
			if (((HandlerMethod) handler).getMethodAnnotation(WithoutLogin.class) == null) {// 非免登接口提示用户登录
				throw new AppException(AppConstants.X_ACCESS_TOKEN + "不能为空");
			}
			return true;
		} else {// 若用户有送token一定要验证token有效性，即使访问的是免登接口也要验证，不然用户会以为自己已经登陆了
			String userId = JwtUtil.getUserId(token);
			AppUser appUser = appUserService.getById(userId);
			if (appUser == null) {
				throw new AppException("token已失效，请重新登录");
			}
			String appSessionObjectKey = AppConstants.PREFIX_USER_APP_SESSION_OBJECT + userId;
			AppSessionObject appSessionObject = (AppSessionObject) redisUtil.get(appSessionObjectKey);
			if (appSessionObject == null) {
				throw new AppException("token已失效，请重新登录");
			}
			if (!token.equals(appSessionObject.getToken()) || !JwtUtil.verify(token, userId, appUser.getPassword())) {
				throw new AppException("token已失效，请重新登录");
			}

			redisUtil.expire(appSessionObjectKey, JwtUtil.EXPIRE_TIME);
			// 向request注入appSessionObject对象，可以在任何地方使用如下代码获取
			// AppSessionObject appSessionObject = (AppSessionObject) RequestContextHolder.getRequestAttributes().getAttribute(AppLoginInterceptor.APP_SESSION_OBJECT, RequestAttributes.SCOPE_REQUEST);
			request.setAttribute(APP_SESSION_OBJECT, appSessionObject);
		}

		return true;
	}
}
