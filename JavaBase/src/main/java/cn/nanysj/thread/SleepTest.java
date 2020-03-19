package cn.nanysj.thread;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 22:52
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class SleepTest {
	private static String lock = "";

	private static class ThreadA extends Thread{
		@Override
		public void run() {
			super.run();
			try {
				System.out.println("Thread ["+Thread.currentThread().getName() + "] Start....");
				synchronized (lock){
					sleep(1000);
					System.out.println("Thread ["+Thread.currentThread().getName() + "] Get Lock");
				}
				sleep(1000);
				System.out.println("Thread ["+Thread.currentThread().getName() + "] End....");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		for (int i=0; i<5; i++){
			new ThreadA().start();
		}
	}


}
