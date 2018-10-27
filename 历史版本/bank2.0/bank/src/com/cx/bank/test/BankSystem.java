
package com.cx.bank.test;

import java.util.List;
import java.util.Map;

import com.cx.bank.manager.*;
import com.cx.bank.mode1.*;

/**
 * 控制层
 * 
 * @author RG
 * @version 2.0 2018/08/20 17:17:55
 */
public class BankSystem {
	Manager mi;

	/**
	 * 无参的构造方法，已创建就得到业务层对象
	 * 
	 * @throws Exception
	 */
	public BankSystem() throws Exception {
		mi = ManagerImp1.getInstance();
	}

	/**
	 * 登录
	 * 
	 * @param fb
	 *            表单bean
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public boolean login(FormBean fb) throws Exception {
		return mi.login(fb.getUsername(), fb.getPassword());
	}

	/**
	 * 注册
	 * 
	 * @param fb
	 *            表单bean
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public boolean register(FormBean fb) throws Exception {
		return mi.register(fb.getUsername(), fb.getPassword());
	}

	/**
	 * 查询
	 * 
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public String inquiry() throws Exception {
		return mi.inquiry();
	}

	/**
	 * 查询交易记录
	 * @param myName 当前账号名称
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public List transactionRecord(String myName) throws Exception {
		return mi.transactionRecord(myName);
	}

	/**
	 * 转账
	 * 
	 * @param fb
	 *            表单bean
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public boolean transfer(FormBean fb) throws Exception {
		// boolean flag = mi.transfer(fb.getToName(),fb.getToMoney());
		// System.out.println("转账系统："+flag);
		// return flag;
		return mi.transfer(fb.getUsername(), fb.getToName(), fb.getToMoney());
	}

	/**
	 * 存款
	 * 
	 * @param fb
	 *            表单bean
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public boolean deposit(FormBean fb) throws Exception {
		return mi.deposit(fb.getToMoney());
	}

	/**
	 * 取款
	 * 
	 * @param fb
	 *            表单bean
	 * @return 返回结果信息
	 * @throws Exception
	 */
	public boolean withDrawals(FormBean fb) throws Exception {
		return mi.withDrawals(fb.getToMoney());
	}

	/**
	 * 退出
	 * 
	 * @throws Exception
	 */
	public void secede() throws Exception {
		mi.secede();
	}
}
