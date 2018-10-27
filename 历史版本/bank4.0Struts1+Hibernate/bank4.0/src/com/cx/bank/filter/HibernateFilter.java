package com.cx.bank.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate创建session的拦截器
 * 
 * @author RG
 * @version 4.0 2018/09/18
 */
public class HibernateFilter implements Filter {

    private static ThreadLocal hibernateHolder = new ThreadLocal();// 每个线程维护自己专用的变量
    private static SessionFactory sessionFactory;// Hibernate的session工厂

    /**
     * 初始化方法 创建session工厂
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            Configuration cfg = new Configuration().configure();// 读取hibernate.cfg.xml文件
            sessionFactory = cfg.buildSessionFactory();// 创建session工厂
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    /**
     * 拦截器方法
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            /**
             * 请求下传前，一定要执行下面语句 如果存在和当前线程线程绑定的session，就将其从绑定的当前线程中移除
             * 并且如果这个session是开启的就将其关闭。
             */
            Session session = (Session) hibernateHolder.get();
            if (session != null) {
                if (session.isOpen()) {
                    session.close();
                }
                hibernateHolder.remove();
            }
        }

    }

    /**
     * 获取和当前线程绑定的session的方法
     * 
     * @return Session 和当前线程绑定的session
     */
    public static Session getSession() {
        Session session = (Session) hibernateHolder.get();
        if (session == null) {
            session = sessionFactory.openSession();// 创建Session
            hibernateHolder.set(session);// 将session和当前线程绑定
        }
        return session;

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
