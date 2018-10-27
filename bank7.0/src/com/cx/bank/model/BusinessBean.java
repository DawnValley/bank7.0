package com.cx.bank.model;

/**
 * 业务的表单bean，用来封装业务操作的数据
 * 
 * @author RaoGang
 * @version 6.0 2018/10/03
 */
public class BusinessBean {

    private String username;// 当前用户名
    private String toName;// 对方用户名
    private double toMoney;// 金额
    private String queryStr;// 查询信息
    private int pageNo;// 当前页码
    private int pageSize;// 页面尺寸

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
