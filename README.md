
# Spring

官方文件 : https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-basics

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

1. 建構子注入(上面寫的那些)
```xml
	<bean id="UserConstructor" class="User.User">
	<constructor-arg index="0" value="許誌展">
	</constructor-arg>
	</bean>
```
  ```xml
	<bean id="User" class="User.User" name="userNew" >
	<constructor-arg index="0" value="許誌展">
	</constructor-arg>
	</bean>
   ```

2. Set方式注入(**最重要**)
+ 依賴注入:Set注入
   + **依賴:bean物件的創建依賴於容器**
   + **注入:bean物件中的所有屬性，由容器來注入**

範例:
> 複雜類型
```java
public class Address {
	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

}
```
> 真實測試對象
```java
public class Student {
	private String name;
	private Address address;
	private String[] books;
	private List<String> hobbys;
	private Map<String,String> card;
	private Set<String> games;
	private String wife;
	private Properties info;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String[] getBooks() {
		return books;
	}
	public void setBooks(String[] books) {
		this.books = books;
	}
	public List<String> getHobbys() {
		return hobbys;
	}
	public void setHobbys(List<String> hobbys) {
		this.hobbys = hobbys;
	}
	public Map<String, String> getCard() {
		return card;
	}
	public void setCard(Map<String, String> card) {
		this.card = card;
	}
	public Set<String> getGames() {
		return games;
	}
	public void setGames(Set<String> games) {
		this.games = games;
	}
	public String getWife() {
		return wife;
	}
	public void setWife(String wife) {
		this.wife = wife;
	}
	public Properties getInfo() {
		return info;
	}
	public void setInfo(Properties info) {
		this.info = info;
	}

}
```
> xml配置
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
	<bean id="address" class="Student.Address"></bean>

	<bean id="student" class="Student.Student">
		<!-- 1.普通值注入 -->
		<property name="name" value="Hoxton"></property>
		<!-- 2.bean注入 -->
		<property name="address" ref="address"></property>
		<!-- 3.Array注入 -->
		<property name="books">
			<array>
				<value>The lord of rings</value>
				<value>Mistborn</value>
				<value>Hally Porter</value>
			</array>
		</property>
		<!-- 4.List注入 -->
		<property name="hobbys">
			<list>
				<value>listen Music</value>
				<value>coding</value>
				<value>Watch Movie</value>
			</list>
		</property>
		<!-- 5.Map注入 -->
		<property name="card">
			<map>
				<entry key="身分證" value="S123456789"></entry>
				<entry key="信用卡" value="1234-5678-9"></entry>
			</map>
		</property>
		<!-- 6. Set注入 -->
		<property name="games">
			<set>
				<value>LOL</value>
				<value>Apex</value>
				<value>Dota2</value>
			</set>
		</property>
		<!-- 7.設置為null -->
		<property name="wife">
			<null></null>
		</property>
		<!-- 8.Property注入 -->
		<property name="info">
			<props>
				<prop key="driver">20190525</prop>
				<prop key="url">男</prop>
				<prop key="username">小明</prop>
			</props>
		</property>
	</bean>

</beans>
```
> 測試類

```java
public class MyTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("student.xml");
		Student student = (Student) context.getBean("student");
		System.out.println(student.getName());
	}
}
```

# P命名空間(Set注入property)與C(建構子注入constructor)命名空間,不實用知道就好


> Java bean
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
	public User(String name) {
		System.out.println("User的有參建構子");
		System.out.println("name"+name);
	}


	public void show() {
		System.out.println("name" + name);
	}
//建構子注入，所以需要有參建構子
	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
}

```
> xml 配置
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="Student.User" p:name="Hoxton" p:age="22"></bean>
  <bean id="user2" class="Student.User" c:age="18" c:name="Hoxton"></bean>
</beans>
```


# Bean的作用域

+ singleton(全局唯一，也是Spring的默認機制):
  + 只有創建一個實例，不論後續有多少個Dao，始終用的都只有一個實例
  + 在創建容器的時候就產生了
  + 適合單線程 

+ prototype(每個物件獨立)
  + 每次創建不同實例
  + 在get方法的時候產生
  + 相對來講比較浪費資源


+ request
+ session
+ application
+ websocket

上面四個都是Web的東西，先擱著唄^_^


# 單例設計模式是什麼?(額外補充)
+ 核心概念:建構子私有化，如此一來別的物件就不能創建它，保證記憶體中只有一個物件
+ 只會有一個實體物件
+ 該物件必須由單例類自行創造
+ 單例類對外提供「一個」對外的全域訪問點


1. Hungry(餓漢式)
```java
package singleton;

public class Hungry {

