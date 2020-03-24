package com.zjmzxfzhl.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Configuration
public class RedissonConfig {
	@Value("${spring.redis.sentinel.nodes:}")
	private String nodes;

	@Bean
	public RedissonClient redissonClient() throws IOException {
		Config config = null;
		if (nodes != null && nodes.length() > 0) {
			config = Config.fromYAML(new ClassPathResource("redisson-sentinel.yml").getInputStream());
			return Redisson.create(config);
		} else {
			config = Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream());
		}
		return Redisson.create(config);
	}
}
