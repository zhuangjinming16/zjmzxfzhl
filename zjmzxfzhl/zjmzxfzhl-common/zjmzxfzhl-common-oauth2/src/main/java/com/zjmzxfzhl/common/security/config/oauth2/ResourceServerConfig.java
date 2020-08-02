package com.zjmzxfzhl.common.security.config.oauth2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.common.security.annotation.AnonymousAccess;
import com.zjmzxfzhl.common.security.config.AuthenticationEntryPointImpl;
import com.zjmzxfzhl.common.security.config.oauth2.filter.CaptchaFilter;

/**
 * @author 庄金明
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
                SpringContextUtils.getApplicationContext().getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }

        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                // 防止iframe 造成跨域
                .headers().frameOptions().disable()
                // 不创建会话
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 请求配置
                .and().authorizeRequests()
                // demo
                .antMatchers("/", "/demo/helloworld/helloworld", "/demo/redlock/*").permitAll()
                // OAuth2
                .antMatchers("/oauth/token", "/oauth/token_key", "/oauth/error", "/token/**").permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html", "/csrf", "/**/*swagger*/**", "/v2/**", "/webjars/**").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                // app
                .antMatchers("/app/**").access("#oauth2.hasScope('app')")
                // 其他所有请求都需要认证
                .anyRequest().access("#oauth2.hasScope('admin')")
                /// .anyRequest().authenticated();
                .and().addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authenticationEntryPointImpl);
    }
}
