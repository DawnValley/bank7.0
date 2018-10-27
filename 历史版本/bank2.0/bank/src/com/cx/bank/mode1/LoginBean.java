  

  package com.cx.bank.mode1;
  /**
  *登录表单bean类
  *@author RG
  *@version 2.0 2018/08/19 17:45:41 
  */
  public class LoginBean 
  {
	  private String username;
	  private String password;
	  public LoginBean()
	  {
	  }
	  public void setUsername(String name)
	  {
		  this.username = name;
	  }
	  public void setPassword(String password)
	  {
		  this.password = password;
	  }
	  public String getUsername()
	  {
		  return username;
	  }
	  public String getPassword()
	  {
		  return password;
	  }
  }
