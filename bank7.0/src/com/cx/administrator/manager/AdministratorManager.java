package com.cx.administrator.manager;

import com.cx.bank.util.PageModel;

/**
 * 管理员业务层接口
 * 
 * @author RaoGang
 * @version 6.0 2018/09/25
 */
public interface AdministratorManager {
    // 查询记录数
    public int getUserTotalRecords(String queryStr, String identity) throws Exception;

    // 查询所有用户信息
    public PageModel getAllUserInformation(String administratorName, String queryStr, String identity, int pageNo,
            int pageSize) throws Exception;

    // 账户激活解冻
    public void stateOperation(String administratorName, int id, String operation) throws Exception;

    // 查询日志总记录数
    public int getTotalLogRecords(String queryStr, String type) throws Exception;

    // 查询日志信息
    public PageModel getAllLogInformation(String administratorName, String queryStr, String type, int pageNo,
            int pageSize) throws Exception;
}
