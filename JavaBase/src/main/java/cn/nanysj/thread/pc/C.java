package cn.nanysj.thread.pc;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 19:36
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class C {
	private String lock;

	public C(String lock) {
		this.lock = lock;
	}

	public void getValue(){
		try {
			synchronized (lock) {
				if (ValueObject.value.equals("")) {
					lock.wait();
				}
				System.out.println("消费者：get【" + ValueObject.value + "】");
				ValueObject.value = "";
				lock.notify();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
