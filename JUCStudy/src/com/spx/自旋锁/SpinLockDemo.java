package com.spx.自旋锁;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁
 *
 */
public class SpinLockDemo {
	
	//  原子引用线程
	AtomicReference<Thread> atomicReference = new AtomicReference<>();
	
	public void myLock() {
		Thread thread = Thread.currentThread();   //  获得调用方法的线程
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
	
		
		Thread.sleep(1000);   //  保证A线程已经进入
		
		
		
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
