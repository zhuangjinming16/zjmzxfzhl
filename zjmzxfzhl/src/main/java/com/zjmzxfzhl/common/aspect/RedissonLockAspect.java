package com.zjmzxfzhl.common.aspect;

import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.aspect.annotation.RedissonLock;
import com.zjmzxfzhl.common.exception.BaseException;
import com.zjmzxfzhl.common.redlock.RedissonDistributedLocker;

import lombok.extern.slf4j.Slf4j;

/**
 * 分布式锁处理,一般限定RedissonLock注解只用于service，请注意若用于controller会与交易防重发一起触发
 * 
 * @author 庄金明
 *
 */
@Aspect
@Component
@Order(1) // order设置的小一点，若注解用于service类，让该切面优先于Transactional注解
@Slf4j
public class RedissonLockAspect {
	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;

	@Around("@annotation(redissonLock)")
	public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) throws Throwable {
		Object obj = null;
		// 方法内的所有参数
		Object[] params = joinPoint.getArgs();

		// 等待多久，默认10秒
		int waitTime = redissonLock.leaseTime();
		// 多久会自动释放，默认10秒
		int leaseTime = redissonLock.leaseTime();

		int[] lockIndexs = redissonLock.lockIndexs();
		String[] fieldNames = redissonLock.fieldNames();
		String lockParams = "";
		// 锁2个及2个以上参数时，fieldNames数量应与lockIndexs一致
		if (lockIndexs.length > 1 && lockIndexs.length != fieldNames.length) {
			throw new BaseException("lockIndexs与fieldNames数量不一致");
		}
		// 数组为空代表锁整个方法
		if (lockIndexs.length > 0) {
			StringBuffer lockParamsBuffer = new StringBuffer();
			for (int i = 0; i < lockIndexs.length; i++) {
				if (fieldNames.length == 0 || fieldNames[i] == null || fieldNames[i].length() == 0) {
					lockParamsBuffer.append("." + params[lockIndexs[i]]);
				} else {
					Object lockParamValue = PropertyUtils.getSimpleProperty(params[lockIndexs[i]], fieldNames[i]);
					lockParamsBuffer.append("." + lockParamValue);
				}
			}
			lockParams = lockParamsBuffer.toString();
		}
		// 取得方法名
		String key = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + lockParams;

		RLock rlock = redissonDistributedLocker.getLock(key);
		boolean isSuccess = redissonDistributedLocker.tryLock(rlock, waitTime, leaseTime, TimeUnit.SECONDS);
		if (isSuccess) {
			log.info("取到锁[" + key + "]");
			try {
				obj = joinPoint.proceed();
			} finally {
				log.info("释放锁[" + key + "]");
				redissonDistributedLocker.unlock(rlock);
			}
		} else {
			throw new BaseException(redissonLock.msg());
		}
		return obj;
	}
}
