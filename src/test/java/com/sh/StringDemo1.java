package com.sh;

public class StringDemo1 {
	public static void main(String[] args) {
		char[] ch = {'a','b','c','d','e','f'};
		//调用String构造方法，传递字符数字
		String s = new String(ch);
		System.out.println(s);
		
		String s1 = new String(ch,1,4);
		System.out.println(s1);
	}
}
