package com.spx.CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(5);
		
		//  第一个参数就为拿走的版本号    第二个参数为修改值
		System.out.println(atomicInteger.compareAndSet(5, 2019)+"  "+"current data:"+ atomicInteger.get());
		System.out.println(atomicInteger.compareAndSet(5, 1024)+"  "+"current data:"+ atomicInteger.get());
	}
}
