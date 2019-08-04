package com.spx.��д��;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
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
class MyCache1{
	private volatile Map<String,Object> map = new HashMap<>();
	//private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	ReentrantLock myLock = new ReentrantLock();
	
	public void put(String key, Object value) throws InterruptedException {
		
		myLock.lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " ����д��"+key);
			Thread.sleep(300);   // ģ������ӵ��
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+ " "+  "д�����");
		} finally {
			myLock.unlock();
		}
		
	} 
	
	
	public void get(String key) {
		myLock.lock();
		try {
			System.out.println(Thread.currentThread().getName()+ " "+  " ���ڶ�ȡ");
			Object result = map.get(key);
			System.out.println(Thread.currentThread().getName()+ " "+  "��ȡ���"+result);
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

