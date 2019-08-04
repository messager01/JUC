package com.spx.������;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ��дһ��������
 *
 */
public class SpinLockDemo {
	
	//  ԭ�������߳�
	AtomicReference<Thread> atomicReference = new AtomicReference<>();
	
	public void myLock() {
		Thread thread = Thread.currentThread();   //  ��õ��÷������߳�
		System.out.println(Thread.currentThread().getName()+"   "+"myLock");
		while(!atomicReference.compareAndSet(null, thread)) {
			//System.out.println("hold on please!!!");
		}
	}

	public void myUnLock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName()+"   "+"myUnLock");
		
	}
	public static void main(String[] args) throws InterruptedException {
		
	SpinLockDemo demo = new SpinLockDemo();
		
		new Thread(()->{
			demo.myLock();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			demo.myUnLock();
		},"AAA").start(); 
	
		
		Thread.sleep(1000);   //  ��֤A�߳��Ѿ�����
		
		
		
		new Thread(()->{
			demo.myLock();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			demo.myUnLock();
		},"BBB").start(); 
	}

}
