package com.cx.bank.servlet;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cx.bank.manager.Manager;
import com.cx.bank.manager.ManagerImp1;
import com.cx.bank.util.PageModel;

/**
 * 查询交易记录的Servlet
 * 
 * @author RG
 * @version 2.0 2018/08/26
 */
public class TransactionRecordServlet extends HttpServlet {
	public TransactionRecordServlet()// 无参的构造方法
	{
	}

	/**
	 * 无参的初始化方法，只用作在服务器启动时显示
	 */
	public void init() throws ServletException {
		// System.out.println("银行项目查询交易记录servlet已启动");
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
		Manager mi = null;
		try {
			mi = ManagerImp1.getInstance();
			/*PageModel pm = mi.getTransactionRecord((String) req.getSession().getAttribute("username"));*/
			/*req.setAttribute("pm", pm);*/
			req.getRequestDispatcher("transactionRecord.jsp").forward(req, resp);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				req.setAttribute("message", "查询交易记录");
				req.getRequestDispatcher("operationError.jsp").forward(req, resp);
			} catch (Exception e) {
			}

		}
	}
}
