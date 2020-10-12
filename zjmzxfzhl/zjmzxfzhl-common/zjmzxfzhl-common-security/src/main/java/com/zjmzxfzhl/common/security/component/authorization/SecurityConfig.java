package com.zjmzxfzhl.common.security.component.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author 庄金明
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Value("${zjmzxfzhl.authorization.serverType:1}")
    private String serverType;

    /**
     * 解决 无法直接注入 AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // serverType=0，服务器只做认证服务器
        if ("0".equals(serverType)) {
            http.httpBasic().and().authorizeRequests().antMatchers("/actuator/**", "/token/**").permitAll().anyRequest().authenticated().and().csrf().disable();
        }
        // serverType=1，服务器即是认证服务器也是资源服务器
        else if ("1".equals(serverType)) {
            http.httpBasic().and().authorizeRequests()
                    // security保护获取code和确认授权
                    .antMatchers("/oauth/authorize", "/oauth/confirm_access").authenticated()
                    // 其余请求放行，但要被资源服务器保护
                    .anyRequest().permitAll().and().csrf().disable();
        }

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/favicon.ico");
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
