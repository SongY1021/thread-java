package cn.nanysj.thread.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/19 23:40
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class UseAtomicReference {
	private static AtomicReference<String> atomicReference = new AtomicReference<>("Test AtomicReference");

	public static void main(String[] args) {
		//获取值
		System.out.println(atomicReference.get());
		//比较然后设置值
		System.out.println(atomicReference.compareAndSet("Test AtomicReference",
				"Test AtomicReference A"));
	}
}
