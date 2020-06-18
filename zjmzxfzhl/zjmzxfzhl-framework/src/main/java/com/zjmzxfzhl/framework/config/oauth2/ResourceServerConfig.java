package com.zjmzxfzhl.framework.config.oauth2;

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

import com.zjmzxfzhl.common.util.SpringContextUtils;
import com.zjmzxfzhl.framework.config.oauth2.filter.CaptchaFilter;
import com.zjmzxfzhl.framework.config.security.AuthenticationEntryPointImpl;
import com.zjmzxfzhl.framework.config.security.annotation.AnonymousAccess;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    private CaptchaFilter captchaFilter;

    // @Autowired
    // private ResourceServerProperties resourceServerProperties;
    // @Autowired
    // private OAuth2ClientProperties oAuth2ClientProperties;

    // @Bean
    // public AuthIgnoreConfig authIgnoreConfig() {
    // return new AuthIgnoreConfig();
    // }

    // @Bean
    // public RestTemplate restTemplate() {
    // RestTemplate restTemplate = new RestTemplate();
    // restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    // return restTemplate;
    // }

    // @Bean
    // public ResourceServerTokenServices tokenServices() {
    // RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
    // DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
    // // UserAuthenticationConverter userTokenConverter = new CommonUserConverter();
    // // accessTokenConverter.setUserTokenConverter(userTokenConverter);
    // remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
    // remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
    // remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());
    // remoteTokenServices.setRestTemplate(restTemplate());
    // remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
    // return remoteTokenServices;
    // }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.requestMatchers().antMatchers("/app/**").and().authorizeRequests().anyRequest().authenticated();

        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = SpringContextUtils.getApplicationContext()
                .getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
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
                // // 授权异常
                // .exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl)
                // 防止iframe 造成跨域
                // .and()
                .headers().frameOptions().disable()
                // 不创建会话
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 请求配置
                .and().authorizeRequests()
                // demo
                .antMatchers("/", "/demo/helloworld/helloworld", "/demo/redlock/*").permitAll()
                // OAuth2
                .antMatchers("/oauth/*", "/token/**").permitAll()
                // 静态资源等等
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.scss", "/**/*.woff",
                        "/**/*.ttf", "/**/*.eot", "/**/*.gif", "/**/*.jpg", "/**/*.png", "/**/*.ico", "/**/*.svg",
                        "/**/*.js")
                .permitAll()
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
                // .anyRequest().authenticated();
                .and().addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authenticationEntryPointImpl);
    }
}
