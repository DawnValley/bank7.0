  
  
  package com.cx.bank.servlet;
  import java.io.*;
  import javax.servlet.*;
  import javax.servlet.http.*;

import com.cx.bank.manager.Manager;
import com.cx.bank.manager.ManagerImp1;
import com.cx.bank.mode1.*;
  /**
  *退出的Servlet类
  *@author RG
  *@version 2.0 2018/08/20 20:48:18 
  */
  public class SecedeServlet extends HttpServlet 
  {
	  public SecedeServlet()//无参的构造方法
	  {
	  }
	  /**
	  *无参的初始化方法，只用作在服务器启动时显示
	  */
	  public void init()throws ServletException
	  {
		  //System.out.println("银行项目退出servlet已启动");
	  }
	  /**
	  *get请求处理
	  *@param req   注入的request对象
	  *@param resp  注入的response对象
	  */
	  public void doGet(HttpServletRequest req,HttpServletResponse resp)
	  {
		  doPost(req,resp);
	  }
	  /**
	  *post请求处理
	  *@param req   注入的request对象
	  *@param resp  注入的response对象
	  */
	  public void doPost(HttpServletRequest req,HttpServletResponse resp)
	  {
		  Manager mi = null;
		  try{
			  //mi = ManagerImp1.getInstance();
			  //mi.secede();
			  resp.sendRedirect("logout.jsp");
		  }
		  catch(Exception ex){
	          ex.printStackTrace();
			  try
			  {
				  req.setAttribute("message","退出");
				  req.getRequestDispatcher("operationError.jsp").forward(req,resp);
			  }
			  catch (Exception e)
			  {
			  }
			  
		  }
	  }
  }
