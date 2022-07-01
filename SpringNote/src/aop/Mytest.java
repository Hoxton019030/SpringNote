package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mytest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("aopDiy.xml");
		// 動態代理，代理的是介面
		UserService userService = context.getBean("userServiceImpl", UserService.class);
		userService.select();

	}
}
