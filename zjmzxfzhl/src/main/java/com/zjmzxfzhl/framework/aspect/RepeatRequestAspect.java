package com.zjmzxfzhl.framework.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.aspect.components.RepeatRequestComponent;
import com.zjmzxfzhl.framework.shiro.util.ShiroUtils;

/**
 * 登录用户请求防重发处理,Order=0优先于RedissonLockAspect
 * 
 * @author 庄金明
 * @date 2020年3月24日
 */
@Aspect
@Component
@Order(0)
public class RepeatRequestAspect {
    @Autowired
    private RepeatRequestComponent repeatRequestComponent;

    // 管理端请求，若有多个modules则使用||运算符增加拦截controller方法，如下
    // @Pointcut("execution(* com.*..*.sys.controller.*.*(..)) || execution(* com.*..*.othermodule.controller.*.*(..))")
    // 或者使用拦截全部controller，然后排除个别非管理端的controller，如下
    // @Pointcut("execution(* com.*..*.controller.*.*(..)) && !execution(* com.*..*.app.controller.*.*(..))")
    /**
     * 后台管理请求切面
     */
    @Pointcut("execution(* com.*..*.controller.*.*(..)) && !execution(* com.*..*.app.controller.*.*(..))")
    private void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object controllerAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String userId = ShiroUtils.getUserId();
        if (userId == null || userId.length() == 0) {
            userId = "";
        }
        return repeatRequestComponent.exec(joinPoint, userId);
    }
}
