package com.cx.bank.model;

/**
 * 业务的表单bean
 * @author RG
 * @version 6.0 2018/10/03
 */
public class BusinessBean {

    private String username;
    private String toName;
    private double toMoney;
    private String queryStr;
    private int pageNo;
    private int pageSize;
    
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getQueryStr() {
        return queryStr;
    }
    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }
    public String getToName() {
        return toName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }
    public double getToMoney() {
        return toMoney;
    }
    public void setToMoney(double toMoney) {
        this.toMoney = toMoney;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
}
