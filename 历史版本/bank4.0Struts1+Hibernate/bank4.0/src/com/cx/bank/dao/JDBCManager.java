  

  package com.cx.bank.dao;
  import java.sql.*;
  import java.util.*;
  import java.io.*;
  import java.net.URLDecoder;

  import com.cx.bank.dao.*;
  /**
  *连接池管理器
  *@author RG
  *@version 2.0 2018/08/19 23:35:56 
  */
  public class JDBCManager 
  {
	  private String poolName;//连接池名
	  private String driverName;//驱动名
	  private String url;//数据源名称
	  private String userName;//数据库用户名
	  private String password;//数据库密码
	  private int maxConn;//最大连接数
	  private Hashtable connPools = new Hashtable();//连接池队列
	  public JDBCManager()//无参的构造方法
	  {
	  }
	  /**
	  *创建连接池
	  *@param name 需要创建的连接池的数据库名字（如：mysql、access）
	  */
	  public void createConnPool(String name)
	  {
		  try
		  {
			  Properties prop = new Properties();
			  String surl = Thread.currentThread().getContextClassLoader().getResource("../").toString().substring(6)+"properties";
			  String purl = URLDecoder.decode(surl,"UTF-8");
			 /*FileInputStream in = new FileInputStream("F:\\bank\\WEB-INF\\classes\\"+name+".properties");//绝对路径
			  //FileInputStream in = new FileInputStream("WEB-INF\\classes\\"+name+".properties");//不可用的方法
			  //InputStream in = this.getClass().getResourceAsStream("/"+name+".properties");//相对路径读取properties文件
			   路径：file:/D:/Tomcat%208.5/webapps/bank/WEB-INF/
			  System.out.println("路径："+Thread.currentThread().getContextClassLoader().getResource("../"));
			  System.out.println("路径2："+JDBCManager.class.getClassLoader().getResource(""));
			  System.out.println("路径3："+JDBCManager.class.getClassLoader().getResource("/"));
			  System.out.println("路径4："+JDBCManager.class.getClassLoader().getResource("./"));
			  System.out.println("路径5："+JDBCManager.class.getClassLoader().getResource("../"));
		     */
			  FileInputStream in = new FileInputStream(purl+"\\"+name+".properties");
			  prop.load(in);
			  in.close();
			  //将properties文件中的配置信息读取到实例全局变量中
			  poolName = prop.getProperty("poolName");
			  driverName = prop.getProperty("driverName");
			  url = prop.getProperty("url");
			  userName = prop.getProperty("userName");
			  password = prop.getProperty("password");
			  maxConn = Integer.parseInt(prop.getProperty("maxConn"));
			  
			  JDBConnPool pool = new JDBConnPool(poolName,driverName,url,userName,password,maxConn);//创建连接池对象
			  connPools.put(poolName,pool);//将创建的连接池对象放入管理器的连接池map集合中
		  }
		  catch (Exception ex)
		  {
			  ex.printStackTrace();
		  }
	  }
	  /**
	  *获取连接
	  *@param name 需要连接的连接池名字
	  *@return 获取的连接
	  */
	  public Connection getConnection(String name)
	  {
		  JDBConnPool pool = (JDBConnPool)connPools.get(name);//从连接池map集合中取得指定的连接池对象
		  if(pool != null)
			  return pool.getConnection();//取得连接池中的连接
		  else
			  return null;
	  }
	  /**
	  *释放连接
	  *@param name 需要释放连接的连接池名字
	  *@param conn 需要释放的连接
	  */
	  public void releaseConnection(String name,Connection conn)
	  {
		  JDBConnPool pool = (JDBConnPool)connPools.get(name);//从连接池map集合中取得指定的连接池对象
		  if(pool != null)
			  pool.releaseConnection(conn);//在相应的连接池中释放相应的连接
	  }
	  /**
	  *关闭所有连接
	  *@param name 需要释放连接的连接池名字
	  *@param conn 需要释放的连接
	  */
	  public synchronized void closeConnection()
	  {
		  Enumeration allpools = connPools.elements();//取得所有的连接池
		  while(allpools.hasMoreElements())//当集合还有下一个元素时循环
		  {
			  JDBConnPool pool = (JDBConnPool)allpools.nextElement();//取得连接池
			  pool.closeConn();//关闭连接池中所有的连接
		  }
	  }
  }
