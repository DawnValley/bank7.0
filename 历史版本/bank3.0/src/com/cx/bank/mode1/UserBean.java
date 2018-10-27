  
  
  package com.cx.bank.mode1;
  
  /**定义一个用户类
   *包含用户的姓名以及密码
   *@author RG
   *@version 1.3 2018/07
   */
  public class UserBean  
  {
	  private String username;
	  private String password;
      
      /*无参构造器*/
	  public UserBean(){
	  }
  
      /*有参构造器*/
	  public UserBean(String username,String pWd){
	     this.username=username;
		 this.password=password;
	  }

      /*设置姓名*/
      public void setUsername(String username){
	      this.username=username;
	  }

      /*取得姓名*/
	  public String getUsername(){
	       return this.username;
	  }
    
	  /*设置密码*/
	  public void setPassword(String password){
	      this.password=password;
	  }
      
	  /*取得密码*/
	  public String getPassword(){
	       return this.password;
	  }
	
  }
