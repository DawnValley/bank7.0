package com.cx.bank.mode1;

import java.util.Date;
import java.util.Set;

/**
 * 交易记录的数据bean
 * @author RG
 * @version 2.0 2018/09/08
 */
public class TransactionRecordBean {

	private Integer bId;
    private UserBean users;
	private String toName;
	private Double bMoney;
	private String transactionType;
	private Date bDate;
    /**
     * @return the bId
     */
    public Integer getbId() {
        return bId;
    }
    /**
     * @return the users
     */
    public UserBean getUsers() {
        return users;
    }
    /**
     * @return the toName
     */
    public String getToName() {
        return toName;
    }
    /**
     * @return the bMoney
     */
    public Double getbMoney() {
        return bMoney;
    }
    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }
    /**
     * @return the bDate
     */
    public Date getbDate() {
        return bDate;
    }
    /**
     * @param bId the bId to set
     */
    public void setbId(Integer bId) {
        this.bId = bId;
    }
    /**
     * @param users the users to set
     */
    public void setUsers(UserBean users) {
        this.users = users;
    }
    /**
     * @param toName the toName to set
     */
    public void setToName(String toName) {
        this.toName = toName;
    }
    /**
     * @param bMoney the bMoney to set
     */
    public void setbMoney(Double bMoney) {
        this.bMoney = bMoney;
    }
    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    /**
     * @param bDate the bDate to set
     */
    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

	
	
}
