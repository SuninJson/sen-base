package com.tfm.framework.sundry.invokeorder;

public class Son extends Father{
    public static void main(String[] args) {
		/**
		 * 构造顺序：
		 * 1、父类的静态代码块
		 * 2、子类的静态代码块
		 * 3、父类的普通代码块
		 * 4、父类的构造方法
		 * 5、子类的普通代码块
		 * 6、子类的构造方法
		 */
		Son son=new Son();
		System.out.println();

		/**
		 * 调用顺序就是方法的调用顺序
		 */
		son.son2();
		Son.son1();
	}
	static{
		System.out.println("我是子类的静态代码块");
	}
	{
		System.out.println("我是子类的普通代码块");
	}
	public Son(){
		super();
		System.out.println("我是子类的构造方法");
	}
	public static void son1(){
		System.out.println("我是子类的静态方法");
	}
	public void son2(){
		System.out.println("我是子类的普通方法");
		bb();
		aa();
	}
}
