package com.zjmzxfzhl.framework.config;

import java.util.Collections;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.zjmzxfzhl.framework.permission.PermissionParser;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Configuration
@MapperScan("com.**.mapper*")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setSqlParserList(Collections.singletonList(permissionParser()));
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    @Bean
    public PermissionParser permissionParser() {
        return new PermissionParser();
    }
}
