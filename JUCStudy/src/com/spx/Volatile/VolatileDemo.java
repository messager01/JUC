package com.spx.Volatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *    可以看出    num  即使加上volatile  也不能保证原子性
 *    
 *    why?   
 *    
 *     因为   num++ 并不是  原子操作
 *     
 *     如何解决原子性？
 *     syn？
 *     使用JUC  AutomicInteger原子类
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

		// 需要等待上面的所有线程都执行完毕，在main中查看结果
		while(Thread.activeCount() > 2) {   //  为什么大于2，因为默认后台有两个线程   一个是main线程  一个是GC线程
			Thread.yield();
		}
		System.out.println("finally,the result is "+ myData.num);
		System.out.println("finally,the automic result is "+ myData.atomicInteger);
	}

}
