
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

	// 查询金额
	public String inquiry() {
		String s = new Double(mb.getMoney()).toString();
		System.out.println("您的账户当前金额为：" + mb.getMoney() + "元");
		return s;
	}

	// 查询交易记录
	public List transactionRecord(String myName) throws Exception {
		return userDao.transactionRecord(myName);
	}

	/**
	 * <b>用户取款</b>
	 * 
	 * @param m
	 *            取款数额
	 */
	public boolean withDrawals(double m) throws Exception {
		if (m > mb.getMoney())// 当取款数额大于余额，取款失败
		{
			throw new AccountOverDrawnException("您的账户余额不足！取款失败，请重新输入！");// 取款数额大于余额异常
			// return false;
		} else if (m < 0)// 当取款数额为负数，取款失败，否则输出取款金额及当前账户金额
		{
			throw new InvalidWithDrawalstException("您输入的取款金额为负数！取款失败，请重新输入！");// 取款数额为负数异常
			// return false;
		} else {
			mb.setMoney(mb.getMoney() - m);// 取款后重新设置金额
			System.out.println("取款成功！共取款：" + m + "元");
			userDao.addBank(user);// 把更改后的余额保存到文件中
			return true;
		}
	}

	/**
	 * <b>用户存款</b>
	 * 
	 * @param m
	 *            存款数额
	 */
	public boolean deposit(double m) throws Exception {
		if (m < 0)// 当存款数额为负数，存款失败，否则输出存款金额及当前账户金额
		{
			throw new InvalidDepositException("您输入的存款金额为负数！存款失败，请重新输入！");
		} else {
			mb.setMoney(mb.getMoney() + m);
			System.out.println("存款成功！共存款：" + m + "元");// 存款后重新设置金额
			userDao.addBank(user);// 把更改后的余额保存到文件中
			return true;
		}
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
		boolean flag = false;
		if (money < 0) {
			System.out.println("转账金额为负数！");
			return false;
		}
		// System.out.println("mb.getMoney()>money:"+mb.getMoney()+"?"+money+":"+(mb.getMoney()>money));
		if (mb.getMoney() > money) {
			flag = userDao.transfer(username, toName, money);
			// System.out.println("业务层"+flag);
		}
		if (flag) {
			mb.setMoney(mb.getMoney() - money);
			// 有错，转账时出现java.io.FileNotFoundException: .\x.proterties
			// (系统找不到指定的文件。)
			// 已解决
			userDao.addBank(user);// 把更改后的余额保存到文件中
		}
		// System.out.println("业务层return"+flag);
		return flag;
	}

	/**
	 * <b>退出系统</b>
	 */
	public void secede() throws Exception {
		userDao.addBank(user);// 存储用户数据
		System.out.println("系统已退出！");
		// System.exit(0);
	}
}
