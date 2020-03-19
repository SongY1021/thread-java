package cn.nanysj.thread.sync;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/12 20:26
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class SyncClzAndInst {

	/**
	 * 继承Thread类
	 * 使用类锁
	 */
	private static class UseThreadAndSyncClz extends Thread {

		public void run(){
			try {
				System.out.println("UseThreadAndSyncClz Run "+ this);
				SyncClzAndInst.syncClass();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 继承Thread类
	 * 使用对象锁锁
	 */
	private static class UseThreadAndSyncObj extends Thread {
		private SyncClzAndInst syncClzAndInst;

		public UseThreadAndSyncObj(SyncClzAndInst syncClzAndInst){
			this.syncClzAndInst = syncClzAndInst;
		}

		public void run(){
			try {
				System.out.println("UseThreadAndSyncObj Run "+ syncClzAndInst);
				this.syncClzAndInst.syncObj();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 继承Thread类
	 * 使用对象锁锁
	 */
	private static class UseThreadAndSyncObj2 extends Thread {
		private SyncClzAndInst syncClzAndInst;

		public UseThreadAndSyncObj2(SyncClzAndInst syncClzAndInst){
			this.syncClzAndInst = syncClzAndInst;
		}

		public void run(){
			try {
				System.out.println("UseThreadAndSyncObj Run "+ syncClzAndInst);
				this.syncClzAndInst.syncObj2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 实现Runable接口
	 * 使用类锁
	 */
	private static class UseRunableAndSyncClz implements Runnable{

		public void run(){
			try {
				System.out.println("UseRunableAndSyncClz Run");
				SyncClzAndInst.syncClass();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 实现Runable接口
	 * 使用对象锁
	 */
	private static class UseRunableAndSyncObj implements Runnable{

		private SyncClzAndInst syncClzAndInst;

		public UseRunableAndSyncObj(SyncClzAndInst syncClzAndInst){
			this.syncClzAndInst = syncClzAndInst;
		}

		public void run(){
			try {
				System.out.println("UseRunableAndSyncObj Run");
				this.syncClzAndInst.syncObj2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//锁类
	private static synchronized void syncClass() throws InterruptedException {
		Thread.sleep(1L);
		System.out.println("syncClass start....");
		Thread.sleep(10L);
		System.out.println("syncClass end....");
	}

	//对象锁
	private synchronized void syncObj() throws InterruptedException {
		Thread.sleep(3L);
		System.out.println("syncObj start...."+this);
		Thread.sleep(2000L);
		System.out.println("syncObj end...."+this);
	}

	//对象锁
	private synchronized void syncObj2() throws InterruptedException {
		Thread.sleep(2L);
		System.out.println("syncObj2 start...."+this);
		Thread.sleep(2000L);
		System.out.println("syncObj2 end...."+this);
	}

	public static void main(String[] args) {
//		SyncClzAndInst inst1 = new SyncClzAndInst();
//		UseThreadAndSyncObj useThreadAndSyncObj1 = new UseThreadAndSyncObj(inst1);
//		useThreadAndSyncObj1.start();
//
		SyncClzAndInst inst2 = new SyncClzAndInst();
		UseThreadAndSyncObj useThreadAndSyncObj = new UseThreadAndSyncObj(inst2);
		useThreadAndSyncObj.start();

		UseThreadAndSyncObj2 useThreadAndSyncObj2 = new UseThreadAndSyncObj2(inst2);
		useThreadAndSyncObj2.start();

//		UseThreadAndSyncClz useThreadAndSyncClz = new UseThreadAndSyncClz();
//		useThreadAndSyncClz.start();
//
//		UseThreadAndSyncClz useThreadAndSyncClz1 = new UseThreadAndSyncClz();
//		useThreadAndSyncClz1.start();



	}
}
