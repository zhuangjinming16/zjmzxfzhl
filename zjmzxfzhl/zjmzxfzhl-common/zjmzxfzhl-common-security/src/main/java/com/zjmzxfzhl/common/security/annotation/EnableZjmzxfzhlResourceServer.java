package com.zjmzxfzhl.common.security.annotation;

import com.zjmzxfzhl.common.security.component.resource.ZjmzxfzhlResourceServerAutoConfiguration;
import com.zjmzxfzhl.common.security.component.resource.ZjmzxfzhlResourceServerBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author 庄金明
 * @date 2020年7月18日
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ZjmzxfzhlResourceServerAutoConfiguration.class, ZjmzxfzhlResourceServerBeanDefinitionRegistrar.class})
public @interface EnableZjmzxfzhlResourceServer {
}
