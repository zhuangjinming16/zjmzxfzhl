package com.zjmzxfzhl.common.security.component.authorization;

import com.zjmzxfzhl.common.core.constant.CacheConstants;
import com.zjmzxfzhl.common.core.security.SecurityUser;
import com.zjmzxfzhl.common.security.component.authorization.controller.TokenController;
import com.zjmzxfzhl.common.security.component.authorization.listener.AuthenticationSuccessEventListener;
import com.zjmzxfzhl.common.security.service.RedisAuthorizationCodeServices;
import com.zjmzxfzhl.common.security.service.RedisClientDetailsService;
import com.zjmzxfzhl.common.security.service.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 庄金明
 * @date 2020年6月23日
 */
public class ZjmzxfzhlAuthorizationServerAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix="zjmzxfzhl.security.config")
    public ZjmzxfzhlSecurityProperties zjmzxfzhlSecurityProperties(){
        return new ZjmzxfzhlSecurityProperties();
    }

    @Bean
    public TokenController tokenController(){
        return new TokenController();
    }

    @Bean
    @ConditionalOnMissingBean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisClientDetailsService redisClientDetailsService(DataSource dataSource, RedisTemplate redisTemplate) {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcClientDetailsService redisClientDetailsService){
        return new UserDetailsServiceImpl(redisClientDetailsService);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(RedisTemplate redisTemplate) {
        RedisAuthorizationCodeServices redisAuthorizationCodeServices = new RedisAuthorizationCodeServices();
        redisAuthorizationCodeServices.setRedisTemplate(redisTemplate);
        return redisAuthorizationCodeServices;
    }

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CacheConstants.OAUTH_ACCESS);
        return tokenStore;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (accessToken instanceof DefaultOAuth2AccessToken && authentication.getUserAuthentication() != null) {
                final Map<String, Object> tokenEnhancerInfo = new HashMap<>(16);
                SecurityUser securityUser = (SecurityUser) authentication.getUserAuthentication().getPrincipal();
                tokenEnhancerInfo.put("userRealName", securityUser.getUserRealName());
                tokenEnhancerInfo.put("orgId", securityUser.getOrgId());
                tokenEnhancerInfo.put("roleId", securityUser.getRoleId());
                tokenEnhancerInfo.put("additionalInformation", securityUser.getAdditionalInformation());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(tokenEnhancerInfo);
            }
            return accessToken;
        };
    }

    @Bean
    public AuthenticationSuccessEventListener authenticationSuccessEventListener(){
        return new AuthenticationSuccessEventListener();
    }
}
