  
  package com.cx.bank.factory;
  import java.util.*;
  import java.io.*;
  import com.cx.bank.dao.*;
  /**
  *持久层的工厂模式
  *@author RG
  *@version 1.6 2018/07/25
  *@see com.cx.bank.dao.FileDaoImp1
  */
  public class UserDaoFactory
  {
	  private BankDaoInterface userDao;//持久层接口
	  private static UserDaoFactory instance = null;//私有静态的工厂类变量，单例第一要素

	  private UserDaoFactory() throws Exception//私有的构造方法，单例第二要素
	  {
		  Properties prop = new Properties();//文件对象
		  FileInputStream fis = new FileInputStream(new File(".\\classInfo.properties"));//文件输入流对象
		  prop.load(fis);//加载外存文件内容到内存的文件对象中
		  fis.close();//关闭文件

		  String className = prop.getProperty("className");//获取文件对象中className的值
		  Class c = Class.forName(className);//通过反射取得相应的反射对象
		  Object o = c.newInstance();//通过反射对象创建相应的类对象
		  userDao = (BankDaoInterface)o;//将取得的类对象造型为持久层接口类型
	  }
	  /**
	  *公有的获得instance属性值的方法，单例第三要素
	  *@exception Exception 异常
	  *@return instance 本类的对象
	  *同步锁，保证安全，因为只加载一次，不影响效率
	  */
	  public static synchronized UserDaoFactory getInstance() throws Exception
	  {
		  if(instance == null)//如果instance值为空，创建UserDaoFactory对象，返回该对象
		  {
			  instance = new UserDaoFactory();
		  }
		  return instance;
	  }

	  /**
	  *返回持久层类对象的方法
	  *@return userDao 持久层类对象
	  */
	  public BankDaoInterface createUserDao()
	  {
		  return userDao;
	  }
  }
