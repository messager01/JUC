package com.spx.��������̰߳�ȫ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;


//    �����׳���java.util.ConcurrentModificationException  �����޸��쳣
public class ContainerNotSafeDemo extends Thread{
	static List<String> list = null;
	
	public static void main(String[] args) {
		
		//list = new ArrayList<>();
		list  = new CopyOnWriteArrayList<>();
		for(int i = 1 ;i <=3 ;i++) {
			new ContainerNotSafeDemo().start();
		}
		
		while(Thread.activeCount() > 2) {   //  Ϊʲô����2����ΪĬ�Ϻ�̨�������߳�   һ����main�߳�  һ����GC�߳�
            Thread.yield();
       }
		System.out.println(list);
	}

	@Override
	public void run() {
		list.add(UUID.randomUUID().toString().substring(0,8));
	}
	
	/**
	 *    �������󣺺����׳���java.util.ConcurrentModificationException  �����޸��쳣
	 *    
	 *    ����ԭ��  ���������޸ĵ���
	 *    		     һ��������д����һ���˹��������������������һ�£������޸��쳣��
	 *    
	 *    ���������  1.Vector?
	 *            2.Collections.synchronizedCollection(����ʵ��)
	 *            3.new CopyOnWriteArrayList();    дʱ����      ��д�����˼��   
	 *        CopyOnWrite������дʱ���Ƶ����������������Ԫ�ص�ʱ�򣬲�ֱ������ǰ����Objcet[] ��ӣ����ǶԵ�ǰ����Object[]����copy��
	 *        ���Ƴ�һ���µ�������Ȼ�����µ����������Ԫ�أ������Ԫ��֮���ٽ�ԭ����������ָ���µ��������������ĺô��ǿ��Զ�CopyOnWrite����
	 *        ���в��������Ҳ���Ҫ��������Ϊ��ǰ������������κ�Ԫ�ء�����CopyOnWrite����Ҳ��һ�ֶ�д�����˼�룬����д��ͬ��������������һ����
	 *            
	 *    
	 *    �Ż����飺
	 */
	
}
