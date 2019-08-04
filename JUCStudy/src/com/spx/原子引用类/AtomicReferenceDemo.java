package com.spx.ԭ��������;

import java.util.concurrent.atomic.AtomicReference;

/**
 * JUC���ṩ��ԭ������ AtomicInteger �ȣ������Ҫ�����Զ����User  ��  Person�ȸ���β�����
 *
 */

class User{
	String userName;
	int age;
	public User(String userName, int age) {
		super();
		this.userName = userName;
		this.age = age;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", age=" + age + "]";
	}
	
	
	
}
public class AtomicReferenceDemo {
	
	public static void main(String[] args) {
		AtomicReference<User> atomicReference = new AtomicReference<>();
		
		User z3 = new User("z3",22);
		User li4 = new User("li4",25);
		
		atomicReference.set(z3);
		System.out.println(atomicReference.compareAndSet(z3, li4)+"    "+atomicReference.get().toString());
		System.out.println(atomicReference.compareAndSet(z3, li4)+"    "+atomicReference.get().toString());
		
	}
}
