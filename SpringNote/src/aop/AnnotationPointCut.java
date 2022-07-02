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
