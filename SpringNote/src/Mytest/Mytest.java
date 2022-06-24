package Mytest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import Entity.Hello;

public class Mytest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		// 獲取Spring上下文對象
		Hello hello = (Hello) context.getBean("hello123");
		// 我們的對象現在都在Spring裡面管理了，要使用的話，直接去裡面取用就好
		// 取用名為hello123的bean

		String string = hello.toString();
		System.out.println(string);

	}

}