	private byte[] data1 = new byte[1024 * 1024];
	private byte[] data2 = new byte[1024 * 1024];
	private byte[] data3 = new byte[1024 * 1024];
	private byte[] data4 = new byte[1024 * 1024];

	// 1.建構子私有化，外部無法創建該對象
	private Hungry() {
	}

	// 2.自己創建對象，變數被final修飾後，不能再指向其他對象，但指向對象的內容是可以被改變的
	private final static Hungry HUNGRY = new Hungry();

    // 3.提供對應的獲取方法
	public static Hungry getInstance() {
		return HUNGRY;
	}
}
```

2. LazyMan(懶漢式)
```java
public class LazyMan {

	private byte[] data1 = new byte[1024 * 1024];
	private byte[] data2 = new byte[1024 * 1024];
	private byte[] data3 = new byte[1024 * 1024];
	private byte[] data4 = new byte[1024 * 1024];

    //1.建構子私有化
	private LazyMan() {
	}

	//2.創建LazyMan的變量，但沒有具體引用，這就是差異，為了避免資源的浪費，只要這個類沒有被使用，則不創建，待調用方法時才創建，
	private static LazyMan LazyMan;

	public static LazyMan getInstance() {
		if(LazyMan==null) {
			LazyMan=new LazyMan();
		}
		return LazyMan;
	}

}
```


# Bean的自動裝配
+ 撰寫xml就是手動裝配
+ 自動裝配是Spring滿足bean依賴的一種方式
+ Spring會在上下文忠自動尋找，並自動給bean裝配屬性

在Spring中有三種裝配的方式
1. 在xml中顯示的配置
2. 在java中顯示配置
3. **隱式的自動裝配bean**(最重要)


### 自動裝配的概念

1. byName :
autowired byname 會自動在容器上下文中查找，和自己物件set方法後面的質對應的bean id，故beanid不能亂改
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="cat" class="autowired.pojo.Cat"></bean>
	<bean id="dog" class="autowired.pojo.Dog"></bean>
	<!-- autowired byname 會自動在容器上下文中查找，和自己物件set方法後面的質對應的bean id -->
	<bean id="people" class="autowired.pojo.People" autowire="byName">
		<property name="name" value="Hoxton"></property>
	</bean>

</beans>
```
2. byType:
從容器的上下文中查找，和自己物件類型相同的的bean!，此方法只能用在創建一個實例對象，若重複的話類型也會重複，會出現衝突。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="cat123" class="autowired.pojo.Cat"></bean>
	<bean id="dog123" class="autowired.pojo.Dog"></bean>
	<!-- 從容器的上下文中查找，和自己物件類型相同的的bean! -->
	<bean id="people" class="autowired.pojo.People" autowire="byType">
		<property name="name" value="Hoxton"></property>
	</bean>
</beans>
```


### 小結
+ byName時，需要保證所有bean的id唯一，並且這個bean需要和自動注入的屬性的set方法的值一致
+ byType時，需要保證所有bean的class唯一，並且這個bean需要和自動注入的屬性的類型一致



# 使用Annotaion(@Autowired)自動裝配

是先透過byType的方式注入，若有重複類型的bean，則再使用byName方法注入。直接在屬性上使用即可，要用在set方法上也是可啦，但不推


### 常用註解

1. @Autowired :自動裝配 優先順序1.byType 2.byName

2. @Nullable  :字段標記了這個註解，說明這個字段可以為Null

3. @Resource  :自動裝配 ByName

4. @Component :組件的意思，寫在Class上方，說明這個類被Spring控管，也就是Bean
	他本身有幾個衍生的註解，**功能和@Component完全一樣**，只是依照MVC架構命名，分別是
	+ dao 【@Repository】 :用來寫在Dao上
	+ Service【@Service】 :寫在Service層
	+ Controller【@Controller】 :寫在Controller上

	
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
		<!-- 使用這個標籤來掃描 annotaion這個package底下的所有Class 
		package底下的Class就會被自動註冊至Spring容器裡面，成為bean-->
	    <context:component-scan
		base-package="annotation"></context:component-scan>
</beans>
  ```
  ```java
  package annotation;

 import org.springframework.stereotype.Component;

 //等價於在xml裡註冊了<bean id="user" class="annotation.User"/>
 //@Component組件
 @Component
 public class User {
	public String name ="Hoxton123";
}
```


---

#### 豆知識
1. 若xml裡面配置了重複類型的bean，例如
```xml
  <bean id="cat1" class="autowired.pojo.Cat" />
  <bean id="cat2" class="autowired.pojo.Cat" />
	<bean id="dog1" class="autowired.pojo.Dog" />
	<bean id="dog2 class="autowired.pojo.Dog" />
	<bean id="people" class="autowired.pojo.People" />
```
若直接使用@Autowired
```java
@Autowired
	private Cat cat;
```
則Spring會不知道我們要找的是哪一個，因此要使用@Qualifier(限定詞)來標註到底具體要使用哪一個beanid
```java
@Autowired
@Qualifier(value="cat1")
	private Cat cat;
```

 ---

