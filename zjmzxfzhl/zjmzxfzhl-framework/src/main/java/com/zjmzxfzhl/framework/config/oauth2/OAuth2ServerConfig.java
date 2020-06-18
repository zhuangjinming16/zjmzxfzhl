package com.zjmzxfzhl.framework.config.oauth2;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import com.zjmzxfzhl.framework.config.oauth2.service.RedisAuthorizationCodeServices;
import com.zjmzxfzhl.framework.config.oauth2.service.RedisClientDetailsService;

@Configuration
public class OAuth2ServerConfig {

    @Resource
    private DataSource dataSource;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 声明 ClientDetails实现
     */
    @Bean(value = "redisClientDetailsService")
    public RedisClientDetailsService redisClientDetailsService() {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }

    /**
     * @return RandomValueAuthorizationCodeServices
     */
    @Bean
    public RandomValueAuthorizationCodeServices authorizationCodeServices() {
        RedisAuthorizationCodeServices redisAuthorizationCodeServices = new RedisAuthorizationCodeServices();
        redisAuthorizationCodeServices.setRedisTemplate(redisTemplate);
        return redisAuthorizationCodeServices;
    }

}
