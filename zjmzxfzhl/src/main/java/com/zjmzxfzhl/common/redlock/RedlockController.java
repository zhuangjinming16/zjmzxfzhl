package com.zjmzxfzhl.common.redlock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.modules.sys.entity.SysUser;

@Controller
public class RedlockController {
	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;

	@RequestMapping(value = "/test/redlock")
	@ResponseBody
	public R testRedlock() throws Exception {
		String lockKey = "test";
		// redissonDistributedLocker.unlock(lockKey);
		for (int i = 0; i < 100; i++) {
			final int x = i;
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					int y = 0;
					while (y < 20) {
						y++;
						boolean isSuccess = redissonDistributedLocker.tryLock(lockKey, 2, 1, TimeUnit.SECONDS);
						if (isSuccess) {
							try {
								System.err.println("==================线程" + x + ",获得所sleep1000");
								Thread.sleep(100);
								System.err.println("==================线程" + x + ",do samething");
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								System.err.println("==================线程" + x + ",unlock");
								System.err.println();
								redissonDistributedLocker.unlock(lockKey);
							}
						}
					}
				}
			});
			t.start();
		}
		return R.ok();
	}

	@Autowired
	private RedlockService redlockService;

	@RequestMapping(value = "/test/redlock2")
	@ResponseBody
	public R testRedlock2(String id) throws Exception {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
		CyclicBarrier cyclicBarrier1 = new CyclicBarrier(100);
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				try {
					cyclicBarrier.await();
					redlockService.testLock();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();
			new Thread(() -> {
				try {
					cyclicBarrier1.await();
					redlockService.testLockOne("1");
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();

			new Thread(() -> {
				try {
					cyclicBarrier1.await();
					redlockService.testLockTwo("2", 3);
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();
		}
		return R.ok();
	}

	@RequestMapping(value = "/test/redlock3")
	@ResponseBody
	public R testRedlock3() throws Exception {
		SysUser user = new SysUser();
		user.setUserId("admin");
		user.setUserName("系统管理员");
		redlockService.testLockThree("2", 3, user);
		return R.ok();
	}
}
