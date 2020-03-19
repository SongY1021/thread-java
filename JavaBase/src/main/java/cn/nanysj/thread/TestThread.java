package cn.nanysj.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/12 15:00
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class TestThread {
	/**
	 * 继承Thread类
	 */
	private static class UseThread extends Thread{

		public void run(){
			System.out.println(Thread.currentThread().getName() + "--> run");
			super.run();
		}
	}


	/**
	 * 实现Runable接口
	 */
	private static class UseRunable implements Runnable{

		public void run() {
			System.out.println(Thread.currentThread().getName() + "--> run");
		}
	}

	/**
	 * 实现 Callable接口，
	 * 与Runable接口不同的是  Callable接口具有返回值
	 */
	private static class UseCallable implements Callable<String>{

		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName() + "--> run");
			return "Implements Callable ";
		}
	}


	public static void main(String[] args) throws ExecutionException, InterruptedException {
		UseThread useThread = new UseThread();
		useThread.start();

		UseRunable useRunable = new UseRunable();
		new Thread(useRunable).start();

		UseCallable useCallable = new UseCallable();
		FutureTask<String> futureTask = new FutureTask<String>(useCallable);
		new Thread(futureTask).start();
		System.out.println(futureTask.get());
	}
}
