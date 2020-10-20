package com.zjmzxfzhl;

import com.zjmzxfzhl.common.security.annotation.EnableZjmzxfzhlAuthorizationServer;
import com.zjmzxfzhl.common.security.annotation.EnableZjmzxfzhlResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@SpringBootApplication
@EnableZjmzxfzhlAuthorizationServer
@EnableZjmzxfzhlResourceServer
public class ZjmzxfzhlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZjmzxfzhlApplication.class, args);
    }
}
