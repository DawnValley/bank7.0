package com.cx.bank.mode1;

/**
 * 值对象，用于将收集的数据传递到业务层
 * @author RG
 * @version 4.0 2018/09/23
 */
public class ValueObjectBean {
    private String username;
    private String password;
    private String toName;
    private double toMoney;
    private String queryStr;
    private int pageNo;
    private int pageSize;
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @return the toName
     */
    public String getToName() {
        return toName;
    }
    /**
     * @return the toMoney
     */
    public double getToMoney() {
        return toMoney;
    }
    /**
     * @return the queryStr
     */
    public String getQueryStr() {
        return queryStr;
    }
    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }
    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @param toName the toName to set
     */
    public void setToName(String toName) {
        this.toName = toName;
    }
    /**
     * @param toMoney the toMoney to set
     */
    public void setToMoney(double toMoney) {
        this.toMoney = toMoney;
    }
    /**
     * @param queryStr the queryStr to set
     */
    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }
    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
}
