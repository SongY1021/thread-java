package cn.nanysj.thread.alternate;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 20:48
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class DbToolsTest {
	private static class BackupA extends Thread{
		private DbTools dbTools;

		public BackupA(DbTools dbTools) {
			this.dbTools = dbTools;
		}

		@Override
		public void run() {
			super.run();
			dbTools.backupA();
		}
	}

	private static class BackupB extends Thread{
		private DbTools dbTools;

		public BackupB(DbTools dbTools) {
			this.dbTools = dbTools;
		}

		@Override
		public void run() {
			super.run();
			dbTools.backupB();
		}
	}

	public static void main(String[] args) {
		DbTools dbTools = new DbTools();

		for (int i=0; i<10; i++){
			new BackupA(dbTools).start();
			new BackupB(dbTools).start();
		}
	}
}
