package cn.nanysj.thread.aqs;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/23 22:38
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class AQSTest {
	private static LinkedList<String> printQueuen = new LinkedList<>();
	volatile private static boolean infoType = true;
	private static CustomLock lock = new CustomLock();
	private static Condition condProducter = lock.newCondition();
	private static Condition condConsumer = lock.newCondition();
	private static class ThreadProducter extends Thread{
		@Override
		public void run() {
			while (true){
				try{
					lock.lock();
					if(printQueuen.size() == 0){
						if(infoType){
							printQueuen.addLast("★");
						}else{
							printQueuen.addLast("☆");
						}
						infoType = !infoType;
						Thread.sleep(50);
						condConsumer.signalAll();
					}else{
						condProducter.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	private static class ThreadConsumer extends Thread {
		@Override
		public void run() {
			while (true){
				try{
					lock.lock();
					if(printQueuen.size()>0){
						System.out.println(printQueuen.removeFirst());
						Thread.sleep(30);
						condProducter.signalAll();
					}else{
						condConsumer.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) {
		new ThreadProducter().start();
		new ThreadConsumer().start();
	}
}
