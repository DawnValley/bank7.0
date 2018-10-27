  
  
  package com.cx.bank.manager;
  /**
  *<b>业务层Manager接口</b>
  *@author RG
  *@version 1.2 2018/06/21
  */
  public interface Manager 
  {
	  void inquiry();//查询金额
	  void withDrawals(double m)throws Exception;//用户取款
	  void deposit(double m)throws Exception;//用户存款
	  void secede();//退出系统
  }
