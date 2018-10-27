  
  
  package com.cx.bank.dao;
  import com.cx.bank.mode1.*;
  
   /*定义dao层接口
   *@author RG
   *@version 1.3 2018/07
   */
   public interface  BankDaoInterface
   {
	   boolean register(UserBean user);//注册方法
       boolean login(UserBean user);//登录方法
	   boolean transfer(String name,double money);//转账方法
	   boolean addBank(UserBean user);//存储方法
	   double getBalance(UserBean user);//余额查询方法
   }  