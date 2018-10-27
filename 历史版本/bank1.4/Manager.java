  
  
  package com.cx.bank.manager;
  import java.io.*;
  /**
  *<b>业务层Manager接口</b>
  *@author RG
  *@version 1.3 2018/07
  */
  public interface Manager 
  {
	  void inquiry();//查询金额
	  void withDrawals(double m);//用户取款
	  void deposit(double m);//用户存款
	  boolean transfer(String username,double money) throws Exception;//转账
	  boolean register(String userName,String userPwd) throws Exception;//注册
	  boolean login(String userName,String userPwd) throws Exception;//登录
	  void secede() throws Exception;//退出系统
  }
