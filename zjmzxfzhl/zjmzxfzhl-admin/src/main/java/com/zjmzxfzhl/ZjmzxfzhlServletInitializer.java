package com.zjmzxfzhl;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public class ZjmzxfzhlServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ZjmzxfzhlApplication.class);
    }
}
