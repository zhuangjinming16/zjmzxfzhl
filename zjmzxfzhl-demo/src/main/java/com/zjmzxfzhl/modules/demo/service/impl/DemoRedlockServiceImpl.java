package com.zjmzxfzhl.modules.demo.service.impl;

import com.zjmzxfzhl.common.core.redis.aspect.annotation.RedissonLock;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.demo.service.DemoRedlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Service
@Slf4j
public class DemoRedlockServiceImpl implements DemoRedlockService {

    /**
     * 通过注解@RedissonLock锁整个方法
     */
    @Override
    @RedissonLock
    public void redlock2() throws Exception {
        Thread.sleep(2000);
        log.info("锁整个方法");
    }

    /**
     * 通过RedissonLock注解锁第一个参数
     *
     * @param someId
     */
    @Override
    @RedissonLock(lockIndexs = 0)
    public void redlock3(String someId) throws Exception {
        Thread.sleep(2000);
        log.info("锁第一个参数：" + someId);
    }

    /**
     * 通过RedissonLock注解锁第一个参数、第二个参数组合
     *
     * @param someId
     * @param someInt
     */
    @Override
    @RedissonLock(lockIndexs = {0, 1})
    public void redlock4(String someId, int someInt) throws Exception {
        Thread.sleep(2000);
        log.info("锁第一个参数、第二个参数组合：" + someId + "," + someInt);
    }

    /**
     * 通过RedissonLock注解锁第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第四个参数的zjmzxfzhlId属性组合
     *
     * @param someId
     * @param someInt
     * @param demoZjmzxfzhl1
     * @param demoZjmzxfzhl2
     */
    @Override
    @RedissonLock(lockIndexs = {0, 1, 2, 3}, fieldNames = {"", "", "zjmzxfzhlId", "zjmzxfzhlId"})
    public void redlock5(String someId, int someInt, DemoZjmzxfzhl demoZjmzxfzhl1, DemoZjmzxfzhl demoZjmzxfzhl2) throws Exception {
        Thread.sleep(2000);
        log.info("锁第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第四个参数的zjmzxfzhlId属性组合：" + someId + "," + someInt + "," + demoZjmzxfzhl1.getZjmzxfzhlId() + "," + demoZjmzxfzhl2.getZjmzxfzhlId());
    }

    /**
     * 通过RedissonLock注解锁第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第三个参数的zjmzxfzhlName属性组合
     *
     * @param someId
     * @param someInt
     * @param demoZjmzxfzhl1
     */
    @Override
    @RedissonLock(lockIndexs = {0, 1, 2, 2}, fieldNames = {"", "", "zjmzxfzhlId", "zjmzxfzhlName"})
    public void redlock6(String someId, int someInt, DemoZjmzxfzhl demoZjmzxfzhl1) throws Exception {
        Thread.sleep(2000);
        log.info("锁第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第三个参数的zjmzxfzhlName属性组合：" + someId + "," + someInt + "," + demoZjmzxfzhl1.getZjmzxfzhlId() + "," + demoZjmzxfzhl1.getZjmzxfzhlName());
    }
}
