package cn.nanysj.thread.forkjoin;

import java.util.Random;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/15 0:02
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class ProductionArray {
	public static final int ARRAY_SIZE = 10000000;

	public static Integer[] productionArray(){
		Random random = new Random();
		Integer[] arr = new Integer[ARRAY_SIZE];
		for (int i=0; i<ARRAY_SIZE; i++){
			arr[i] = random.nextInt(ARRAY_SIZE*3);
		}
		return arr;
	}
}
