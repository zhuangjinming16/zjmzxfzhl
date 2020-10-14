package com.zjmzxfzhl.common.security.util;

import com.zjmzxfzhl.common.core.constant.SecurityConstants;
import com.zjmzxfzhl.common.core.exception.BaseException;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.common.security.component.resource.AuthorizeRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import java.util.List;

public class AuthorizeRequestUtil {
    public static ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry resolveAuthorizeRequest(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry, List<AuthorizeRequest> authorizeRequests) {
        if (CommonUtil.isNotEmptyObject(authorizeRequests)) {
            for (int i = 0; i < authorizeRequests.size(); i++) {
                AuthorizeRequest authorizeRequest = authorizeRequests.get(i);
                if (SecurityConstants.ANT_MATCHERS.equals(authorizeRequest.getType())) {
                    HttpMethod httpMethod = HttpMethod.resolve(authorizeRequest.getMethod());
                    String[] antMatchers = authorizeRequest.getAntPatterns().toArray(new String[0]);
                    String method = authorizeRequest.getMethod();
                    if (SecurityConstants.ACCESS.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).access(authorizeRequest.getMethodParams());
                    } else if (SecurityConstants.NOT_ACCESS.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).not().access(authorizeRequest.getMethodParams());
                    } else if (SecurityConstants.ANONYMOUS.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).anonymous();
                    } else if (SecurityConstants.DENY_ALL.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).denyAll();
                    } else if (SecurityConstants.FULLY_AUTHENTICATED.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).fullyAuthenticated();
                    } else if (SecurityConstants.HAS_ANY_AUTHORITY.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).hasAnyAuthority(authorizeRequest.getMethodParams().split(","));
                    } else if (SecurityConstants.HAS_ANY_ROLE.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).hasAnyRole(authorizeRequest.getMethodParams().split(","));
                    } else if (SecurityConstants.HAS_IP_ADDRESS.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).hasIpAddress(authorizeRequest.getMethodParams());
                    } else if (SecurityConstants.PERMIT_ALL.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).permitAll();
                    } else if (SecurityConstants.REMEMBER_ME.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).rememberMe();
                    } else if (SecurityConstants.AUTHENTICATED.equals(method)) {
                        registry.antMatchers(httpMethod, antMatchers).authenticated();
                    } else {
                        throw new BaseException("[zjmzxfzhl.resource.config]配置错误");
                    }
                } else if (SecurityConstants.ANY_REQUEST.equals(authorizeRequest.getType())) {
                    String method = authorizeRequest.getMethod();
                    if (SecurityConstants.ACCESS.equals(method)) {
                        registry.anyRequest().access(authorizeRequest.getMethodParams());
                    } else if (SecurityConstants.NOT_ACCESS.equals(method)) {
                        registry.anyRequest().not().access(authorizeRequest.getMethodParams());
                    } else if (SecurityConstants.ANONYMOUS.equals(method)) {
                        registry.anyRequest().anonymous();
                    } else if (SecurityConstants.DENY_ALL.equals(method)) {
                        registry.anyRequest().denyAll();
                    } else if (SecurityConstants.FULLY_AUTHENTICATED.equals(method)) {
                        registry.anyRequest().fullyAuthenticated();
                    } else if (SecurityConstants.HAS_ANY_AUTHORITY.equals(method)) {
                        registry.anyRequest().hasAnyAuthority(authorizeRequest.getMethodParams().split(","));
                    } else if (SecurityConstants.HAS_ANY_ROLE.equals(method)) {
                        registry.anyRequest().hasAnyRole(authorizeRequest.getMethodParams().split(","));
                    } else if (SecurityConstants.HAS_IP_ADDRESS.equals(method)) {
                        registry.anyRequest().hasIpAddress(authorizeRequest.getMethodParams());
                    } else if (SecurityConstants.PERMIT_ALL.equals(method)) {
                        registry.anyRequest().permitAll();
                    } else if (SecurityConstants.REMEMBER_ME.equals(method)) {
                        registry.anyRequest().rememberMe();
                    } else if (SecurityConstants.AUTHENTICATED.equals(method)) {
                        registry.anyRequest().authenticated();
                    } else {
                        throw new BaseException("[zjmzxfzhl.resource.config]配置错误");
                    }
                } else {
                    throw new BaseException("[zjmzxfzhl.resource.config]配置错误");
                }
            }
        } else {
            registry.anyRequest().authenticated();
        }
        return registry;
    }
}
