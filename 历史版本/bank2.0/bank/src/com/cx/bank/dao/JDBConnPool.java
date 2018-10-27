  
  
  package com.cx.bank.dao;
  import java.sql.*;
  import java.util.*;
  /**
  *连接池管理器
  *@author RG
  *@version 2.0 2018/08/19 23:35:48 
  */
  public class JDBConnPool 
  {
	  private String poolName;//连接池名
	  private String driverName;//驱动名
	  private String url;//数据源名称
	  private String userName;//数据库用户名
	  private String password;//数据库密码
	  private int maxConn;//最大连接数
	  private Vector emptyConnections = new Vector();//空连接集合
	  private int isUse = 0;//正在使用的连接数
	  //有参构造方法，给属性设值
	  public JDBConnPool(String poolName,String driverName,String url,String userName,String password,int maxConn)
	  {
		  this.poolName = poolName;
		  this.driverName = driverName;
		  this.url = url;
		  this.userName = userName;
		  this.password = password;
		  this.maxConn = maxConn;
	  }
	  /**
	  *获取连接
	  *@return 获取的连接
	  */
	  public synchronized Connection getConnection()
	  {
		  Connection con = null;
		  if(emptyConnections.size() > 0)//如果空连接集合不为空时，直接取得连接
		  {
			  con = (Connection)emptyConnections.firstElement();//取得空连接集合的第0个空连接
			  emptyConnections.remove(0);//将第0个空连接从连接池中移除
			  try
			  {
				  if(con.isClosed())//如果当前空连接已关闭，继续取得连接
					  this.getConnection();
			  }
			  catch (Exception ex)
			  {
				  ex.printStackTrace();
			  }
		  }
		  else if(isUse < maxConn)//否则如果使用的连接数小于最大连接数，创建新连接
		  {
			  con = this.newConnection();
		  }
		  if(con != null)//如果获取的连接不为空，使用的连接数加一
		  {
			  isUse++;
		  }
		  return con;
	  }
	  /**
	  *创建连接
	  *@return 创建的连接
	  */
	  public Connection newConnection()
	  {
		  Connection con = null;
		  try
		  {
			  Class.forName(driverName);//加载驱动
			  con = DriverManager.getConnection(url,userName,password);//建立连接
		  }
		  catch (Exception ex)
		  {
			  ex.printStackTrace();
			  return null;
		  }
		  return con;
	  }
	  /**
	  *释放连接
	  *@param conn 需要释放的连接
	  */
	  public synchronized void releaseConnection(Connection conn)
	  {
		  emptyConnections.addElement(conn);//把释放的连接加入空连接集合
		  isUse--;//使用连接数减一
	  }
	  /**
	  *关闭所有连接
	  */
	  public synchronized void closeConn()
	  {
		  Connection con = null;
		  Enumeration allConns = emptyConnections.elements();//将连接池的所有连接放入集合
		  while(allConns.hasMoreElements())//当集合还有下一个元素时，循环关闭连接
		  {
			  try
			  {
				  con = (Connection)allConns.nextElement();//取得连接
				  con.close();//关闭连接
			  }
			  catch (Exception ex)
			  {
				  ex.printStackTrace();
			  }
		  }
		  emptyConnections.removeAllElements();//清空空连接集合
	  }
  }
