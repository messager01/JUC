package com.spx.CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(5);
		
		//  ��һ��������Ϊ���ߵİ汾��    �ڶ�������Ϊ�޸�ֵ
		System.out.println(atomicInteger.compareAndSet(5, 2019)+"  "+"current data:"+ atomicInteger.get());
		System.out.println(atomicInteger.compareAndSet(5, 1024)+"  "+"current data:"+ atomicInteger.get());
	}
}
