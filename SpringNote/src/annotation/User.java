package annotation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//等價於在xml裡註冊了<bean id="user" class="annotation.User"/>
//@Component組件
@Repository
public class User {
	public String name ="Hoxton123";

	public User() {
	}
	

}
