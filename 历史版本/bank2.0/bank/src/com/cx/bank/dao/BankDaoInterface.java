  
  
  package com.cx.bank.dao;
  import java.io.*;
import java.util.List;

import com.cx.bank.mode1.*;
  
   /*定义dao层接口
   *@author RG
   *@version 2.0 2018/08/26
   */
   public interface  BankDaoInterface
   {
	   boolean register(UserBean user) throws Exception;//注册方法
       boolean login(UserBean user) throws Exception;//登录方法
       List transactionRecord(String myName) throws Exception;//查询交易记录
	   boolean transfer(String myname,String toname,double money) throws Exception;//转账方法
	   boolean addBank(UserBean user) throws Exception;//存储方法
	   double getBalance(UserBean user) throws Exception;//余额查询方法
   }  