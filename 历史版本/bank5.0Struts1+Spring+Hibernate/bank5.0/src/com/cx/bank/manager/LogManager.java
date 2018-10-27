package com.cx.bank.manager;

import com.cx.bank.mode1.LogBean;
/**
 * 日志管理接口
 * @author RG
 * @version 5.0 2018/10/01
 */
public interface LogManager {

    void storageLog(LogBean log) throws Exception;

}