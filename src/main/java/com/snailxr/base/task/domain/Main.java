package com.snailxr.base.task.domain;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(print(1));
		int a=1234565789;
	}

	/**
	 * Prints the.
	 *
	 * @param i the i
	 * @return the exception
	 */
	static Exception print(int i){
		if(i>0){
			return new Exception();
		}else{
			throw new RuntimeException();
		}
		
		
	}
}
