package javaConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Mytest {
	public static void main(String[] args) {
		//如果完全使用了配置類方法去做，我們就只能通過AnnotationConfig 上下文來獲取容器，通過配置類的class物件加載。
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
		User user = (User) context.getBean("getUser");
		String name = user.getName();
		System.out.println(name);
	}

}
