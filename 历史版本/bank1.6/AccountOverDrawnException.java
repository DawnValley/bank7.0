  
  
  package com.cx.bank.util;
  /**
  *<b>取款超出余额异常类</b>
  *@author RG
  *@version 1.2 2018/06/21
  */
  public class AccountOverDrawnException extends RuntimeException 
  {
	 public AccountOverDrawnException()//无参的构造方法
	 {
		 super();
	 }
	 public AccountOverDrawnException(String msg)//有参的构造方法
	 {
		 super(msg);
	 }
  }
