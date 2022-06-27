package autowired.pojo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowired.xml");
		People people = context.getBean("people",People.class);
		people.getCat().shout();
		people.getDog().shout();
		
		
	}

}
