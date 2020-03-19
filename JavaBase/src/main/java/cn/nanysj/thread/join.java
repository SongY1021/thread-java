package cn.nanysj.thread;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 22:17
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class join {

	/**
	 * 内部类 线程队列
	 */
	private static class JumpQueue extends Thread{

		//前线程
		private Thread previous;

		//构造方法 传入前线程和当前线程名称
		public JumpQueue(Thread previous, String name) {
			this.previous = previous;
			this.setName(name);
		}

		@Override
		public void run() {
			try {
				System.out.println("Thread [" + Thread.currentThread().getName() + "] 需要在 Thread [" + previous.getName() + "] 执行完成后才能执行...");
				//将前线程插入到当前线程前， 等待前线程执行完成
				previous.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread [" + Thread.currentThread().getName() + "] 执行完成...");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//获取当前线程，作为根线程
		Thread previous = Thread.currentThread();
		for (int i=0; i<10; i++){
			Thread thread = new JumpQueue(previous, String.valueOf(i));
			thread.start();
			previous = thread;
		}
		Thread.sleep(1000);
		System.out.println("Thread [" + Thread.currentThread().getName() + "] 执行完成。");
	}
}
