package com.zjmzxfzhl.common.core.redis.aspect;

import com.zjmzxfzhl.common.core.redis.aspect.components.RepeatRequestComponent;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 登录用户请求防重发处理,Order=0优先于RedissonLockAspect
 *
 * @author 庄金明
 * @date 2020年3月24日
 */
@Aspect
/// zjmzxfzhl-common-redis 加了 spring.factories，不需要在加 @Component，否则springboot会优先解析该类，
/// 因为在spring ioc的过程中，优先解析@Component，@Service，@Controller注解的类。其次解析配置类，也就是@Configuration标注的类。最后开始解析配置类中定义的bean。
/// 会导致 ConditionalOnBean(RedissonDistributedLocker.class) 返回false
/// @Component
@ConditionalOnBean(RepeatRequestComponent.class)
@Order(0)
public class RepeatRequestAspect {
    @Autowired
    private RepeatRequestComponent repeatRequestComponent;

    @Pointcut("execution(* com.*..*.controller.*.*(..))")
    private void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object controllerAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return repeatRequestComponent.exec(joinPoint, SecurityUtils.getUserId());
    }
}
