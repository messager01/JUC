package com.spx.原子引用类;

import java.util.concurrent.atomic.AtomicReference;

/**
 * JUC中提供了原子整形 AtomicInteger 等，那如果要操作自定义的User  、  Person等该如何操作？
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
