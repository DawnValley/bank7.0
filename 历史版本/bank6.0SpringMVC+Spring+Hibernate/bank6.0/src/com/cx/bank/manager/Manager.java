
package com.cx.bank.manager;

import com.cx.bank.model.UserBean;
import com.cx.bank.util.PageModel;

/**
 * <b>业务层Manager接口</b>
 * 
 * @author RG
 * @version 6.0 2018/10/03
 */
public interface Manager {

	boolean register(UserBean ub) throws Exception;// 注册

	boolean login(UserBean ub) throws Exception;// 登录

	UserBean getAccountInformation(String userName) throws Exception;// 余额查询方法

	PageModel getTransactionRecord(String myName,String queryStr,int pageNo,int pageSize) throws Exception;// 查询交易记录

	int withDrawals(String username, double m) throws Exception;// 用户取款

	int deposit(String username, double m) throws Exception;// 用户存款

	int transfer(String username,String toName, double money) throws Exception;// 转账

	
	/**
	 * @throws Exception
	 */
	void secede(String role,String name) throws Exception;// 退出系统
}
