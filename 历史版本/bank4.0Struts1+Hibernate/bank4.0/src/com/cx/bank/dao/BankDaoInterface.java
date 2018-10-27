
package com.cx.bank.dao;

import com.cx.bank.mode1.ValueObjectBean;

/**
 * 定义dao层接口
 * 
 * @author RG
 * @version 4.0 2018/09/19
 */
public interface BankDaoInterface {
    boolean register(ValueObjectBean lb) throws Exception;// 注册方法

    boolean login(ValueObjectBean lb) throws Exception;// 登录方法
}