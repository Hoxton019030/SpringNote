package annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("annotation.xml");
        //1. 預設名稱為類名稱的小寫
		User user = context.getBean("user", User.class);
		System.out.println(user.name);
	}

}
