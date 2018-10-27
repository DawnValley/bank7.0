
package com.cx.bank.dao;

import com.cx.bank.model.UserBean;

/**
 * 定义dao层接口
 * 
 * @author RaoGang
 * @version 4.0 2018/09/19
 */
public interface BankDaoInterface {
    boolean register(UserBean ub) throws Exception;// 注册方法

    boolean login(UserBean ub) throws Exception;// 登录方法
}