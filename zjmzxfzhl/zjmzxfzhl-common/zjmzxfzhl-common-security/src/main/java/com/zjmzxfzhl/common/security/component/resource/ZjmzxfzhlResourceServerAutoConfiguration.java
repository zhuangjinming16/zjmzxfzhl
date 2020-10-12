package com.zjmzxfzhl.common.security.component.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjmzxfzhl.common.security.service.RedisClientDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

/**
 * @author 庄金明
 * @date 2020年6月23日
 */
public class ZjmzxfzhlResourceServerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisClientDetailsService redisClientDetailsService(DataSource dataSource,RedisTemplate<String, Object> redisTemplate) {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setRedisTemplate(redisTemplate);
        return clientDetailsService;
    }

    @Bean
    public ZjmzxfzhlAuthenticationEntryPointImpl authenticationEntryPointImpl(ObjectMapper objectMapper){
        return new ZjmzxfzhlAuthenticationEntryPointImpl(objectMapper);
    }
}
