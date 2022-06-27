package javaConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class User {
	private String name;

	public User() {
	}
	public String getName() {
		return name;
	}
	
	@Value("Hoxton123")
	public void setName(String name) {
		this.name = name;
	}

}
