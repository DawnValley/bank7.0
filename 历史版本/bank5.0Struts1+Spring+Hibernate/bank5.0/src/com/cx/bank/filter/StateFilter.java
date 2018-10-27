package com.cx.bank.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cx.bank.manager.Manager;
/**
 * 用户状态拦截器
 * 由于设置的状态信息是静态的，影响其他用户的使用，
 * 实际并未使用该拦截器获取用户状态信息
 * @author RG
 * @version 5.0 2018/10/01
 */
public class StateFilter implements Filter {
    
    //private static String state;//静态全局变量，存储用户状态信息
    private Manager manager;
    

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
    /**
     *取得用户状态
     *@param req   注入的request对象
     *@param resp  注入的response对象
     *@param chain  注入的FilterChain对象
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;

        String userName = (String)request.getSession().getAttribute("userName");//取得用户名
        try {
            //通过用户名取得该用户状态信息，并设在静态全局变量中
            String state = manager.getAccountInformation(userName).getConditions();
            request.getSession().setAttribute("state", state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    /*public static String getSate(){
        return state;
    }*/
    
    public void setManager(Manager manager){
        this.manager = manager;
    }

}
