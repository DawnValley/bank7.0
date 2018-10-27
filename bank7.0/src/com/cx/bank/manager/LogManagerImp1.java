package com.cx.bank.manager;

import javax.annotation.Resource;

import com.cx.bank.dao.OperationMapper;
import com.cx.bank.model.LogBean;

/**
 * 日志管理实现类
 * 
 * @author RaoGang
 * @version 5.0 2018/10/01
 */
public class LogManagerImp1 implements LogManager {
    // mybatis的持久层对象，通过注解注入
    @Resource(name = "mapper")
    private OperationMapper mapper;

    /**
     * 存储日志信息
     * 
     * @param log
     *            日志信息
     */
    public void storageLog(LogBean log) throws Exception {
        mapper.addLog(log);
    }
}
