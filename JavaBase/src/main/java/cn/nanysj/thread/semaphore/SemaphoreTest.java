package cn.nanysj.thread.semaphore;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.sql.Connection;
import java.util.Random;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/16 23:06
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class SemaphoreTest {
	private static DBPool pool = new DBPool(10);

	private static class SemaphoreThread extends Thread{
		@Override
		public void run() {
			Random r = new Random();
			long startTime = System.currentTimeMillis();
			try {
				Connection conn = pool.fatchConn();
				System.out.println("SemaphorThread-[" + Thread.currentThread().getId() + "] 获取连接耗时：" + (System.currentTimeMillis() - startTime) + "ms...");
				Thread.sleep(100+r.nextInt(100));
				System.out.println("SemaphorThread-[" + Thread.currentThread().getId() + "] 操作完成，归还线程");
				pool.releaseConn(conn);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public static void main(String[] args) {
			for (int i=0; i<50; i++){
				new SemaphoreThread().start();
			}
		}
	}
}
