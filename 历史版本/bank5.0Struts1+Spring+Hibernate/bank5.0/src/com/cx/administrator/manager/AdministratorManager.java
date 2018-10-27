package com.cx.administrator.manager;

import com.cx.bank.util.PageModel;

/**
 * 管理员业务层接口
 * 
 * @author RG
 * @version 2018/09/25
 */
public interface AdministratorManager {
    public int getTotalRecords(String queryStr, String identity) throws Exception;

    public PageModel getAllUserInformation(String administratorName, final String queryStr, String identity,
            final int pageNo, final int pageSize) throws Exception;

    public void stateOperation(String administratorName, int id, String operation) throws Exception;

    public int getTotalLogRecords(String queryStr, String type) throws Exception;

    public PageModel getAllLogInformation(String administratorName, final String queryStr, String type,
            final int pageNo, final int pageSize) throws Exception;
}
