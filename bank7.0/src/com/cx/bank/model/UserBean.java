
package com.cx.bank.model;

import java.sql.Timestamp;

/**
 * 用户数据bean
 *
 * @author RaoGang
 * @version 3.0 2018/09/18
 */
public class UserBean {
    private int id;// 用户id
    private String role;// 用户角色
    private String username;// 用户名
    private String password;// 用户密码
    private String realName;//真实姓名
    private String sex;//性别
    private String email;//邮箱地址
    private double money;// 金额
    private String conditions;// 用户状态
    private Timestamp udate;// 创建时间
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    public Timestamp getUdate() {
        return udate;
    }
    public void setUdate(Timestamp udate) {
        this.udate = udate;
    }
    
}
