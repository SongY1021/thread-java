package cn.nanysj.thread;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/12 18:38
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class TestInterrupt {

	//继承Thread类
	private static class ThreadInterrupt extends Thread {

		public void run(){
			while (!isInterrupted()){
				System.out.println("Thread Interrupt Run");
			}
		}
	}

	//实现Runable接口
	private static class RunableInterrupt implements Runnable{

		public void run() {
			//获取当前线程的isInterrupted方法
			while (!Thread.currentThread().isInterrupted()){
				System.out.println("Runable Interrupt Run");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadInterrupt threadInterrupt = new ThreadInterrupt();
		threadInterrupt.start();
		Thread.sleep(5);
		threadInterrupt.interrupt();

		RunableInterrupt runableInterrupt = new RunableInterrupt();
		Thread runableThread = new Thread(runableInterrupt);
		runableThread.start();
		Thread.sleep(5);
		runableThread.interrupt();
	}
}
