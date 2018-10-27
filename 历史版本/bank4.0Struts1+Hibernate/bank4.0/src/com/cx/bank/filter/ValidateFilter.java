  
  
  package com.cx.bank.filter;
  import java.io.*;
  import javax.servlet.*;
  import javax.servlet.http.*;
  /**
  *登录验证拦截器
  *@author RG
  *@version 1.0 2018/08/19 20:32:13  
  */
  public class ValidateFilter implements Filter
  {
	  public ValidateFilter() //无参的构造方法
	  {}
	  /**
	  *初始化方法
	  *@param config   注入的自动收集Filter组件的配置信息初始化参数config对象
	  */
	  public void init(FilterConfig filterConfig)throws ServletException
	  {
		  //System.out.println("银行项目拦截器已启动");
	  }
	  /**
	  *对请求进行拦截的方法
	  *@param req   注入的request对象
	  *@param resp  注入的response对象
	  *@param chain  注入的FilterChain对象
	  */
	  public void doFilter(ServletRequest req,ServletResponse resp,FilterChain chain)throws IOException,ServletException
	  {
		  //System.out.println("进入拦截器");
		  //给request和response造型
		  HttpServletRequest request = (HttpServletRequest)req;
		  HttpServletResponse response = (HttpServletResponse)resp;
		  String currentURL = request.getServletPath();//取得除去服务器路径的请求的路径
		  String targetURL = currentURL.substring(currentURL.indexOf("/",0)+1);//得到页面名称
		  //System.out.println("currentURL: "+currentURL);
		  //System.out.println("targetURL: "+targetURL);
		  HttpSession session = request.getSession(false);//取得请求页面的session对象
		  //如果不是bank、login、error、logout页面，进行session判断
		  if(!"index.jsp".equals(targetURL)&&!"login.jsp".equals(targetURL)&&!"registerSuccess.jsp".equals(targetURL)&&!"error.jsp".equals(targetURL))
		  {
			  //如果session不存在或者验证信息不存在，跳转到登录页面
			  if(session==null||session.getAttribute("username")==null)
			  {
				  response.sendRedirect("login.jsp");
			  }
			  else
				  chain.doFilter(request,response);//遗漏了会出现空白页面
		  }
		  else
		  {
			  chain.doFilter(request,response);//请求下传
		  }
	  }
	  /**
	  *拦截销毁调用的用来处理善后工作的方法
	  */
	  public void destroy()
	  {}
  }
