package com.snailxr.base.support;

import java.util.Stack;

public class LoadProperty {

	public static Stack<String> mainStack = new Stack<String>();
	
	//public Stack<String> currentStack;
	
	public static void main(String[] args) {

		try {
			if(mainStack.isEmpty()){
				loadUrls();
			}else{
			String key=mainStack.pop();
			System.out.println(key);
}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadUrls(){
		String key=PropertyCache.getInstance().getValue("ds_url");
		mainStack.push(key);
		
		System.out.println(key);
	}
	
}
