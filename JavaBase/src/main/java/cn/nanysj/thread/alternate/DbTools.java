package cn.nanysj.thread.alternate;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 20:38
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class DbTools {
	volatile private boolean prevIsA = false;

	synchronized public void backupA(){
		while (prevIsA){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i=0; i<3; i++){
			System.out.println("●●●");
		}
		prevIsA = true;
		notifyAll();
	}

	synchronized public void backupB(){
		while (!prevIsA){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i=0; i<3; i++){
			System.out.println("○○○");
		}
		prevIsA = false;
		notifyAll();
	}
}
