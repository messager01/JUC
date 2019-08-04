package com.spx.集合类的线程安全;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;


//    很容易出现java.util.ConcurrentModificationException  并发修改异常
public class ContainerNotSafeDemo extends Thread{
	static List<String> list = null;
	
	public static void main(String[] args) {
		
		//list = new ArrayList<>();
		list  = new CopyOnWriteArrayList<>();
		for(int i = 1 ;i <=3 ;i++) {
			new ContainerNotSafeDemo().start();
		}
		
		while(Thread.activeCount() > 2) {   //  为什么大于2，因为默认后台有两个线程   一个是main线程  一个是GC线程
            Thread.yield();
       }
		System.out.println(list);
	}

	@Override
	public void run() {
		list.add(UUID.randomUUID().toString().substring(0,8));
	}
	
	/**
	 *    故障现象：很容易出现java.util.ConcurrentModificationException  并发修改异常
	 *    
	 *    导致原因：  并发争抢修改导致
	 *    		     一个人正在写，另一个人过来抢，导致数据情况不一致，并发修改异常。
	 *    
	 *    解决方案：  1.Vector?
	 *            2.Collections.synchronizedCollection(集合实例)
	 *            3.new CopyOnWriteArrayList();    写时复制      读写分离的思想   
	 *        CopyOnWrite容器即写时复制的容器，往容器添加元素的时候，不直接往当前容器Objcet[] 添加，而是对当前容器Object[]进行copy，
	 *        复制出一个新的容器，然后向新的容器中添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是可以对CopyOnWrite容器
	 *        进行并发读，且不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器，扩容扩一个。
	 *            
	 *    
	 *    优化建议：
	 */
	
}
