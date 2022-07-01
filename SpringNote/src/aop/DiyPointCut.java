package aop;

public class DiyPointCut {
	
	public void before() {
		System.out.println("====方法執行前");
	}
	public void after() {
		System.out.println("====方法執行後");
	}

}
