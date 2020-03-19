package cn.nanysj.thread.pc;


/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 19:26
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class P {
	private String lock;

	public P(String lock) {
		this.lock = lock;
	}

	public void setValue(){
		synchronized (lock){
			try {
				if(!ValueObject.value.equals("")) {
					lock.wait();
				}
				String value = System.currentTimeMillis()+"_"+System.nanoTime();
				ValueObject.value = value;
				System.out.println("生产者：set【" + value + "】");
				lock.notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
