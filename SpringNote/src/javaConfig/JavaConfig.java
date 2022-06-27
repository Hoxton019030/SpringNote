package javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//相當於xml裡面的beans，也會將這個類交由Spring託管，註冊到容器中，因為他本身也是一個@Component
//@Configuraion本身就是一個配置類，就和我們之前看bean.xml是一樣的
public class JavaConfig {

	@Bean
//	相當於在xml裡面配置
//	<bean id="getUSer" ref="User">
	public User getUser() {
		return new User(); //返回要注入到bean的物件
	}
}
