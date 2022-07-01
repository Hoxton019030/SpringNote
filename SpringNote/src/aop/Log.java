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
