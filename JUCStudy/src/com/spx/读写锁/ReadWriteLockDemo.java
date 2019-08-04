package com.spx.��д��;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 *    ����߳�ͬʱ��һ����Դ��û���κ����⣬����Ϊ�����㲢�����󣬶�ȡ������ԴӦ�ÿ���ͬʱ����
 *    ����
 *    �����һ���߳���ȥд������Դ���Ͳ�Ӧ���������̶߳Ը���Դ���ж�����д
 *    
 *    �ܽ᣺   ��������   
 *          ��д������
 *          дд������
 */

//  ��Դ��    ģ��дһ������  ��Redis
class MyCache{
	private volatile Map<String,Object> map = new HashMap<>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void put(String key, Object value) throws InterruptedException {
		
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " ����д��"+key);
			Thread.sleep(1000);   // ģ������ӵ��
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+ " "+  "д�����");
		} finally {
			lock.writeLock().unlock();
		}
		
	} 
	
	
	public void get(String key) {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " ���ڶ�ȡ");
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName()+ " "+  "��ȡ���"+result);
		} finally {
			lock.readLock().unlock();
		}
	}
	
}
public class ReadWriteLockDemo {
/**
 *    �Ӷ�д��֮ǰ   
 *   	2  ����д��2
		3  ����д��3
		5  ����д��5
		4  ����д��4
		1  ����д��1
		1  ���ڶ�ȡ
		2  ���ڶ�ȡ
		1 ��ȡ���null
		4  ���ڶ�ȡ
		3  ���ڶ�ȡ
		3 ��ȡ���null
		2 ��ȡ���null
		5  ���ڶ�ȡ
		4 ��ȡ���null
		5 ��ȡ���null
		5 д�����
		3 д�����
		4 д�����
		2 д�����
		1 д�����
 * 
 */
	
	/**
	 *    ����˶�д��֮��
	 *      2  ����д��2
			2 д�����
			5  ����д��5
			5 д�����
			3  ����д��3
			3 д�����
			1  ����д��1
			1 д�����
			4  ����д��4
			4 д�����
			1  ���ڶ�ȡ
			1 ��ȡ���1
			2  ���ڶ�ȡ
			3  ���ڶ�ȡ
			4  ���ڶ�ȡ
			4 ��ȡ���4
			2 ��ȡ���2
			5  ���ڶ�ȡ
			5 ��ȡ���5
			3 ��ȡ���3

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

