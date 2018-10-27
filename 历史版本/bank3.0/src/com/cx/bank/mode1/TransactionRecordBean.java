package com.cx.bank.mode1;

import java.util.Date;

/**
 * 交易记录的数据bean
 * @author RG
 * @version 2.0 2018/09/08
 */
public class TransactionRecordBean {

	private Integer bId;
	private Integer userId;
	private String myName;
	private String toName;
	private Double bMoney;
	private String transactionType;
	private Date bDate;
	public Integer getbId() {
		return bId;
	}
	public void setbId(Integer bId) {
		this.bId = bId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
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
	public Date getbDate() {
		return bDate;
	}
	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}
	
	
}
