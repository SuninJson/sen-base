package com.tfm.framework.sundry.invokeorder;

public class Father {
    //构造方法
	public Father(){
		System.out.println("我是父类的构造方法");
	}
	//静态代码块
	static{
		System.out.println("我是父类的静态代码块");
	}
	
	//普通代码块
	{
		System.out.println("我是父类的普通代码块");
	}
	
	
	//静态方法
	public static void aa(){
		System.out.println("我是父类的静态方法");
	}
	
	//普通方法
	public void bb(){
		System.out.println("我是父类的普通方法");
	}
}
