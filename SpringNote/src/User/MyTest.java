package User;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		User user = (User)context.getBean("User");
//		User userConstructor = (User)context.getBean("UserConstructor");
		User userConstructor = (User)context.getBean("UserArgumentConstructor");
		
		user.show();
		
	}
}
