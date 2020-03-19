package cn.nanysj.thread.pc;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 19:40
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class PcTest {
	private static class ThreadP extends Thread {
		private P p;

		public ThreadP(P p) {
			this.p = p;
		}

		public void run(){
			while (true){
				p.setValue();
			}
		}
	}

	private static class ThreadC extends Thread {
		private C c;

		public ThreadC(C c) {
			this.c = c;
		}

		public void run(){
			while (true){
				c.getValue();
			}
		}
	}

	public static void main(String[] args) {
		String lock = "";
		new ThreadP(new P(lock)).start();
		new ThreadC(new C(lock)).start();
	}
}