要使用註解須知:
1. 導入約束。　`context約束`
2. 配置註解的支持 `<context:annotation-config/>`


範例:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
      https://www.springframework.org/schema/beans/spring-beans.xsd
      http://www.springframework.org/schema/context
      https://www.springframework.org/schema/context/spring-context.xsd">

  <context:annotation-config/>
  
  <bean id="cat" class="autowired.pojo.Cat" />
	<bean id="dog" class="autowired.pojo.Dog" />
	<bean id="people" class="autowired.pojo.People" />

</beans>
```
```java
package autowired.pojo;

import org.springframework.beans.factory.annotation.Autowired;

public class People {

	@Autowired
	private Cat cat;
	@Autowired
	private Dog dog;
	private String name;

	public People(Cat cat, Dog dog, String name) {
		this.cat = cat;
		this.dog = dog;
		this.name = name;
	}
	

	public People() {
	}


	@Override
	public String toString() {
		return "People [cat=" + cat + ", dog=" + dog + ", name=" + name + "]";
	}

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
```

### 小結(XML爽別人，註解爽自己)
+ xml
	+ xml更萬能，適用於任何場合，維護簡單方便
	+ 
+ 註解
	+ 不是自己的類使用不了，維護相對複雜

# 使用Java的方式配置Spring(完全不使用xml)

JavaConfig是Spring的一個子項目，在Spring4後，成為了一個核心的功能，調用時是用`AnnotationConfigApplicationContext`

1. 一樣撰寫pojo
```java
public class User {
	private String name;

	public User() {
	}
	public String getName() {
		return name;
	}
	
	@Value("Hoxton123")
	//將name附值為Hoxton123
	public void setName(String name) {
		this.name = name;
	}
}
```
2. 撰寫config類
```java
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

```
3. 撰寫測試類
```java
package javaConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Mytest {
	public static void main(String[] args) {
		//如果完全使用了配置類方法去做，我們就只能通過AnnotationConfig 上下文來獲取容器，通過配置類的class物件加載。
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
		User user = (User) context.getBean("getUser");
		String name = user.getName();
		System.out.println(name);
		//印出Hoxton123
	}

}

```


如此一來就不需要配置任何xml檔



---

# 了解AOP之代理模式

## 靜態代理
+ 抽象角色: 逼班會使用介面或抽象類來解決
+ 真實角色: 被代理的角色
+ 代理角色: 代理真實角色，代理真實角色後，我們一班會做一些附屬操作
+ 客戶: 訪問代理對象的人


# 完全看不懂 代理模式先跳過 好難喔


# AOP 剖面導向程式設計

AOP是OOP的延續，是軟體開發中的一個熱門重點，也是Spring框架的一個重要內容，利用AOP可以對業務邏輯的各個部份進行隔離，從而使得業務邏輯各部分之間的耦合度降低，提高程式的可重用性，同時提高了開發效率。

需導入的依賴
```xml
	<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>1.9.4</version>
		</dependency>
```

在SpringAOP中透過Advice定義了剖面邏輯，Spring支援五種類型的Advice

通知類型
1. 前置通知 
	+ 方法前
		+ org.springframework.aop.MethodBeforeAdvice
2. 後置通知
	+ 方法後
		+ org.springframework.aop.AfterReturningAdvice
3. 環繞通知
	+ 方法前後
		+ org.springframework.aop.intercept.MethodInterceptor
4. 異常拋出通知
	+ 方法拋出異常
		+ org.springframework.aop.ThrowsAdvice
5. 引介通知
	+ 類中增加新的方法屬性
		+ org.springframework.aop.IntroductionInterceptor
	

## 方法一:使用Spring的API介面【主要SpringAPI介面實現】

使用<aop:config>的方式去設置

```java
package aop;

public interface UserService {
	public void add();
	public void delete();
	public void update();
	public void select();

}
```
```java
package aop;

public class UserServiceImpl implements UserService{

