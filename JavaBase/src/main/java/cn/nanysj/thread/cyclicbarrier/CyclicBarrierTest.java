package cn.nanysj.thread.cyclicbarrier;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/15 17:47
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class CyclicBarrierTest {
	private static CyclicBarrier barrier = new CyclicBarrier(5, new ThirdThread());
	private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

	private static class SubThread extends Thread{
		@Override
		public void run() {
			try{
				long threadId = Thread.currentThread().getId();
				resultMap.put(Thread.currentThread().getName(), threadId);
				Random r = new Random();
				if(r.nextBoolean()){
					Thread.sleep(2000+threadId);
					System.out.println("Thread-[" + threadId + "] working....");
				}
				System.out.println("Thread-[" + threadId + "] waiting....");
				barrier.await();
				System.out.println("Thread-[" + threadId + "] finish....");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private static class ThirdThread implements Runnable{

		@Override
		public void run() {
			System.out.println("ThirdThread-[" + Thread.currentThread().getId() + "] workiing....");
			StringBuilder builder = new StringBuilder("[");
			for (Map.Entry<String, Long> entry : resultMap.entrySet()) {
				builder.append("{"+entry.getKey()+"="+entry.getValue()+"},");
			}
			builder.append("}");
			System.out.println("The ResultMap is"+builder.toString());
		}
	}

	public static void main(String[] args) {
		for (int i=0; i<5;i++){
			new SubThread().start();
		}
	}
}
