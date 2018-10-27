package com.cx.bank.form;

import org.apache.struts.action.ActionForm;
/**
 * 业务的表单bean
 * @author RG
 * @version 2018/09/11
 */
public class BusinessForm extends ActionForm {

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
	
}
