package Mytest;

import proxy.Proxy;

public class ttest {
	public static void main(String[] args) {
		String s1 = "runoob";
		String s2 = "runoob";
		System.out.println(s1 == s2);
		System.out.println("s1:" + s1.hashCode());
		System.out.println("s2:" + s2.hashCode());

		String s3 = new String("runoob");
		System.out.println("s3:" + s3.hashCode());
		System.out.println(s1.equals(s3));
		Object a = new Object();
		Proxy proxy = new Proxy();
	

	}
	
}
