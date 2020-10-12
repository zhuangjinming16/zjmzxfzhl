package com.zjmzxfzhl.common.security.component.authorization;

import com.zjmzxfzhl.common.security.component.authorization.exception.ZjmzxfzhlWebResponseExceptionTranslator;
import com.zjmzxfzhl.common.security.service.RedisClientDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.Arrays;

/**
 * @author 庄金明
 */
public class ZjmzxfzhlAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisClientDetailsService redisClientDetailsService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private TokenEnhancer tokenEnhancer;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Value("${zjmzxfzhl.old-access-token-validity-seconds:20}")
    private int oldAccessTokenValiditySeconds;

    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(redisClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients()
                // .checkTokenAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 处理授权码
                .authorizationCodeServices(authorizationCodeServices)
                // 指定token存储位置
                .tokenStore(tokenStore)
                // 自定义生成令牌
                .tokenEnhancer(tokenEnhancer)
                // 用户账号密码认证
                .userDetailsService(userDetailsService)
                // 指定认证管理器
                .authenticationManager(authenticationManager)
                // 是否重复使用 refresh_token
                .reuseRefreshTokens(false)
                // ZjmzxfzhlTokenServices
                .tokenServices(tokenServices(tokenStore, tokenEnhancer, endpoints))
                // 异常处理，也可自定义
                .exceptionTranslator(new ZjmzxfzhlWebResponseExceptionTranslator());
    }

    private ZjmzxfzhlTokenServices tokenServices(TokenStore tokenStore, TokenEnhancer tokenEnhancer,
                                                 AuthorizationServerEndpointsConfigurer endpoints) {
        ZjmzxfzhlTokenServices tokenServices = new ZjmzxfzhlTokenServices(oldAccessTokenValiditySeconds);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        addUserDetailsService(tokenServices, userDetailsService);

        return tokenServices;
    }

    private void addUserDetailsService(ZjmzxfzhlTokenServices tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        }
    }
}
