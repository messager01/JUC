package com.spx.读写锁;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 *    多个线程同时读一个资源类没有任何问题，所以为了满足并发需求，读取共享资源应该可以同时进行
 *    但是
 *    如果有一个线程想去写共享资源，就不应该让其他线程对该资源进行读或者写
 *    
 *    总结：   读读共享   
 *          读写不共存
 *          写写不共存
 */

//  资源类    模仿写一个缓存  如Redis
class MyCache1{
	private volatile Map<String,Object> map = new HashMap<>();
	//private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	ReentrantLock myLock = new ReentrantLock();
	
	public void put(String key, Object value) throws InterruptedException {
		
		myLock.lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " 正在写入"+key);
			Thread.sleep(300);   // 模拟网络拥堵
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+ " "+  "写入完成");
		} finally {
			myLock.unlock();
		}
		
	} 
	
	
	public void get(String key) {
		myLock.lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " 正在读取");
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName()+ " "+  "读取完成"+result);
		} finally {
			myLock.unlock();
		}
	}
	
}
public class ReadWriteLockDemo2 {

	public static void main(String[] args) {
		MyCache1 myCache = new MyCache1();
		
		for(int i =1; i <=5;i++) {
			
			final int tempInt = i;
			
			new Thread(()->{
				try {
					myCache.put(tempInt+"",tempInt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},String.valueOf(i)).start();
		}
		
		
		
		for(int i =1; i <=5;i++) {
			
			final int tempInt = i;
			
			new Thread(()->{
					myCache.get(tempInt+"");
			},String.valueOf(i)).start();
		}
	}

}

