package com.zjmzxfzhl.common.core.redis.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求防重发注解，限定只用于controller防重发，配合 RepeatRequestAspect 实现一些特殊防重发交易场景
 * <p>
 * 例如有某个APP的请求都是使用同一个controller来处理，并且用一个参数transId来区分不同交易，但只需要防止同一个用户发送相同的transId，就可以加上该注解
 * <p>
 * 也可以用在那些不需要登录就可以访问的交易中，通过锁设备序列号、锁设备IMEI号或者锁设备序列号+设备IMEI号组合等
 * <p>
 * 使用例子：
 *
 * @author 庄金明
 * @RepeatRequest 锁整个方法
 * @RepeatRequest(lockIndexs = 0) 锁第一个参数
 * @RepeatRequest(lockIndexs = {0,1}) 锁第一个参数和第二个参数组合
 * @RepeatRequest(lockIndexs = {1,2}) 锁第二个参数和第三个参数组合
 * @RepeatRequest(lockIndexs = 0,fieldNames = "transId") 锁第一个参数属性等于transId
 * @RepeatRequest(lockIndexs = {0,1},fieldNames = {"transId","id"}) 锁第一个参数属性等于transId和第二个参数属性等于id的组合
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatRequest {
    /**
     * true时，若用户未登陆，则只使用 lockIndexs 组拼key，若用户已登录，只使用 userId 组拼 key 忽略 lockIndexs
     * <p>
     * false时，若用户未登陆，则只使用 lockIndexs 组拼key，若用户已登录，即使用 userId 组拼 key 也使用 lockIndexs 组拼key
     */
    boolean isLoginThenOnlyUserId() default true;

    /**
     * 要锁的参数角标
     */
    int[] lockIndexs() default {};

    /**
     * 要锁的参数的属性名
     */
    String[] fieldNames() default {};

    /**
     * 等待多久（单位：秒）,默认不等待，若前一个请求未执行完毕，直接返回错误
     */
    int waitTime() default 0;

    /**
     * 锁多久后自动释放（单位：秒）
     */
    int leaseTime() default 30;

    /**
     * 未取到锁时提示信息
     *
     * @return
     */
    String msg() default "交易未执行完毕，请勿重复提交";
}