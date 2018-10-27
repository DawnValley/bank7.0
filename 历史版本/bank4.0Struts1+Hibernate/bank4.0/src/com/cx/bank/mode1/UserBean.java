
package com.cx.bank.mode1;

import java.util.Date;

/**
 * 用户数据bean
 *
 * @author RG
 * @version 3.0 2018/09/18
 */
public class UserBean {
    private int id;
    private String username;
    private String password;
    private double money;
    private Date udate;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
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
     * @return the money
     */
    public double getMoney() {
        return money;
    }
    /**
     * @return the udate
     */
    public Date getUdate() {
        return udate;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @param money the money to set
     */
    public void setMoney(double money) {
        this.money = money;
    }
    /**
     * @param udate the udate to set
     */
    public void setUdate(Date udate) {
        this.udate = udate;
    }

}
