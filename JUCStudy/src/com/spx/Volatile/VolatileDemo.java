package com.spx.Volatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *    ���Կ���    num  ��ʹ����volatile  Ҳ���ܱ�֤ԭ����
 *    
 *    why?   
 *    
 *     ��Ϊ   num++ ������  ԭ�Ӳ���
 *     
 *     ��ν��ԭ���ԣ�
 *     syn��
 *     ʹ��JUC  AutomicIntegerԭ����
 *
 */
class MyData{
	volatile int num;
	
	AtomicInteger atomicInteger = new AtomicInteger();
	public  void addPlusPlus(){
		num++;
	}
	
	public void addAtomic() {
		atomicInteger.getAndIncrement();     //  Atomically increments by one the current value.  
	}
	
}

public class VolatileDemo {

	public static void main(String[] args) {
		MyData myData = new MyData();
		for(int i =1; i <= 20;i++) {
			new Thread(()-> {
				for(int j = 1;j <= 1000;j++) {
					myData.addPlusPlus();
					myData.addAtomic();
				}
			},String.valueOf(i)).start();
		}

		// ��Ҫ�ȴ�����������̶߳�ִ����ϣ���main�в鿴���
		while(Thread.activeCount() > 2) {   //  Ϊʲô����2����ΪĬ�Ϻ�̨�������߳�   һ����main�߳�  һ����GC�߳�
			Thread.yield();
		}
		System.out.println("finally,the result is "+ myData.num);
		System.out.println("finally,the automic result is "+ myData.atomicInteger);
	}

}
