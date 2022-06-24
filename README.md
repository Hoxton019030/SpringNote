
# Spring

>一開始是由EJB(Enterprise JavaBean)演化而來，用來解決企業開發的複雜性


>是一個IoC跟AoP的輕量級框架

---

# 優點
1. 免費的、開放原始碼的容器
2. 輕量級的、非入侵式的框架
3. 控制反轉 、切面導向
4. 支持事務的處理，對框架整合的支持

###### 3.4.面試必問


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
  private User userDao = new UserDaoImpl();

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
      UserServiceImpl userService = new userServiceImpl();

      userService.getUser();
      //印出「預設」
   }
}

```







