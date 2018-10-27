package com.cx.bank.model;

import java.sql.Timestamp;

/**
 * 交易记录的数据bean
 * 
 * @author RaoGang
 * @version 2.0 2018/09/08
 */
public class TransactionRecordBean {

    private Integer bId;// 交易id
    private UserBean users;// 关联的用户
    private String toName;// 对方用户名
    private Double bMoney;// 交易金额
    private String transactionType;// 交易类型
    private Timestamp bDate;// 交易时间

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }

    public UserBean getUsers() {
        return users;
    }

    public void setUsers(UserBean users) {
        this.users = users;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public Double getbMoney() {
        return bMoney;
    }

    public void setbMoney(Double bMoney) {
        this.bMoney = bMoney;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Timestamp getbDate() {
        return bDate;
    }

    public void setbDate(Timestamp bDate) {
        this.bDate = bDate;
    }

}
