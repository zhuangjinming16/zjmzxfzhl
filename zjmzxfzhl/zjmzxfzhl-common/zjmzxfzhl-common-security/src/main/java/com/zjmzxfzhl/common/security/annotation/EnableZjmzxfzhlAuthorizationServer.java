package com.zjmzxfzhl.common.security.annotation;

import com.zjmzxfzhl.common.security.component.authorization.SecurityConfig;
import com.zjmzxfzhl.common.security.component.authorization.ZjmzxfzhlAuthorizationServerAutoConfiguration;
import com.zjmzxfzhl.common.security.component.authorization.ZjmzxfzhlAuthorizationServerBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.lang.annotation.*;

/**
 * @author 庄金明
 * @date 2020年7月18日
 */
@Documented
@Inherited
@EnableAuthorizationServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ZjmzxfzhlAuthorizationServerAutoConfiguration.class,SecurityConfig.class,
        ZjmzxfzhlAuthorizationServerBeanDefinitionRegistrar.class})
public @interface EnableZjmzxfzhlAuthorizationServer {
}
