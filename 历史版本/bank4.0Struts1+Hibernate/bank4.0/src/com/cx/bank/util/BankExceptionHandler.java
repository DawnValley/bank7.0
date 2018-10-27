package com.cx.bank.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * 异常处理类
 * @author RG
 * @version 3.0 2018/09/13
 */
public class BankExceptionHandler extends ExceptionHandler {

	@Override
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
		if(!(ex instanceof BankPublicException)){
			return super.execute(ex, ae, mapping, formInstance, request, response);
		}
		
		BankPublicException bpe = null;
		ActionForward forward = null;
		ActionMessage error = null;
		String property = null;
		
		//取得转向路径
		if(ae.getPath() != null){
			forward=new ActionForward(ae.getPath());
		}else{
			forward=mapping.getInputForward();
		}
		
		//异常处理
		bpe=(BankPublicException)ex;
		String errorKey = bpe.getErrorKey();//拿到国际化资源文件的key
		error = new ActionMessage(errorKey,bpe.getArgs());//异常对象的错误码作为key，args作为动态填充符
		property=error.getKey();
		
		logException(ex);
		
		request.setAttribute(Globals.EXCEPTION_KEY, ex);
		storeException(request,property,error,forward,ae.getScope());
		return forward;
	}

}
