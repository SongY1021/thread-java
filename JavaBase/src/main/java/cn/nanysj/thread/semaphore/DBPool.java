package cn.nanysj.thread.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/16 23:08
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class DBPool {
	private final Semaphore useful,useless;//uesful:可使用连接数，useless：以使用连接数
	private static LinkedList<SqlConnection> pool = new LinkedList<SqlConnection>();

	public DBPool(int poolSize) {
		this.useful = new Semaphore(poolSize);
		this.useless = new Semaphore(0);
		for (int i = 0; i < poolSize; i++) {
			pool.add(SqlConnection.fetchConnection());
		}
	}

	//获取连接
	public Connection fatchConn() throws InterruptedException {
		useful.acquire();//可使用连接-1
		Connection conn;
		synchronized (pool){
			conn = pool.removeFirst();//从池队列获取连接
		}
		useless.release();//以使用连接+1
		return conn;
	}

	//归还连接
	public void releaseConn(Connection conn) throws InterruptedException {
		if(conn != null && conn instanceof SqlConnection){
			System.out.println("当前有" + useful.getQueueLength() + "个线程等待数据库连接....");
			System.out.println("可用连接数: " + useful.availablePermits());
			useless.acquire();//已使用连接-1
			synchronized (pool){
				pool.addLast((SqlConnection) conn);//添加连接至池队列尾
			}
			useful.release();//可使用连接+1
		}
	}
}
