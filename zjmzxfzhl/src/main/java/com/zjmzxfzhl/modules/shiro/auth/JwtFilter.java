package com.zjmzxfzhl.modules.shiro.auth;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.util.JacksonUtil;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			executeLogin(request, response);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader(Constants.X_ACCESS_TOKEN);
		if (token == null || token.length() == 0) {
			token = httpServletRequest.getParameter(Constants.X_ACCESS_TOKEN);
		}
		JwtToken jwtToken = new JwtToken(token);
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(jwtToken);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

	/**
	 * 返回自定错误信息
	 */
	@Override
	protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
		try {
			response.setContentType("application/json;charset=utf-8");
			Result r = Result.error(801, "token已失效，请重新登录");
			String json = JacksonUtil.objToStr(r);
			response.getWriter().print(json);
		} catch (IOException e1) {
		}
		return false;
	}

	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		System.out.println(httpServletRequest.getRequestURL());
		return super.preHandle(request, response);
	}

}
