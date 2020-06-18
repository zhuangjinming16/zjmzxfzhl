package com.zjmzxfzhl.framework.config.security;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 庄金明
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
        // "/configuration/security",
        // "/swagger-ui.html", "/webjars/**", "/doc.html", "/login.html");
        // web.ignoring().antMatchers("/js/**");
        // web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/static/**", "/favicon.ico");
        // 忽略登录界面
        // web.ignoring().antMatchers("/login.html");
        // web.ignoring().antMatchers("/index.html");
        // web.ignoring().antMatchers("/oauth/user/token");
        // web.ignoring().antMatchers("/oauth/client/token");
        // web.ignoring().antMatchers("/validata/code/**");
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
