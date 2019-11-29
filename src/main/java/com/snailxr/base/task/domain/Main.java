package com.snailxr.base.task.domain;

public class Main {

	public static void main(String[] args) {
		System.out.println(print(1));
		int a=1234565789;
	}

	static Exception print(int i){
		if(i>0){
			return new Exception();
		}else{
			throw new RuntimeException();
		}
		
		
	}
}
