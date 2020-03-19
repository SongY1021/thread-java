package cn.nanysj;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 易识界
 * @version V1.0
 * @date 2020/3/8 16:23
 * @email nanysj_010@163.com
 * @Copyright © cn.nanysj
 */
public class ConllectionTest {

	public static void ArrayListTest(){
		ArrayList<String> arrayList = new ArrayList<String>();
//		arrayList.add("");
//
//
//		LinkedList<String> linkedList = new LinkedList<String>();
		List<String> strings = Collections.synchronizedList(arrayList);
//		CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(arrayList);
//
//		HashSet<String> hashSet = new HashSet<String>();
//
		LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
//
//		linkedHashSet.add("aa");

		HashMap<String, String> hashMap = new HashMap<String, String>();
//		hashMap.put("重地", "aa");
		hashMap.put("通话", "aa");

		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
//
//		linkedHashMap.put("aa", "aa");
		linkedHashMap.put("重地", "aa");
		linkedHashMap.put("通话", "aa");
		linkedHashMap.put("ss", "aa");

		for (Map.Entry<String, String> stringStringEntry : linkedHashMap.entrySet()) {
			System.out.println(stringStringEntry.getKey() + "=" + stringStringEntry.getValue());
		}
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("aa", "aa");

		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
		concurrentHashMap.put("aa", "aa");

	}

	public static void main(String[] args) {
		ConllectionTest.ArrayListTest();
	}
}
