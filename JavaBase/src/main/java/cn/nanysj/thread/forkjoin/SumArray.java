package cn.nanysj.thread.forkjoin;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/14 23:50
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class SumArray {

	//任务类 相当于fork
	private static class SumTask extends RecursiveTask<Integer>{
		//阈值
		private static final int THRESHOLD = ProductionArray.ARRAY_SIZE/10;
		private Integer[] src;//计算的数组
		private int beginIndex;//统计开始位置
		private int endIndex;//统计截止位置

		public SumTask(Integer[] src, int beginIndex, int endIndex) {
			this.src = src;
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
		}

		//计算方法
		protected Integer compute() {
			//如果当前统计范围在阈值之内，则直接计算并返回结果
			if(endIndex-beginIndex <= THRESHOLD){
				Integer count = 0;
				for (int i= beginIndex; i<=endIndex; i++) {
					count += src[i];
				}
				return count;
			}else{
				//当前统计范围大于阈值范围，拆分统计范围
				int mid = (endIndex+beginIndex)>>>1;
				//按照拆分后的范围新建统计任务
				SumTask leftTask = new SumTask(src, beginIndex, mid);
				SumTask rightTask = new SumTask(src, mid+1, endIndex);
				//将任务提交到任务池(ForkJoinPool)
				invokeAll(leftTask, rightTask);
				//拆分后的任务结果合并返回
				return leftTask.join()+rightTask.join();
			}
		}
	}

	public static void main(String[] args) {
		//1. 创建ForkJoin任务池
		ForkJoinPool pool = new ForkJoinPool();
		//2. 创建任务
		SumTask sumTask = new SumTask(ProductionArray.productionArray(), 0, ProductionArray.productionArray().length-1);
		long beginTime = System.currentTimeMillis();
		//3. 将任务提交给任务池
		pool.invoke(sumTask);
		System.out.println("计算任务开始");
		do{
			System.out.printf("池中活动线程数量: %d\n",pool.getActiveThreadCount());
			System.out.printf("偷窃算法数量: %d\n",pool.getStealCount());
			System.out.printf("并行数量: %d\n",pool.getParallelism());
			System.out.println();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while (!sumTask.isDone());
		//4. 关闭任务池
		pool.shutdown();
		System.out.println("计算结果：" + sumTask.join() + ", 耗时：" + (System.currentTimeMillis() - beginTime) + "ms....");
	}

}
