package com.zjmzxfzhl.modules.demo.service;

import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public interface DemoRedlockService {

    /**
     * 通过注解@RedissonLock锁整个方法
     *
     * @throws Exception
     */
    void redlock2() throws Exception;

    /**
     * 通过RedissonLock注解锁第一个参数
     *
     * @param someId
     * @throws Exception
     */
    void redlock3(String someId) throws Exception;

    /**
     * 通过RedissonLock注解锁第一个参数、第二个参数组合
     *
     * @param someId
     * @param someInt
     * @throws Exception
     */
    void redlock4(String someId, int someInt) throws Exception;

    /**
     * 通过RedissonLock注解锁第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第四个参数的zjmzxfzhlId属性组合
     *
     * @param someId
     * @param someInt
     * @param demoZjmzxfzhl1
     * @param demoZjmzxfzhl2
     * @throws Exception
     */
    void redlock5(String someId, int someInt, DemoZjmzxfzhl demoZjmzxfzhl1, DemoZjmzxfzhl demoZjmzxfzhl2) throws Exception;

    /**
     * 通过RedissonLock注解锁第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第三个参数的zjmzxfzhlName属性组合
     *
     * @param someId
     * @param someInt
     * @param demoZjmzxfzhl1
     * @throws Exception
     */
    void redlock6(String someId, int someInt, DemoZjmzxfzhl demoZjmzxfzhl1) throws Exception;
}
