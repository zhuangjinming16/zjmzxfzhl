package com.zjmzxfzhl.common.core.redis;

import com.zjmzxfzhl.common.core.redis.aspect.components.RepeatRequestComponent;
import com.zjmzxfzhl.common.core.redis.redlock.RedissonDistributedLocker;
import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Configuration
@ConditionalOnProperty(name = "zjmzxfzhl.redisson.enabled", havingValue = "true")
public class RedissonConfig {
    @Value("${spring.redis.sentinel.nodes:}")
    private String nodes;

    @Bean
    @SneakyThrows
    public RedissonClient redissonClient() {
        Config config = null;
        if (nodes != null && nodes.length() > 0) {
            config = Config.fromYAML(new ClassPathResource("redisson-sentinel.yml").getInputStream());
        } else {
            config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
        }
        return Redisson.create(config);
    }

    @Bean
    public RedissonDistributedLocker redissonDistributedLocker() {
        return new RedissonDistributedLocker(redissonClient());
    }

    @Bean
    public RepeatRequestComponent repeatRequestComponent() {
        return new RepeatRequestComponent(redissonDistributedLocker());
    }
}
