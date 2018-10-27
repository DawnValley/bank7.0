  
  
  package com.cx.bank.util;
  /**
  *<b>存款为负数异常类</b>
  *@author RG
  *@version 1.2 2018/06/21
  */
  public class  InvalidDepositException extends ArithmeticException
  {
	 public InvalidDepositException()//无参的构造方法
	 {
		 super();
	 }
	 public InvalidDepositException(String msg)//有参的构造方法
	 {
		 super(msg);
	 }
  }
