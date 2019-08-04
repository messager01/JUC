package com.spx.Volatile;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 
 * ABA  ����Ľ����   AtomicStampedReference
 *
 */
public class ABADemo {
	
	static AtomicReference<Integer> aoAtomicReference = new AtomicReference<>(100);
	                                                                   //                ��ʼֵΪ100  �汾����1
	static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100, 1);
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("==============ABA����Ĳ���=====================================");
		
		new Thread(() -> {
			aoAtomicReference.compareAndSet(100, 101);
			aoAtomicReference.compareAndSet(101, 100);
		},"t1").start();
		
		
		new Thread(() -> {
			try{TimeUnit.SECONDS.sleep(1);}catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(aoAtomicReference.compareAndSet(100, 2019)+"   "+aoAtomicReference.get());
			
		},"t2").start();
		
		
		
		Thread.sleep(2000);
		
		System.out.println("=================ABA����Ľ��==========================");
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName()+"��ǰ�汾��"+stamp);
			try{TimeUnit.SECONDS.sleep(1);}catch (Exception e) {
				e.printStackTrace();
			}
			atomicStampedReference.compareAndSet(100, 101,atomicStampedReference.getStamp() , atomicStampedReference.getStamp()+1);
			atomicStampedReference.compareAndSet(101, 100,atomicStampedReference.getStamp() , atomicStampedReference.getStamp()+1);
			System.out.println("��ǰ��ֵΪ��"+atomicStampedReference.getReference()+"��ǰ�汾��Ϊ��"+atomicStampedReference.getStamp());
		},"t3").start();
		
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName()+"�õ��İ汾��"+stamp);
			try{TimeUnit.SECONDS.sleep(4);}catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = atomicStampedReference.compareAndSet(100, 2019,stamp, atomicStampedReference.getStamp()+1);
			System.out.println("��ǰ��ֵΪ��"+atomicStampedReference.getReference()+"�Ƿ��޸ĳɹ�"+result+"��ǰ�汾��Ϊ��"+atomicStampedReference.getStamp());
		},"t4").start();
	}
}
