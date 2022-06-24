package Mytest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import Entity.Hello;


public class Mytest {
	public static void main(String[] args) {
		//獲取Spring上下文對象
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		//我們的對象現在都在Spring裡面管理了，要使用的話，直接去裡面取用就好
		
		Hello hello = (Hello) context.getBean("hello");
		String string = hello.toString();
		System.out.println(string);
		
	}

}
