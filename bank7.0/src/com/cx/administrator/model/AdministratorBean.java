package com.cx.administrator.model;

import java.util.Date;
/**
 * 管理员表单数据bean
 * @author RaoGang
 * @version 6.0 2018/10/03
 */
public class AdministratorBean {
    private String queryStr;//查询参数
    private String identity;//查询备用参数
    private String type;//日志类型
    private String operation;//操作信息
    private int pageNo;//当前页码
    private int pageSize;//页面尺寸
    private int id;//用户id
    private String username;//用户名
    private String password;//用户密码
    private double money;//用户余额
    private String condition;//用户账户状态
    private Date udate;//时间
    public String getQueryStr() {
        return queryStr;
    }
    public String getIdentity() {
        return identity;
    }
    public int getPageNo() {
        return pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public double getMoney() {
        return money;
    }
    public String getCondition() {
        return condition;
    }
    public Date getUdate() {
        return udate;
    }
    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setUdate(Date udate) {
        this.udate = udate;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
