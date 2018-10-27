package com.cx.bank.model;

/**
 * 查询对象类 所有查询的信息封装成的对象
 * 
 * @author RaoGang
 * @version 7.0 2018/10/08
 */
public class QueryObject {
    private int id;// id
    private String queryStr;// 查询信息
    private int pageNo;// 当前页码
    private int pageSize;// 页面尺寸
    private String type;// 类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*
     * public int getId() { return id; } public String getQueryStr() { return
     * queryStr; } public void setId(int id) { this.id = id; } public void
     * setQueryStr(String queryStr) { this.queryStr = queryStr; } public int
     * getPageNo() { return pageNo; } public int getPageSize() { return
     * pageSize; } public void setPageNo(int pageNo) { this.pageNo = pageNo; }
     * public void setPageSize(int pageSize) { this.pageSize = pageSize; }
     * public String getType() { return type; } public void setType(String type)
     * { this.type = type; }
     */

}
