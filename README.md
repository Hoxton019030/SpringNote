
# Spring

>一開始是由EJB(Enterprise JavaBean)演化而來，用來解決企業開發的複雜性


>是一個IoC跟AoP的輕量級框架

---

# 優點
> 3.4.面試必問
1. 免費的、開放原始碼的容器
2. 輕量級的、非入侵式的框架
3. 控制反轉 、切面導向
4. 支持事務的處理，對框架整合的支持




總結出來就是: 
>Spring就是一個輕量級的控制反轉和面向切面的框架



# Spring的組成

七大模塊

0. Spring Core (萬物之源)
   + Bean Container
1. Spring AoP
2. Spring ORM
   + Hibernate Support
3. Spring DAO
4. Spring Web
   + WebApplicationContext
5. Spring Context
   + Application
6. Spring MVC


# 弊端

> 發展了太久之後，違背了原來的理念，配置十分繁瑣，人稱配置地獄

# IoC理論推導

### 最初未使用IoC思想
1. UserDao 介面
```java
public interface UserDao{
   void getUser();
}
```
2. UserDaoImpl 實現類
``` java
public class UserDaoImpl imlements UserDao{
   public void getUser(){
      System.out.println("預設");
   }
}
```
``` java
public class UserDaoMySqlImpl imlements UserDao{
   public void getUser(){
      System.out.println("mySQL");
   }
}
```
``` java
public class UserDaoOracalImpl imlements UserDao{
   public void getUser(){
      System.out.println("Oracle");
   }
}
```
3. UserService 業務介紹

```java

```

4. UserServiceImpl 業務實現
```java
public class UserServiceImpl implements UserService{
  private User userDao = new UserDaoOracalImpl();

  public void getUser(){
   userDao.getUser();
  }
}
```

5. Main方法
```java
public class Mytest{
   public static void main (String[] args){
      //使用者實際是調用Service，DAO層不需要接觸。
      UserServiceImpl userService = new UserServiceImpl();

      userService.getUser();
      /*印出「Oracle」
      每一次用戶的需求改變(調用不同資料庫而需要去更動)
      UserServiceImpl userService = new UserDaoOracalImpl();
      很明顯的程式的耦合性太強，違反開放擴充，封閉修改的原則
      程式無法適應用戶的需求變更
      */

   }
}

```

### 使用IoC思想來設計程式

1. UserDao 介面
```java
public interface UserDao{
   void getUser();
}
```

2. UserDaoImpl 實現類
``` java
public class UserDaoImpl imlements UserDao{
   public void getUser(){
      System.out.println("預設");
   }
}
```
``` java
public class UserDaoMySqlImpl imlements UserDao{
   public void getUser(){
      System.out.println("mySQL");
   }
}
```
``` java
public class UserDaoOracalImpl imlements UserDao{
   public void getUser(){
      System.out.println("Oracle");
   }
}
```
3. UserService 業務介紹

```java

```

# 4. UserServiceImpl 業務實現 (這邊修改實例化物件)
```java
public class UserServiceImpl implements UserService{
  private User userDao;
  
  //利用Set，進行動態實現值的注入
  public void setUserDao(UserDao userDo){
   this.userDao= userDao;
  }

  public void getUser(){
   userDao.getUser();
  }
}
```

5. Main方法
```java
public class Mytest{
   public static void main (String[] args){
      //使用者實際是調用Service，DAO層不需要接觸。
      UserService userService = new UserServiceImpl();
      UserServiceImpl.setUserDao(UserDaoMySqlImpl);
      //用set的方式去把值注入進去
      userService.getUser();
      //印出「mySQL」

      UserServiceImpl.setUserDao(UserDaoOracalImpl);
      userService.getUser();
      //印出「Oracle」

      /*讓物件的創造與控制由工程師轉移到客戶，客戶需求更改，工程師不需要去後台變更程式碼
      */
      
   }
}

```

未使用IoC之前，用戶的可能會影響我們原來的代碼，我們需要根據用戶的需求去修改原代碼!如果程式代碼量十大分，修改一次的成本代價十分昂貴

```java
private UserDao userDao;
public void setUserDao(UserDao userDao){
   this.userDao=userDao;
}
```

+ 之前是程序主動創造對象，現在控制權則是可以動態修改的
+ 使用set注入後，程序不在具有主動性，而是變成了被動的接受對象


# 開始使用Spring

> 引入Maven依賴，這裡面包含七大組件

