package com.spx.读写锁;

import java.util.HashMap;
import java.util.Map;
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
class MyCache{
	private volatile Map<String,Object> map = new HashMap<>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void put(String key, Object value) throws InterruptedException {
		
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " 正在写入"+key);
			Thread.sleep(1000);   // 模拟网络拥堵
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+ " "+  "写入完成");
		} finally {
			lock.writeLock().unlock();
		}
		
	} 
	
	
	public void get(String key) {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " 正在读取");
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName()+ " "+  "读取完成"+result);
		} finally {
			lock.readLock().unlock();
		}
	}
	
}
public class ReadWriteLockDemo {
/**
 *    加读写锁之前   
 *   	2  正在写入2
		3  正在写入3
		5  正在写入5
		4  正在写入4
		1  正在写入1
		1  正在读取
		2  正在读取
		1 读取完成null
		4  正在读取
		3  正在读取
		3 读取完成null
		2 读取完成null
		5  正在读取
		4 读取完成null
		5 读取完成null
		5 写入完成
		3 写入完成
		4 写入完成
		2 写入完成
		1 写入完成
 * 
 */
	
	/**
	 *    添加了读写锁之后
	 *      2  正在写入2
			2 写入完成
			5  正在写入5
			5 写入完成
			3  正在写入3
			3 写入完成
			1  正在写入1
			1 写入完成
			4  正在写入4
			4 写入完成
			1  正在读取
			1 读取完成1
			2  正在读取
			3  正在读取
			4  正在读取
			4 读取完成4
			2 读取完成2
			5  正在读取
			5 读取完成5
			3 读取完成3

	 *  
	 */
	
	public static void main(String[] args) {
		MyCache myCache = new MyCache();
		
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

