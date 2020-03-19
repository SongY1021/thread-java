package cn.nanysj.thread.waitandnotify;

import java.util.Scanner;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 15:32
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class WaitAndNotifyTest {
	private static Express express = new Express("北京", "黑龙江", 2000);

	//监听位置变化线程
	private static class SiteListener extends Thread {
		public void run(){
			express.siteListener();
		}
	}

	//更新位置变化线程
	private static class ChangeSite extends Thread {
		public void run(){
			boolean flag = true;
			while (flag){
				System.out.println("请输入");
				Scanner scanner = new Scanner(System.in);
				String site = scanner.next();
				int runKm = scanner.nextInt();
				flag = express.changeSite(site, runKm);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		for(int i=0; i<3; i++){
			new SiteListener().start();
		}
		Thread.sleep(1000);
		new ChangeSite().start();
	}
}
