package cn.nanysj.thread.waitandnotify;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 15:02
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class Express {
	private String startSite;//起始位置
	private String endSite;//终点位置
	private String site;//到达位置
	private int totalKm;//总的公里数
	private int remainKm;

	//构造函数
	public Express(String startSite, String endSite, int totalKm) {
		this.startSite = startSite;
		this.endSite = endSite;
		this.totalKm = totalKm;
		this.site = startSite;
		this.remainKm = totalKm;
	}

	/**
	 * 监听位置变化，直到到达终点。
	 */
	public synchronized void siteListener(){
		while(!this.endSite.equals(this.site)){
			try {
				wait();//挂起线程
				//notify唤醒线程后执行
				if (!this.endSite.equals(this.site)) {
					System.out.println("[" + Thread.currentThread().getName() + "] --> 当前位置：" + this.site + ", 距离您还有：" + this.remainKm + "公里");
				}else{
					System.out.println("[" + Thread.currentThread().getName() + "] --> 已到达：" + this.endSite);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新位置信息
	 * @param site 地点
	 * @param runKm 行驶公里数
	 * @return
	 */
	public synchronized boolean changeSite(String site, int runKm){
		this.site = site;//更新位置
		this.remainKm -= runKm;//更新剩余公里数
		notifyAll();//通知所有等待线程位置更新
		if(this.remainKm >0){
			return true;
		}
		return false;
	}
}
