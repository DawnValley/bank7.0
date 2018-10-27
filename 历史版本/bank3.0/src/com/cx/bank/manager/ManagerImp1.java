
package com.cx.bank.manager;

import java.util.*;
import java.io.*;
import com.cx.bank.mode1.*;
import com.cx.bank.util.*;
import com.cx.bank.dao.*;
import com.cx.bank.factory.*;

/**
 * <b>业务层ManagerImp1类</b>
 * 
 * @author RG
 * @version 2.0 2018/08/26
 * @see com.cx.bank.mode1.MoneyBean
 * @see com.cx.bank.factory.UserDaoFactory
 */
public class ManagerImp1 implements Manager {
	private MoneyBean mb = MoneyBean.getMoneyBean();// MoneyBean的单例变量
	private BankDaoInterface userDao = null;// 持久层接口变量
	private UserBean user = new UserBean();// 拿到模型层类UserBean的对象地址
	private static ManagerImp1 instance = null;// 创建业务层对象
	private MD5 md5 = new MD5();// 私有MD5加密类对象属性

	/* 私有构造方法 */
	private ManagerImp1() throws Exception {
		UserDaoFactory udf = UserDaoFactory.getInstance();// 通过单例创建持久层的工厂类对象
		userDao = udf.createUserDao();// 通过工厂类对象拿到持久层类对象
	}

	/**
	 * 拿到对象地址的方法-饱汉模式 同步锁，保证线程安全
	 */
	public static synchronized ManagerImp1 getInstance() throws Exception {
		if (instance == null) {
			instance = new ManagerImp1();
		}
		return instance;
	}

	/**
	 * 实现注册方法
	 * 
	 * @param userName 用户名
	 * @param userPwd 用户密码
	 */
	public boolean register(String userName, String userPwd) throws Exception {
		user.setUsername(userName);// 将用户名设置到用户信息内
		user.setPassword(userPwd);// 将用户密码设置到用户信息内
		if (userDao.register(user) == true)
			return true;
		else
			return false;
	}

	/**
	 * 实现登录方法
	 * 
	 * @param userName
	 *            用户名
	 * @param userPwd
	 *            用户密码
	 */
	public boolean login(String userName, String userPwd) throws Exception {
		user.setUsername(userName);// 将用户名设置到用户信息内
		user.setPassword(userPwd);// 将用户密码设置到用户信息内
		if (userDao.login(user) == true)
			return true;
		else
			return false;
	}
	
	/**
	 * 查询用户信息
	 * @param userName 用户名
	 * @return 用户信息map
	 */
	public Map getAccountInformation(String userName) throws Exception{
		return userDao.getAccountInformation(userName);
	}

	// 查询交易记录
	public PageModel getTransactionRecord(String myName,String queryStr, int pageNo, int pageSize) throws Exception {
		return userDao.getTransactionRecord(myName,queryStr,pageNo,pageSize);
	}

	/**
	 * <b>用户取款</b>
	 * 
	 * @param m
	 *            取款数额
	 */
	public int withDrawals(String name,double m) throws Exception {
		return userDao.withDrawals(name,m);
	}

	/**
	 * <b>用户存款</b>
	 * 
	 * @param m
	 *            存款数额
	 */
	public int deposit(String name,double m) throws Exception {
		return userDao.deposit(name,m);
	}

	/**
	 * 转账方法
	 * 
	 * @param username
	 *            对方用户名
	 * @param money
	 *            转账金额
	 */
	public boolean transfer(String username, String toName, double money) throws Exception {
		return userDao.transfer(username,toName,money);
	}

	/**
	 * <b>退出系统</b>
	 * @Deprecated 不建议使用
	 */
	public void secede() throws Exception {
		//userDao.addBank(user);// 存储用户数据
		System.out.println("系统已退出！");
		//System.exit(0);//会关闭tomcat服务器
	}
}
