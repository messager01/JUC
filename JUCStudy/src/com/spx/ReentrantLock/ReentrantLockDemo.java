package com.spx.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 *         ����������Ҳ�еݹ���
 *     ָ����ͬһ�߳��ڻ�������ڲ�ݹ麯����Ȼ�ܻ�ȡ�����Ĵ���
 *     ����˵��ͬһ���̣߳�����㷽����������ڽ����ڲ㷽�����Զ������
 */
class Phone implements Runnable {

	Lock lock = new ReentrantLock();

	public synchronized void sendSMS() {
		System.out.println(Thread.currentThread().getName() + "���Ͷ���");
		sendEmail();
	}

	public synchronized void sendEmail() {
		System.out.println(Thread.currentThread().getName() + "�����ʼ�");
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
