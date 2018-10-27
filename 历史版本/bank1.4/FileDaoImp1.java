  
  
  package com.cx.bank.dao;
  import java.util.*;
  import java.io.*;
  import com.cx.bank.util.*;
  import com.cx.bank.mode1.*;

  /**
  *<b>定义FileDanImpl</b>
  *@author RG
  *@version 1.3 2018/07/15
  *@see com.cx.bank.util.MD5
  *@see com.cx.bank.mode1.UserBean
  *@see com.cx.bank.mode1.MoneyBean
  */
  public class FileDaoImp1 implements BankDaoInterface
  {

	  private MD5 md5 = new MD5();//私有加密类对象属性
	  private Properties prop = new Properties();//私有文件类对象属性
	  public FileDaoImp1()
	  {
	  }
	  /*private static FileDaoImp1 instance = new FileDaoImp1();//持久层没必要单例，业务层单例就行

	  private FileDaoImp1()
	  {
	  }
	  public static FileDaoImp1 getInstance()
	  {
		  return instance;
	  }*/

	  /**
	  *注册
	  *@param user 用户信息
	  */
	  public boolean register(UserBean user) throws FileNotFoundException,IOException,Exception
	  {
		  String name = user.getUserName();//拿到模型层用户名
		  String pwd = user.getPwd();//拿到模型层用户密码
		  String password = md5.getMD5(pwd);//进行MD5加密
		  File f = new File(name+".properties");//创建文件
		  if(f.exists())
		  {
			  System.out.println("用户已存在，请重新注册！");
			  return false;
		  }
		  else
		  {
			  if("".equals(name)||"".equals(pwd))
			  {
				  System.out.println("用户名和密码不能为空，请重新注册！");
				  return false;
			  }
		  }
			  FileInputStream in = new FileInputStream(".\\Bank.properties");
			  prop.load(in);//把外存文件加载到内存
			  in.close();

			  FileOutputStream out = new FileOutputStream(f);//内存与外存的通道
			  prop.setProperty("userName",name);//设置姓名
			  prop.setProperty("password",password);//设置密码
			  prop.store(out,".\\"+name+".properties");//将内存文件存储到外存
			  //prop.store(out,name+".properties");//与上效果相同
			  out.close();

		      //this.addBank(user);
			  //尝试解决：转账：java.io.FileNotFoundException: .\*.proterties (系统找不到指定的文件。)
			  //最终发现 FileInputStream in = new FileInputStream(".\\"+name+".properties");写错了
			  //写成了".proterties"

		      return true;
	  }

      /**
	  *登录
	  *@param user 用户信息
	  */
	  public boolean login(UserBean user) throws Exception
	  {
		  String name = user.getUserName();
		  String pwd = user.getPwd();
		  String password = md5.getMD5(pwd);
		  File f = new File(name+".properties");
		  if(f.exists() == true)
		  {
			  FileInputStream in = new FileInputStream(".\\"+name+".properties");
			  prop.load(in);
			  in.close();
			  if(prop.getProperty("password").equals(password))
			  {
				  Double money = Double.parseDouble(prop.getProperty("money"));
				  MoneyBean.getMoneyBean().setMoney(money);
				  return true;
			  }
			  else
			  {
				  System.out.println("密码错误，请重新登录！");
				  return false;
			  }
		  }
		  else
		  {
			  System.out.println("用户不存在，请重新登录！");
			  return false;
		  }
	  }

	  /**
	  *转账
	  *@param name 要转入的账号用户名
	  *@param money 要转入的钱
	  */
	  public boolean transfer(String name,double money) throws Exception
	  {
		  File f = new File(name+".properties");
		  if(f.exists()==true)//先要判断要转入的账号是否存在
		  {
			  FileInputStream in = new FileInputStream(".\\"+name+".properties");
			  prop.load(in);
			  in.close();

			  FileOutputStream out = new FileOutputStream(f);
			  String proMoney = Double.parseDouble((prop.getProperty("money")))+money+"";
			  prop.setProperty("money",proMoney);
			  prop.store(out,".\\"+name+".properties");
			  out.close();
		      return true;
		  }
		  else
		  {
			  System.out.println("该用户不存在！");
		      return false;
		  }
	  }

	  /**
	  *存储方法
	  *@param user 用户信息
	  */
	  public boolean addBank(UserBean user) throws Exception
	  {
		  String name = user.getUserName();
          File f = new File(name+".properties");//创建文件
		  if(f.exists())
		  {
			  FileInputStream in = new FileInputStream(".\\"+name+".properties");
			  in.close();

			  FileOutputStream out = new FileOutputStream(f);
			  prop.setProperty("money",MoneyBean.getMoneyBean().getMoney()+"");
			  prop.store(out,".\\"+name+".properties");
			  out.close();
			  
			  return true;
		  }
		  else
		  {
			  System.out.println("尚未注册或登录，用户不存在！");
			  return false;
		  }
	  }

	  /**
	  *获取余额
	  *@param user 用户信息
	  *@return money 金额或-1
	  */
	  public double getBalance(UserBean user) throws Exception
	  {
		  String name = user.getUserName();
		  File f = new File(name+".properties");//创建文件
		  if(f.exists())
		  {
			  FileInputStream in = new FileInputStream(f);
			  prop.load(in);
			  in.close();

			  double money = Double.parseDouble(prop.getProperty("money"));
			  return money;
		  }
		  else
		  {
		      System.out.println("用户不存在！");
			  return -1;//文件不存在返回-1
		  }
	  }
  }
