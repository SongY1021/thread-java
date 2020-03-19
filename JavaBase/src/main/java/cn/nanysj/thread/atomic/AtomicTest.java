package cn.nanysj.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/19 22:41
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class AtomicTest {
	private static AtomicInteger atomicInteger = new AtomicInteger(1);

	public static void main(String[] args) {
		//获取值然后+1
		System.out.println(atomicInteger.getAndIncrement());
		//+1然后获取值
		System.out.println(atomicInteger.incrementAndGet());
		//获取值
		System.out.println(atomicInteger.get());
		//比较后更新
		System.out.println(atomicInteger.compareAndSet(1, 2));
	}
}
