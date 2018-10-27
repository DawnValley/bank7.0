
package com.cx.bank.mode1;

/**
 * 登录表单bean类
 * 
 * @author RG
 * @version 2.0 2018/08/20 17:29:01
 */
public class FormBean {
	private String username;
	private String password;
	private String toName;
	private Double toMoney;

	public FormBean() {
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

	public void setToName(String name) {
		this.toName = name;
	}

	public void setToMoney(String toMoney) {
		this.toMoney = Double.parseDouble(toMoney);
	}

	public void setToMoney(Double toMoney) {
		this.toMoney = toMoney;
	}

	public String getToName() {
		return toName;
	}

	public Double getToMoney() {
		return toMoney;
	}
}
