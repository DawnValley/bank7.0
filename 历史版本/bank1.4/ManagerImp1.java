  

  package com.cx.bank.manager;
  import java.util.*;
  import java.io.*;
  import com.cx.bank.mode1.*;
  import com.cx.bank.util.*;
  import com.cx.bank.dao.*;
  import com.cx.bank.factory.*;
  /**
  *<b>业务层ManagerImp1类</b>
  *@author RG
  *@version 1.3 2018/07/15
  *@see com.cx.bank.mode1.MoneyBean
  *@see com.cx.bank.factory.UserDaoFactory
  */
  public class  ManagerImp1 implements Manager
  {
	  private MoneyBean mb = MoneyBean.getMoneyBean();//MoneyBean的单例变量
	  private BankDaoInterface userDao = null;//持久层接口变量
	  private UserBean user= new UserBean();//拿到模型层类UserBean的对象地址
	  private static ManagerImp1 instance =null;//创建业务层对象
	  private MD5 md5=new MD5();//私有MD5加密类对象属性

	  /*私有构造方法*/
	  private ManagerImp1() throws Exception
	  {
		  UserDaoFactory udf = UserDaoFactory.getInstance();//通过单例创建持久层的工厂类对象
		  userDao = udf.createUserDao();//通过工厂类对象拿到持久层类对象
	  }

      /*拿到对象地址的方法-饱汉模式*/
	  public static synchronized ManagerImp1 getInstance() throws Exception
	  {
		  if (instance == null)
		  {
			  instance = new ManagerImp1();
		  }
		  return instance;
	  }

	  /**
	  *实现注册方法
	  *@param userName 用户名
	  *@param userPwd  用户密码
	  */
	  public boolean register(String userName,String userPwd) throws Exception
	  {   
		  user.setUserName(userName);//将用户名设置到用户信息内
		  user.setPwd(userPwd);//将用户密码设置到用户信息内
	      if(userDao.register(user)==true)
		      return true;
		  else
		      return false;  
	  }

      /**
	  *实现登录方法
	  *@param userName 用户名
	  *@param userPwd  用户密码
	  */
	  public boolean login(String userName,String userPwd) throws Exception
	  {
		  user.setUserName(userName);//将用户名设置到用户信息内
		  user.setPwd(userPwd);//将用户密码设置到用户信息内
          if(userDao.login(user)==true)
		      return true;
		  else
		      return false;
	  }

	  /**
	  *转账方法
	  *@param username 用户名
	  *@param money  用户密码
	  */
	  public boolean transfer(String username,double money) throws Exception
	  {
		  if(money < 0)
		  {
			  System.out.println("转账金额为负数！");
			  return false;
		  }
		  else if(userDao.transfer(username,money)==true && mb.getMoney()>=money)
		  {
			  mb.setMoney(mb.getMoney()-money);
			  //有错，转账时出现java.io.FileNotFoundException: .\x.proterties (系统找不到指定的文件。)
			  //已解决
			  userDao.addBank(user);//把更改后的余额保存到文件中
		      return true;
		  }
		  else
			  return false;
	  }
	  
	  public void inquiry()//查询金额
	  {
		  System.out.println("您的账户当前金额为："+mb.getMoney()+"元");
	  }
	  /**
	  *<b>用户取款</b>
	  *@param m 取款数额
	  */
	  public void withDrawals(double m) throws RuntimeException
	  {
		  if(m > mb.getMoney())//当取款数额大于余额，取款失败
		  {
			  throw new AccountOverDrawnException("您的账户余额不足！取款失败，请重新输入！");//取款数额大于余额异常
		  }
		  else if(m < 0)//当取款数额为负数，取款失败，否则输出取款金额及当前账户金额
		  {
			  throw new InvalidWithDrawalstException("您输入的取款金额为负数！取款失败，请重新输入！");//取款数额为负数异常
		  }
		  else
		  {
			  mb.setMoney(mb.getMoney() - m);//取款后重新设置金额
			  System.out.println("取款成功！共取款："+m+"元");
		  }
	  }
	  /**
	  *<b>用户存款</b>
	  *@param m 存款数额
	  */
	  public void deposit(double m) throws RuntimeException
	  {
		  if(m < 0)//当存款数额为负数，存款失败，否则输出存款金额及当前账户金额
		  {
			  throw new InvalidDepositException("您输入的存款金额为负数！存款失败，请重新输入！");  
		  }
		  else
		  {
			  mb.setMoney(mb.getMoney() + m);
			  System.out.println("存款成功！共存款："+m+"元");//存款后重新设置金额
		  }	  
	  }
	  /**
	  *<b>退出系统</b>
	  */
	  public void secede() throws Exception
	  {
		  userDao.addBank(user);//存储用户数据
		  System.out.println("系统已退出！");
		  System.exit(0);
	  }
  }
