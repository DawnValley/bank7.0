package com.cx.bank.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 身份验证拦截器
 * @author RaoGang
 * @version 6.0 2018/10/06
 */
public class IdentityValidateFilter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        String userName = null;
        userName = (String)request.getSession().getAttribute("userName");
        //如果用户没有登录，跳转到首页
        if(userName==null){
            //response.sendRedirect(request.getContextPath()+"/index.jsp");//出现页面重复
            //后台跳出框架，避免页面重复
            response.getWriter().print("<script>parent.window.location.href='"+request.getContextPath()+"/index.jsp"+"'</script>");
            return false;
        }else{
            return true;
        }
    }
}
