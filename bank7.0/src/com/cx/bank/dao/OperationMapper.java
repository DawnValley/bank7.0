package com.cx.bank.dao;

import java.util.List;

import com.cx.bank.model.LogBean;
import com.cx.bank.model.QueryObject;
import com.cx.bank.model.TransactionRecordBean;
import com.cx.bank.model.UserBean;

/**
 * mybatis的持久层接口
 * 
 * @author RaoGang
 * @version 7.0 2018/10/08
 */
public interface OperationMapper {

    /**
     * 添加用户
     * 
     * @param user
     * @throws Exception
     */
    public void addUser(UserBean user) throws Exception;

    /**
     * 更新用户数据
     * 
     * @param user
     * @throws Exception
     */
    public void updateUser(UserBean user) throws Exception;

    /**
     * 根据用户名取得用户信息
     * 
     * @param username
     * @return
     * @throws Exception
     */
    public UserBean selectUserByName(String username) throws Exception;

    /**
     * 根据用户id取得用户信息
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public UserBean selectUserById(int id) throws Exception;

    /**
     * 查询指定范围的用户记录数
     * 
     * @param qo
     * @return
     * @throws Exception
     */
    public long selectUserTotalRecords(QueryObject qo) throws Exception;

    /**
     * 查询指定范围的用户信息
     * 
     * @param qo
     * @return
     * @throws Exception
     */
    public List<UserBean> selectAllUserInformation(QueryObject qo) throws Exception;

    /**
     * 添加日志
     * 
     * @param log
     * @throws Exception
     */
    public void addLog(LogBean log) throws Exception;

    /**
     * 查询指定范围的日志记录数
     * 
     * @param qo
     * @return
     * @throws Exception
     */
    public long selectTotalLogRecords(QueryObject qo) throws Exception;

    /**
     * 查询指定范围的日志记录
     * 
     * @param qo
     * @return
     * @throws Exception
     */
    public List<LogBean> selectAllLogInformation(QueryObject qo) throws Exception;

    /**
     * 添加交易记录
     * 
     * @param trb
     * @throws Exception
     */
    public void addTransactionRecords(TransactionRecordBean trb) throws Exception;

    /**
     * 查询指定范围的交易记录数
     * 
     * @param qo
     * @return
     * @throws Exception
     */
    public long selectTotalRecords(QueryObject qo) throws Exception;

    /**
     * 查询指定范围的交易记录
     * 
     * @param qo
     * @return
     * @throws Exception
     */
    public List<TransactionRecordBean> selectAllTransactionRecord(QueryObject qo) throws Exception;
}
