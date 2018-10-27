  
  
  package com.cx.bank.servlet;
  import java.io.*;
  import javax.servlet.*;
  import javax.servlet.http.*;
  import com.cx.bank.mode1.*;
  import com.cx.bank.test.*;
  /**
  *登录的Servlet类
  *@author RG
  *@version 1.0 2018/08/19 17:22:37 
  */
  public class LoginServlet extends HttpServlet
  {
	  public LoginServlet()//无参的构造方法
	  {
	  }
	  /**
	  *无参的初始化方法，只用作在服务器启动时显示
	  */
	  public void init()throws ServletException
	  {
		  //System.out.println("银行项目servlet已启动");
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
		  FormBean fb = new FormBean();
		  BankSystem bs = null;
		  boolean flag = false;
		  HttpSession session = req.getSession();
		  fb.setUsername(req.getParameter("username"));
		  fb.setPassword(req.getParameter("password"));
		  try{
			  bs = new BankSystem();
			  flag = bs.login(fb);//登录
		  
			  if(flag == false)
			  {
				  //resp.sendRedirect("error.jsp");
				  //req.getSession().setAttribute("message","登录");
				  req.setAttribute("message","登录");
				  req.getRequestDispatcher("error.jsp").forward(req,resp);
			  }
			  else
			  {
				  if(session.getAttribute("username")==null)
				  {
					    //session.setAttribute("flag","OK");
						session.setAttribute("username",fb.getUsername());
				  }
				  //req.getRequestDispatcher("system.jsp").forward(req,resp);
				  resp.sendRedirect("system.jsp");
			  }
		  }
		  catch(Exception ex){
	          ex.printStackTrace();
			  try
			  {
				  //req.getSession().setAttribute("message","登录");
				  //resp.sendRedirect("error.jsp");
				  req.setAttribute("message","登录");
				  req.getRequestDispatcher("error.jsp").forward(req,resp);
			  }
			  catch (Exception e)
			  {
			  }
			  
		  }
	  }
  }
