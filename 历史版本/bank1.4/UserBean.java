  
  
  package com.cx.bank.mode1;
  
  /**定义一个用户类
   *包含用户的姓名以及密码
   *@author RG
   *@version 1.3 2018/07
   */
  public class UserBean  
  {
	  private String userName;
	  private String pwd;
      
      /*无参构造器*/
	  public UserBean(){
	  }
  
      /*有参构造器*/
	  public UserBean(String userName,String pWd){
	     this.userName=userName;
		 this.pwd=pwd;
	  }

      /*设置姓名*/
      public void setUserName(String userName){
	      this.userName=userName;
	  }

      /*取得姓名*/
	  public String getUserName(){
	       return this.userName;
	  }
    
	  /*设置密码*/
	  public void setPwd(String pwd){
	      this.pwd=pwd;
	  }
      
	  /*取得密码*/
	  public String getPwd(){
	       return this.pwd;
	  }
	
  }
