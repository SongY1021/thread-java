package cn.nanysj.thread.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/23 22:14
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 * 自定义抢占锁
 */
public class CustomLock implements Lock {

	/**
	 * AQS中state表实锁的状态  1：锁被占用，0：锁未被占用
	 */
	protected static class Sync extends AbstractQueuedSynchronizer{
		//尝试获取锁
		protected boolean tryAcquire(int arg) {
			if(compareAndSetState(0, 1)){
				//设置拥有独占访问权的线程为当前线程
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		//释放锁
		protected boolean tryRelease(int arg) {
			if(getState() == 0){
				throw new UnsupportedOperationException();
			}
			setState(0);
			return true;
		}

		//锁是否被占用
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		Condition newCondition(){
			return new ConditionObject();
		}

	}
	//初始化锁模板
	private final Sync sync = new Sync();
	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