```
 <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
 <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.0.RELEASE</version>
 </dependency>
```
> 配置beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">


	<bean id="hello123" class="helloSpring.Hello">
   <!-- 
   資料型態 變數名稱 =  new 資料型態();
   id = 變數名稱
   class= new 的對象
   property 相當於給對象中的屬性設置一個值
   bean = 物件 new Hello(); 
   -->
	<property name="str" value="Spring"></property>
   <!-- 裡面有個名為str的屬性，他的值是Spring -->
	</bean>
</beans>
```
>使用ClassPathXmlApplicationContext去調用bean，須注意，xml黨必須放在src/main/resources底下才會被讀到
```java
public class Mytest {
	public static void main(String[] args) {
		//獲取Spring上下文對象
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		//我們的對象現在都在Spring裡面管理了，要使用的話，直接去裡面取用就好
		Hello hello = (Hello) context.getBean("hello123");
      //取得名為hello123的bean
		String string = hello.toString();
		System.out.println(string);
      //印出Spring，因為在bean裡面說他的value是Spring
	}
}
```

## 配置Service至Spring

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">


	<bean id="hello123" class="helloSpring.Hello">
	<property name="str" value="Spring"></property>
   </bean>

   <bean id="mysqlImpl" class="helloSpring.UsermysqlImpl">
   <!-- id=Bean的自訂義名字 Class=檔案路徑 -->
   </bean>

   <bean id="UserServiceImpl" class="helloSpring.UserServiceImpl">
         <property name="userDao" ref="mySQLImpl"/>
         <!-- 將UserServiceImpl裡面的userDao物件，讓它參考到name="mysqlImpl"的Bean -->
   </bean>
   
</beans>
```

# IoC創建物件的方式之一_調用無參建構子

```xml
<bean id="User" class="User.User">
	<property name="name" value="許誌展"></property>
	</bean>
```

```java

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

	public void show() {
		System.out.println("name" + name);
	}

}

```

```java
public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		User user = (User)context.getBean("User");
		user.show();
      //印出
      //User的無參建構子
      //name許誌展
      /*
      代表Spring所創建的對象其實是調用無參建構子，因此如果將無參建構子刪掉的話
      則會報錯NoSuchMethodException
      */
	}
}
```



# IoC創建物件的方式之二_調用有參建構子(Constructor Argument Resolution)

## 1. 下標附值 (Constructor Argument Index)

> 從 User.User裡面找到一個有一個參數的建構子，並將value注入近去
```xml
	<bean id="UserConstructor" class="User.User">
	<constructor-arg index="0" value="許誌展">
	</constructor-arg>
	</bean>
```

```java
package User;

public class User {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String name) {
		System.out.println("User的有參建構子");
		System.out.println("name"+name);
	}

	public void show() {
		System.out.println("name" + name);
	}

}
```

```java
public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		User userConstructor = (User)context.getBean("UserConstructor");
      /*
      印出 User的有參建構子
      印出name許誌展
      */
	}
}
```


## 2. 參數附值(Construtor Argument Type Matching) 不建議使用，因為如果有兩個一樣資料型別的就不能用
```xml
	<bean id="UserArgumentConstructor" class="User.User">
	<constructor-arg type="java.lang.String" value="許誌展">
	</constructor-arg>
	</bean>
```
```java
   User userConstructor = (User)context.getBean("UserArgumentConstructor");
```

## 3. 參數名附值 建議使用
```xml
<bean id="UserArgumentNameConstructor" class="User.User">
	      <constructor-arg name="name" value="許誌展"/>
</bean>
```
```java
   User userConstructor = (User)context.getBean("UserArgumentNameConstructor");
```


總結:  在配置文件加載的時候，容器中管理的對象就已經初始化了。




# Spring的配置

1. 別名(alias)
    ```xml
	<bean id="UserArgumentConstructor" class="User.User">
	<constructor-arg type="java.lang.String" value="許誌展">
	</constructor-arg>
	</bean>
   <alias name="UserArgumentConstructor" alias="test"/>
   ```
   ```java
   public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		User userConstructor = (User)context.getBean("test");
	  }
   }
   ```
   
2. Bean的配置
   ```xml
	<bean id="User" class="User.User" name="userNew" >
	<constructor-arg index="0" value="許誌展">
	</constructor-arg>
	</bean>
   ```
   `id:bean的唯一識別符`

   `class:bean物件所對應的全限定名 : packageName+Class`

   `name: 也是別名,而且name可以取多個別名`

3. import

這個import，一般用於團隊開發使用，他可以將多個配置文件(xml)，導入合併為一個xml(applicationContext.xml)
```xml
<import resource="bean.xml">
<import resource="bean2.xml">
<import resource="bean3.xml">
```
# DI依賴注入(Dependency Injection)