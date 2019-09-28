package com.zjmzxfzhl.common.redlock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

public interface DistributedLocker {

	RLock getLock(String lockKey);

	RLock lock(String lockKey);

	RLock lock(String lockKey, long timeout);

	RLock lock(String lockKey, long timeout, TimeUnit unit);

	boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit);

	boolean tryLock(RLock lock, long waitTime, long leaseTime, TimeUnit unit);

	void unlock(String lockKey);

	void unlock(RLock lock);
}
