  
  
  package com.cx.bank.servlet;
  import java.io.*;
  import javax.servlet.*;
  import javax.servlet.http.*;

import com.cx.bank.manager.Manager;
import com.cx.bank.manager.ManagerImp1;
import com.cx.bank.mode1.*;
  /**
  *转账的Servlet类
  *@author RG
  *@version 2.0 2018/08/20 17:17:55  
  */
  public class TransferServlet extends HttpServlet 
  {
	  public TransferServlet()//无参的构造方法
	  {
	  }
	  /**
	  *无参的初始化方法，只用作在服务器启动时显示
	  */
	  public void init()throws ServletException
	  {
		  //System.out.println("银行项目转账servlet已启动");
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
	  {//System.out.println("上一个页面："+req.getHeader("Referer"));
		  
		  FormBean fb = new FormBean();
		  Manager mi = null;
		  boolean flag = false;
		  fb.setUsername((String)req.getSession().getAttribute("username"));
		  fb.setToName(req.getParameter("toName"));
		  fb.setToMoney(req.getParameter("toMoney"));
		  try{
			  mi = ManagerImp1.getInstance();
			  flag=mi.transfer(fb.getUsername(),fb.getToName(),fb.getToMoney());
		      //System.out.println("转账"+flag);
			  req.setAttribute("message","转账");
			  req.setAttribute("mess","转账成功！共转款："+fb.getToMoney()+"元！");
			  if(flag == false)
			  {
				  req.getRequestDispatcher("operationError.jsp").forward(req,resp);
			  }
			  else
			  {
				  //resp.sendRedirect("operationSuccess.jsp");
				  req.getRequestDispatcher("/inquiryServlet").forward(req,resp);
			  }
			      
		  }
		  catch(Exception ex){
	          ex.printStackTrace();
			  try
			  {
				  req.getRequestDispatcher("operationError.jsp").forward(req,resp);
			  }
			  catch (Exception e)
			  {
			  }
			  
		  }
	  }
  }
