package User;

public class User {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User() {
		System.out.println("User的無參建構子");
	}
	public User(String name) {
		System.out.println("User的有參建構子");
		System.out.println("name"+name);
	}


	public void show() {
		System.out.println("name" + name);
	}

}
