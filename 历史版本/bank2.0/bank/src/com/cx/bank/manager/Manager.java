
package com.cx.bank.manager;

import java.io.*;
import java.util.List;

/**
 * <b>业务层Manager接口</b>
 * 
 * @author RG
 * @version 2.0 2018/08/26
 */
public interface Manager {
	String inquiry();// 查询金额

	List transactionRecord(String myName) throws Exception;// 查询交易记录

	boolean withDrawals(double m) throws Exception;// 用户取款

	boolean deposit(double m) throws Exception;// 用户存款

	boolean transfer(String username, String toName, double money) throws Exception;// 转账

	boolean register(String userName, String userPwd) throws Exception;// 注册

	boolean login(String userName, String userPwd) throws Exception;// 登录

	void secede() throws Exception;// 退出系统
}
