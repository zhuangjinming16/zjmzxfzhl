package com.zjmzxfzhl.modules.demo.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.exception.BaseException;
import com.zjmzxfzhl.common.redlock.RedissonDistributedLocker;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.demo.service.DemoRedlockService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
@RequestMapping("/demo/redlock")
@Slf4j
public class DemoRedlockController {
	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;

	/**
	 * 不使用注解，直接使用 RedissonDistributedLocker.tryLock 获得锁
	 * 
	 * @return
	 */
	@GetMapping(value = "/redlock1")
	@ResponseBody
	public Result redlock1() {
		log.info("请求接入redlock1");
		// 方法内部写死锁要锁的key，也可以根据一定规则产生要锁的key
		String redlockKey = "redlockKey";
		boolean isSuccess = redissonDistributedLocker.tryLock(redlockKey, 10, 10, TimeUnit.SECONDS);
		if (isSuccess) {
			try {
				log.info("成功获得锁");
				Thread.sleep(2000);
				log.info("do samething");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				redissonDistributedLocker.unlock(redlockKey);
				log.info("成功释放锁");
			}
		} else {
			throw new BaseException("交易超时，请稍后重试");
		}
		return Result.ok();
	}

	@Autowired
	private DemoRedlockService demoRedlockService;

	/**
	 * 通过注解@RedissonLock锁 整个方法
	 */
	@GetMapping(value = "/redlock2")
	@ResponseBody
	public Result redlock2() throws Exception {
		log.info("请求接入redlock2");
		demoRedlockService.redlock2();
		return Result.ok();
	}

	/**
	 * 通过RedissonLock注解锁 第一个参数
	 * 
	 * @param someId
	 * @return
	 */
	@GetMapping(value = "/redlock3")
	@ResponseBody
	public Result redlock3(String someId) throws Exception {
		log.info("请求接入redlock3");
		demoRedlockService.redlock3(someId);
		return Result.ok();
	}

	/**
	 * 通过RedissonLock注解锁 第一个参数、第二个参数组合
	 * 
	 * @param someId
	 * @param someInt
	 * @return
	 */
	@GetMapping(value = "/redlock4")
	@ResponseBody
	public Result redlock4(String someId, int someInt) throws Exception {
		log.info("请求接入redlock4");
		demoRedlockService.redlock4(someId, someInt);
		return Result.ok();
	}

	/**
	 * 通过RedissonLock注解锁 第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第四个参数的zjmzxfzhlId属性组合
	 * 
	 * @return
	 */
	@GetMapping(value = "/redlock5")
	@ResponseBody
	public Result redlock5() throws Exception {
		log.info("请求接入redlock5");
		DemoZjmzxfzhl demoZjmzxfzhl1 = new DemoZjmzxfzhl();
		demoZjmzxfzhl1.setZjmzxfzhlId("id1");

		DemoZjmzxfzhl demoZjmzxfzhl2 = new DemoZjmzxfzhl();
		demoZjmzxfzhl2.setZjmzxfzhlId("id2");
		demoRedlockService.redlock5("2", 3, demoZjmzxfzhl1, demoZjmzxfzhl2);
		return Result.ok();
	}

	/**
	 * 通过RedissonLock注解锁 第一个参数、第二个参数、第三个参数的zjmzxfzhlId属性、第三个参数的zjmzxfzhlName属性组合
	 * 
	 * @return
	 */
	@GetMapping(value = "/redlock6")
	@ResponseBody
	public Result redlock6() throws Exception {
		log.info("请求接入redlock6");
		DemoZjmzxfzhl demoZjmzxfzhl = new DemoZjmzxfzhl();
		demoZjmzxfzhl.setZjmzxfzhlId("id");
		demoZjmzxfzhl.setZjmzxfzhlName("name");
		demoRedlockService.redlock6("2", 3, demoZjmzxfzhl);
		return Result.ok();
	}
}
