package com.snailxr.base.support;

import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadProperty.
 */
public class LoadProperty {

	/** The main stack. */
	public static Stack<String> mainStack = new Stack<String>();
	
	//public Stack<String> currentStack;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
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

	/**
	 * Load urls.
	 */
	public static void loadUrls(){
		String key=PropertyCache.getInstance().getValue("ds_url");
		mainStack.push(key);
		
		System.out.println(key);
	}
	
}
