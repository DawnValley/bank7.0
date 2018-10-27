
package com.cx.bank.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.cx.bank.manager.Manager;
import com.cx.bank.manager.ManagerImp1;
import com.cx.bank.mode1.*;
import com.cx.bank.util.*;

/**
 * 存取款的Servlet类
 * 
 * @author RG
 * @version 2.0 2018/08/20 19:50:42
 */
public class AccessServlet extends HttpServlet {
	public AccessServlet()// 无参的构造方法
	{
	}

	/**
	 * 无参的初始化方法，只用作在服务器启动时显示
	 */
	public void init() throws ServletException {
		// System.out.println("银行项目存款取款servlet已启动");
	}

	/**
	 * get请求处理
	 * 
	 * @param req
	 *            注入的request对象
	 * @param resp
	 *            注入的response对象
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	/**
	 * post请求处理
	 * 
	 * @param req
	 *            注入的request对象
	 * @param resp
	 *            注入的response对象
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getHeader("Referer");
		int first = url.lastIndexOf("/");
		int last = url.lastIndexOf(".jsp");
		String toUrl = url.substring(first + 1, last);
		
		FormBean fb = new FormBean();
		Manager mi = null;
		int flag = 0;
		String username = (String)req.getSession().getAttribute("username");
		fb.setToMoney(req.getParameter("toMoney"));
		/*System.out.println("AccessServlet username="+username);
		System.out.println("AccessServlet toMoney="+fb.getToMoney());*/
		if (toUrl.equals("deposit")) {
			try {
				mi=ManagerImp1.getInstance();
				
				flag = mi.deposit(username,fb.getToMoney());
				
				
				if (flag == 0) {
					req.getRequestDispatcher("operationError.jsp").forward(req, resp);
				} else {
					req.setAttribute("mess", "存款成功！共存款：" + fb.getToMoney() + "元！");
					req.setAttribute("message", "存款");
					req.getRequestDispatcher("/inquiryServlet").forward(req, resp);
				}
			} catch (Exception ex) {
//				System.out.println(ex);
				ex.printStackTrace();
				try {
					req.getRequestDispatcher("operationError.jsp").forward(req, resp);
				} catch (Exception e) {
				}

			}
		} else {
			try {
				mi=ManagerImp1.getInstance();
				
				flag = mi.withDrawals(username,fb.getToMoney());
				
				if (flag == 0) {
					req.getRequestDispatcher("operationError.jsp").forward(req, resp);
				} else {
					req.setAttribute("mess", "取款成功！共取款：" + fb.getToMoney() + "元！");
					req.setAttribute("message", "取款");
					req.getRequestDispatcher("/inquiryServlet").forward(req, resp);
					// resp.sendRedirect("transferSuccess.jsp");
				}

			} catch (AccountOverDrawnException aode) {
				System.out.println(aode);
				try {
					req.setAttribute("errorMessage", aode.getMessage());
					req.getRequestDispatcher("operationError.jsp").forward(req, resp);
				} catch (Exception e) {
				}
			} catch (Exception ex) {
				//System.out.println(ex);
				ex.printStackTrace();
				try {
					req.getRequestDispatcher("operationError.jsp").forward(req, resp);
				} catch (Exception e) {
				}
			}
		}

	}
}
