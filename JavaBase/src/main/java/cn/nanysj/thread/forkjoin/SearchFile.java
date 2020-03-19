package cn.nanysj.thread.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/15 15:25
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class SearchFile extends RecursiveAction {

	private File path;

	public SearchFile(File path) {
		this.path = path;
	}

	@Override
	protected void compute() {
		//如果路径存在
		if(path.exists()){
			List<SearchFile> subTasks = new ArrayList<>();
			//获取路径下的所有文件和文件夹
			File[] files = path.listFiles();
			if(files != null){
				for (File file : files) {
					if(file.isDirectory()){
						subTasks.add(new SearchFile(file));
					}else {
						if(file.getAbsolutePath().endsWith("txt")){
							System.out.println("文件："+file.getAbsolutePath());
						}
					}
				}
				if(!subTasks.isEmpty()){
					for (SearchFile searchFile : invokeAll(subTasks)) {
						searchFile.join();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		SearchFile task = new SearchFile(new File("F:\\"));
		pool.execute(task);

		do {
			System.out.println("并行线程数："+pool.getParallelism());
			System.out.println("任务池数量："+pool.getPoolSize());
			System.out.println("任务总数："+pool.getQueuedTaskCount());
			System.out.println("活动线程数："+pool.getActiveThreadCount());
		}while (!task.isDone());
		task.join();
	}
}