	@Override
	public void add() {
		// TODO Auto-generated method stub
		System.out.println("增加用戶");
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("刪除用戶");
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("更新用戶");
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		System.out.println("查詢用戶");
		
	}

}
```
```java
package aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class Log implements MethodBeforeAdvice{

	@Override
	//method:要執行的目標物件的方法
	//arg:參數
	//target :目標對象
	public void before(Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(target.getClass().getName()+"的"+method.getName()+"被執行了");
		//getName(Class的名字)
		
		
	}

}
```
```java
package aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class AfterLog implements AfterReturningAdvice{

	@Override
	// returnValue:返回值
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("執行了"+method.getName()+"返回結果為:"+returnValue);
		
	}

}
```
```java
package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mytest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
		// 動態代理，代理的是介面
		UserService userService = context.getBean("userServiceImpl", UserService.class);
		userService.select();

	}
}
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

	<!-- 註冊bean Start -->
	<bean id="userServiceImpl" class="aop.UserServiceImpl"></bean>
	<bean id="log" class="aop.Log"></bean>
	<bean id="afterLog" class="aop.AfterLog"></bean>
	<!-- 註冊bean End -->

	<!-- 方式一:使用原生Spring API接口 -->
	<!-- 配置aop Start -->
	<aop:config>
		<!-- 切入點:pointcut, expression:表達式 execution(要執行的位置! *(存取範圍修飾子) *(返回值)*(類名)*(方法名)*(參數)) -->
		<aop:pointcut id="pointcut"
			expression="execution(* aop.UserServiceImpl.*(..))" />
		<!-- 執行環繞 -->
		<aop:advisor advice-ref="log" pointcut-ref="pointcut" />
		<aop:advisor advice-ref="afterLog" pointcut-ref="pointcut" />
<!-- 		把log這個Class橫向切入UserServiceImpl這個Class裡面的方法中 -->
	</aop:config>
	<!-- 配置aop End -->


</beans>
```
## 方法二:自定義類來實現AOP【主要是自定義切面類】

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

	<!-- 註冊bean Start -->
	<bean id="userServiceImpl" class="aop.UserServiceImpl"></bean>
	<bean id="log" class="aop.Log"></bean>
	<bean id="afterLog" class="aop.AfterLog"></bean>
	<!-- 註冊bean End -->

	<!-- 方式二 自定義類別 -->
	<bean id="diy" class="aop.DiyPointCut"></bean>
	<aop:config>
		<!-- 自定義切面，把類定義成切面，ref:要印用的類id -->
		<aop:aspect ref="diy">
			<!-- Start切入點 -->
			<aop:pointcut
				expression="execution(* aop.UserServiceImpl.*(..))" id="point" />
			<!-- End切入點 -->
			<aop:before method="before" pointcut-ref="point" />
			<aop:after method="after" pointcut-ref="point" />
		</aop:aspect>
	</aop:config>

</beans>
```

## 方式三:使用註解來實現

再類上使用@Aspect，將整個類轉化為一個切面，接著再在方法上面寫上
+ @Before
+ @After
+ @Around
將整個方法切入進想增加的業務邏輯裡，`"execution(* aop.UserServiceImpl.*(..))"`，其中的execution是固定寫法。
```java
	package aop;

	import org.aspectj.lang.ProceedingJoinPoint;
	import org.aspectj.lang.Signature;
	import org.aspectj.lang.annotation.After;
	import org.aspectj.lang.annotation.Around;
	import org.aspectj.lang.annotation.Aspect;
	import org.aspectj.lang.annotation.Before;

	@Aspect
	public class AnnotationPointCut {

		@Before("execution(* aop.UserServiceImpl.*(..))")
		public void before() {
			System.out.println("====方法執行前");
		}
		@After("execution(* aop.UserServiceImpl.*(..))")
		public void after() {
			System.out.println("====方法執行後");
		}
		
		//在環繞增強中，我們可以給定一個參數，代表我們要獲取處理切入的點
		@Around("execution(* aop.UserServiceImpl.*(..))")
		public void around(ProceedingJoinPoint jp) throws Throwable {
			System.out.println("環繞前");
			Signature signature = jp.getSignature(); //獲得類的資訊 signature:void aop.UserService.select()
			System.out.println("signature:"+signature);
			//執行方法，相當於invoke
			Object proceed = jp.proceed();
			System.out.println("環繞後");
			System.out.println(proceed);
		}

	}

```


# 聲明式事務(transaction)
1. 回顧事務(ACID)
	+ 原子性(Atomicity)：要嘛全部執行，要嘛都不執行，沒有執行一半的，若出錯則全部回滾(Roll Back)至執行前狀態。

	+ 一致性(Consistency):事務成功後，資料庫所處的狀態和它的業務規則是一致的，即資料不會被破壞，舉例來說，A帳戶轉帳100到B帳戶，不論操作成功與否，A和B的帳戶存款總額都不會改變。

	+ 隔離性(Isolation):在多執行序的情況下，不同的事務有各自的資料空間，它們的操作不會影響到彼此進而產生干擾。

	+ 持久性(Durability):一旦事務提交成功後，事務中所有的資料操作都必須被持久化保存到資料庫中，即使在事物提交後，資料庫馬上崩潰，在資料庫重啟時，也必須保證能夠通過某種機制恢復資料。