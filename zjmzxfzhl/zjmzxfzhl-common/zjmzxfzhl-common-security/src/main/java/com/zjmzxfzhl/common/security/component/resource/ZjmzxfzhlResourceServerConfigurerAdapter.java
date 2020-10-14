package com.zjmzxfzhl.common.security.component.resource;

import com.zjmzxfzhl.common.core.exception.BaseException;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.common.security.annotation.AnonymousAccess;
import com.zjmzxfzhl.common.security.annotation.Inner;
import com.zjmzxfzhl.common.security.util.AuthorizeRequestUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 庄金明
 * @date
 */
@Slf4j
public class ZjmzxfzhlResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Autowired
    protected ZjmzxfzhlAuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    private ZjmzxfzhlResourceProperties zjmzxfzhlResourceProperties;

    @Override
    @SneakyThrows
    public void configure(HttpSecurity httpSecurity) {
        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
                SpringContextUtils.getApplicationContext().getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        Set<String> innerUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
            if (zjmzxfzhlResourceProperties.getInner()) {
                Inner inner = handlerMethod.getMethodAnnotation(Inner.class);
                if (null != inner) {
                    innerUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                }
            }
        }

        httpSecurity.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                httpSecurity.authorizeRequests();
        zjmzxfzhlResourceProperties.getIgnoreUrls().forEach(url -> registry.antMatchers(url).permitAll());
        anonymousUrls.forEach(url -> registry.antMatchers(url).permitAll());
        innerUrls.forEach(url -> registry.antMatchers(url).permitAll());
        List<AuthorizeRequest> authorizeRequests = zjmzxfzhlResourceProperties.getAuthorizeRequests();
        AuthorizeRequestUtil.resolveAuthorizeRequest(registry, authorizeRequests);
        registry.and().csrf().disable();

        if (CommonUtil.isNotEmptyObject(zjmzxfzhlResourceProperties.getAddFilterBeforeUsernamePasswordAuthenticationFilter())) {
            List<String> addFilterBeforeUsernamePasswordAuthenticationFilter =
                    zjmzxfzhlResourceProperties.getAddFilterBeforeUsernamePasswordAuthenticationFilter();
            addFilterBeforeUsernamePasswordAuthenticationFilter.forEach(item -> {
                try {
                    Filter filter = (Filter) SpringContextUtils.getBean(Class.forName(item));
                    registry.and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                } catch (Exception e) {
                    throw new BaseException("设置过滤器失败");
                }
            });
        }
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authenticationEntryPointImpl);
    }

}
