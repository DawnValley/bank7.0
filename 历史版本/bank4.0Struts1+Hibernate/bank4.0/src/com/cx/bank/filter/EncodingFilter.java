  
  package com.cx.bank.filter;
  import java.io.*;
  import javax.servlet.*;
  import javax.servlet.http.*;
  /**
  *编码拦截器
  *@author RG
  *@version 1.0 2018/08/19 21:59:32 
  */
  public class EncodingFilter implements Filter
  {
	  String encoding;
	  FilterConfig config;
	  public EncodingFilter() //无参的构造方法
	  {}
	  /**
	  *初始化方法
	  *@param config   注入的自动收集Filter组件的配置信息初始化参数config对象
	  */
	  public void init(FilterConfig config)throws ServletException
	  {
		  this.config = config;
		  encoding = config.getInitParameter("charset");
		  //System.out.println("init:encoding="+encoding);
	  }
	  /**
	  *对请求进行拦截的方法
	  *@param req   注入的request对象
	  *@param resp  注入的response对象
	  *@param chain  注入的FilterChain对象
	  */
	  public void doFilter(ServletRequest req,ServletResponse resp,FilterChain chain)throws IOException,ServletException
	  {//System.out.println("doFilter");
		  HttpServletRequest request = (HttpServletRequest)req;//将注入的request对象造型为HttpServletRequest类型
		  HttpServletResponse response = (HttpServletResponse)resp;//将注入的Response对象造型为HttpServletResponse类型
		  request.setCharacterEncoding(encoding);//将请求的汉字设为指定编码
		  response.setContentType("text/html;charset="+encoding);//将返回的结果的汉字设为指定编码
		  chain.doFilter(request,response);//请求下传
	  }
	  /**
	  *拦截销毁调用的用来处理善后工作的方法
	  */
	  public void destroy()
	  {}
  }
