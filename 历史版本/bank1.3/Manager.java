  
  
  package com.cx.bank.manager;
  /**
  *<b>业务层Manager接口</b>
  *@author RG
  *@version 1.3 2018/07
  */
  public interface Manager 
  {
	  void inquiry();//查询金额
	  void withDrawals(double m)throws Exception;//用户取款
	  void deposit(double m)throws Exception;//用户存款
	  boolean transfer(String username,double money);//转账
	  boolean register(String userName,String userPwd);//注册
	  boolean login(String userName,String userPwd);//登录
	  void secede();//退出系统
  }
