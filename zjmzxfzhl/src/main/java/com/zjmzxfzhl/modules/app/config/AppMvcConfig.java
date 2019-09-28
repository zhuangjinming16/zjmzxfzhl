package com.zjmzxfzhl.modules.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zjmzxfzhl.modules.app.interceptor.AppLoginInterceptor;

@Configuration
public class AppMvcConfig implements WebMvcConfigurer {
	@Autowired
	private AppLoginInterceptor authorizationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**").excludePathPatterns("/app/login", "/app/register");
	}
}