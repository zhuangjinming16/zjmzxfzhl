package com.zjmzxfzhl.common.core.redis.redlock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public interface DistributedLocker {

    /**
     * 获得锁
     *
     * @param lockKey
     * @return
     */
    RLock getLock(String lockKey);

    /**
     * 锁lockKey
     *
     * @param lockKey
     * @return
     */
    RLock lock(String lockKey);

    /**
     * 锁lockKey timeout
     *
     * @param lockKey
     * @param timeout
     * @return
     */
    RLock lock(String lockKey, long timeout);

    /**
     * 锁lockKey
     *
     * @param lockKey
     * @param timeout
     * @param unit
     * @return
     */
    RLock lock(String lockKey, long timeout, TimeUnit unit);

    /**
     * 尝试锁
     *
     * @param lockKey
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return 是否成功
     */
    boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit);

    /**
     * 尝试锁
     *
     * @param lock
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return 是否成功
     */
    boolean tryLock(RLock lock, long waitTime, long leaseTime, TimeUnit unit);

    /**
     * 解锁
     *
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 解锁
     *
     * @param lock
     */
    void unlock(RLock lock);
}
