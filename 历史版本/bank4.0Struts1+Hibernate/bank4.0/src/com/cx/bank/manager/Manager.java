
package com.cx.bank.manager;

import java.io.*;
import java.util.List;
import java.util.Map;

import com.cx.bank.mode1.UserBean;
import com.cx.bank.mode1.ValueObjectBean;
import com.cx.bank.util.PageModel;

/**
 * <b>业务层Manager接口</b>
 * 
 * @author RG
 * @version 2.0 2018/08/26
 */
public interface Manager {

	boolean register(ValueObjectBean vob) throws Exception;// 注册

	boolean login(ValueObjectBean vob) throws Exception;// 登录

	UserBean getAccountInformation(String userName) throws Exception;// 余额查询方法

	PageModel getTransactionRecord(ValueObjectBean vob) throws Exception;// 查询交易记录

	int withDrawals(ValueObjectBean vob) throws Exception;// 用户取款

	int deposit(ValueObjectBean vob) throws Exception;// 用户存款

	int transfer(ValueObjectBean vob) throws Exception;// 转账

	
	/**
	 * @Deprecated 不建议使用
	 * @throws Exception
	 */
	void secede() throws Exception;// 退出系统
}
