package com.spx.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 *         可重入锁，也叫递归锁
 *     指的是同一线程在获得锁后，内层递归函数仍然能获取该锁的代码
 *     就是说，同一个线程，在外层方法获得锁后，在进入内层方法会自动获得锁
 */
class Phone implements Runnable {

	Lock lock = new ReentrantLock();

	public synchronized void sendSMS() {
		System.out.println(Thread.currentThread().getName() + "发送短信");
		sendEmail();
	}

	public synchronized void sendEmail() {
		System.out.println(Thread.currentThread().getName() + "发送邮件");
	}

	@Override
	public void run() {
		get();
	}

	public void get() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "   " + "invoke" + "get");
			set();
		} finally {
			lock.unlock();
		}
	}

	public void set() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "   " + "invoke" + "set");
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}

	}
}

public class ReentrantLockDemo {

	public static void main(String[] args) throws Exception {
		Phone phone = new Phone();
		new Thread(() -> {
			phone.sendSMS();
		}, "Thread T1").start();

		new Thread(() -> {
			phone.sendSMS();
		}, "Thread T2").start();

		Thread.sleep(1000);
		System.out.println("==================================================");
		Thread t3 = new Thread(phone, "Thread T3");
		Thread t4 = new Thread(phone, "Thread T4");
		t3.start();
		t4.start();
	}

}
