package com.cx.bank.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 身份验证拦截器
 * @author RG
 * @version 6.0 2018/10/06
 */
public class IdentityValidateFilter extends HandlerInterceptorAdapter {

    /*private List<String> exceptUrls;
    public List<String> getExceptUrls() {
        return exceptUrls;
    }
    public void setExceptUrls(List<String> exceptUrls) {
        this.exceptUrls = exceptUrls;
    }*/
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /*System.out.println("----------进入拦截器----------");
        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        System.out.println("uri："+uri);
        System.out.println("url："+url);
        System.out.println("request.getContextPath()："+request.getContextPath());   */
        
        
        String userName = null;
        userName = (String)request.getSession().getAttribute("userName");
        
        if(userName==null){
            //System.out.println("请求被拦截！！！");
            /*response.sendRedirect(request.getContextPath()+"/RegisterLoginController.do?method=loginPage");*/
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return false;
        }else{
            //System.out.println("请求没有被拦截！");
            return true;
        }
        
    }
    

}
