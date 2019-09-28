package com.zjmzxfzhl.common.redlock;

import org.springframework.stereotype.Service;

import com.zjmzxfzhl.common.aspect.annotation.RedissonLock;
import com.zjmzxfzhl.modules.sys.entity.SysUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedlockService {
	@RedissonLock
	public void testLock() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("方法内");
	}

	@RedissonLock(lockIndexs = 0)
	public void testLockOne(String id) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("方法内" + id);
	}

	@RedissonLock(lockIndexs = { 0, 1 })
	public void testLockTwo(String id, int i) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("方法内" + id + "," + i);
	}

	@RedissonLock(lockIndexs = { 0, 1, 2, 2 }, fieldNames = { "", "", "userId", "userName" })
	public void testLockThree(String id, int i, SysUser user) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("方法内" + id + "," + i + "," + user.getUserId() + "," + user.getUserName());
	}
}
