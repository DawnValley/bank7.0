package com.cx.bank.util;
/**
 * 银行系统一些异常通用的异常类，用一个异常类来处理多个异常对象
 * @author RG
 * @version 2018/09/13
 */
public class BankPublicException extends RuntimeException {

	private String errorKey;//国际化资源文件的key
	private Object[] args;//动态填充符
	
	public BankPublicException(String errorKey){
		this.errorKey = errorKey;
	}
	public BankPublicException(String errorKey,Object[] args){
		this.errorKey = errorKey;
		this.args = args;
	}
	public BankPublicException(String errorKey,String arg){
		this(errorKey,new Object[]{arg});
	}
	public String getErrorKey() {
		return errorKey;
	}
	public Object[] getArgs() {
		return args;
	}
}
