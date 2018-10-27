package com.cx.bank.manager;

import com.cx.bank.model.LogBean;
/**
 * 日志管理接口
 * @author RG
 * @version 5.0 2018/10/01
 */
public interface LogManager {

    void storageLog(LogBean log) throws Exception;

}