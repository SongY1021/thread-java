package cn.nanysj.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/15 17:31
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class CDLTest {
	private static CountDownLatch cdl = new CountDownLatch(5);

	private static class InitThread extends Thread{
		@Override
		public void run() {
			super.run();
			System.out.println("InitThread [" + Thread.currentThread().getName() + "] 开始初始化....");
			cdl.countDown();
			System.out.println("InitThread [" + Thread.currentThread().getName() + "] 进行工作...");
		}
	}

	public static class WorkThread extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("WorkThread [" + Thread.currentThread().getName() + "] 开始进行工作....");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("InitThread-Runable [" + Thread.currentThread().getName() + "] 第一步初始化开始....");
				cdl.countDown();
				System.out.println("InitThread-Runable [" + Thread.currentThread().getName() + "] 第二步初始化开始....");
				cdl.countDown();
				System.out.println("InitThread-Runable [" + Thread.currentThread().getName() + "] 进入工作....");
			}
		}).start();
		new WorkThread().start();
		for(int i=0; i<3; i++){
			new InitThread().start();
		}
		cdl.await();
		System.out.println("MainThread 开始工作....");
	}
}
