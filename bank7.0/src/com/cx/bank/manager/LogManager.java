package com.cx.bank.manager;

import com.cx.bank.model.LogBean;

/**
 * 日志管理接口
 * 
 * @author RaoGang
 * @version 5.0 2018/10/01
 */
public interface LogManager {
    /**
     * 存储日志信息
     * 
     * @param log
     *            日志信息
     */
    void storageLog(LogBean log) throws Exception;

}