
package com.cx.bank.dao;

import java.io.*;
import java.util.List;
import java.util.Map;

import com.cx.bank.mode1.*;
import com.cx.bank.util.PageModel;

/*定义dao层接口
*@author RG
*@version 2.0 2018/08/26
*/
public interface BankDaoInterface {
	boolean register(UserBean user) throws Exception;// 注册方法

	boolean login(UserBean user) throws Exception;// 登录方法

	Map getAccountInformation(String userName) throws Exception;// 查询方法

	PageModel getTransactionRecord(String myName,String queryStr, int pageNo, int pageSize) throws Exception;// 查询交易记录

	int withDrawals(String name, double m) throws Exception;// 用户取款

	int deposit(String name, double m) throws Exception;// 用户存款

	boolean transfer(String myname, String toname, double money) throws Exception;// 转账方法


}