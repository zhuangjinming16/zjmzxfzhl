package com.zjmzxfzhl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@SpringBootApplication
public class ZjmzxfzhlApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ZjmzxfzhlApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ZjmzxfzhlApplication.class);
	}

}
