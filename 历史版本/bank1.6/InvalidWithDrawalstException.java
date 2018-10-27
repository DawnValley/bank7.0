  
  
  package com.cx.bank.util;
  /**
  *<b>取款为负数异常类</b>
  *@author RG
  *@version 1.2 2018/06/21
  */
  public class InvalidWithDrawalstException extends ArithmeticException 
  {
	 public InvalidWithDrawalstException()//无参的构造方法
	 {
		 super();
	 }
	 public InvalidWithDrawalstException(String msg)//有参的构造方法
	 {
		 super(msg);
	 }
  }
