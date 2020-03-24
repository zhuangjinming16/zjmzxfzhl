package com.zjmzxfzhl.common.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zjmzxfzhl.common.util.DateUtil;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		try {
			chain.doFilter(xssRequest, response);
		} finally {
			// 请求结束后，清空当前线程时间，防止下次请求继续拿到的是之前请求的时间
			DateUtil.clearNow();
		}
	}

	@Override
	public void destroy() {
	}

}