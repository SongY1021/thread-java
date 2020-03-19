package cn.nanysj.thread.callable;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/19 14:12
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class CallableTest {
	private static class CallableThread implements Callable<String>{
		@Override
		public String call() throws Exception {
			System.out.println("CallableThread is startup....");
			if(!Thread.currentThread().isInterrupted()){
				return "CallableThread is run....";
			}
			return "null";
		}
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CallableThread callableThread = new CallableThread();
		FutureTask<String> futureTask = new FutureTask<String>(callableThread);
		new Thread(futureTask).start();

		Random r = new Random();

		if(r.nextBoolean()){
			System.out.println(futureTask.get());
		}else{
			futureTask.cancel(true);
			if(futureTask.isCancelled()){
				System.out.println("中断...");
			}
		}
	}
}
