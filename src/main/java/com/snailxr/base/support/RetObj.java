package com.snailxr.base.support;

// TODO: Auto-generated Javadoc
/**
 * The Class RetObj.
 */
public class RetObj {
	
	/** The flag. */
	private boolean flag = true;
	
	/** The msg. */
	private String msg;
	
	/** The obj. */
	private Object obj;
	
	/**
	 * Checks if is flag.
	 *
	 * @return true, if is flag
	 */
	public boolean isFlag() {
		return flag;
	}
	
	/**
	 * Sets the flag.
	 *
	 * @param flag the new flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * Gets the obj.
	 *
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}
	
	/**
	 * Sets the obj.
	 *
	 * @param obj the new obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	/**
	 * Instantiates a new ret obj.
	 */
	public RetObj() {

	}
	
	/**
	 * Instantiates a new ret obj.
	 *
	 * @param flag the flag
	 * @param msg the msg
	 * @param obj the obj
	 */
	public RetObj(boolean flag, String msg, Object obj) {
		super();
		this.flag = flag;
		this.msg = msg;
		this.obj = obj;
	}
	
	/**
	 * Instantiates a new ret obj.
	 *
	 * @param flag the flag
	 * @param msg the msg
	 */
	public RetObj(boolean flag, String msg) {
		super();
		this.flag = flag;
		this.msg = msg;
	}
	
}
