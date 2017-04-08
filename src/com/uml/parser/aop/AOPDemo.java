package com.uml.parser.aop;

public class AOPDemo {

	public static void main(String[] args) {
		AOPDemo obj = new AOPDemo();
		obj.method(2);
		obj.method(1, "Hello");
		obj.methodTwo("World");
	}
	
	public void method(int var){
		System.out.println("Methode one with (INTEGER)");
	}
	
	public void method(int var, String str){
		System.out.println("Methode one with (INTEGER, STRING)");
	}
	
	public void methodTwo(String str){
		System.out.println("Methode two with (STRING)");
	}
}
