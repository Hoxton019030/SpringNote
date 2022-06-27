package Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("student.xml");
		Student student = (Student) context.getBean("student");
//		System.out.println(student.getName());
		String[] books = student.getBooks();
		List<String> hobbys = student.getHobbys();
		Map<String, String> card = student.getCard();
		Set<String> games = student.getGames();
//		System.out.println(student.getWife());
		for (String string : games) {
			System.out.println(string);
		}
		String string2 = card.get("身分證");
		System.out.println(string2);
		
		
		
		
		for (String string : books) {
			System.out.println(string);
		}
		for (String string : hobbys) {
			System.out.println(string);
		}
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext("autowired.xml");

	}
}
