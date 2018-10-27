  
  
  package com.cx.bank.mode1;
  /**<b>模型层MoneyBean类</b>
  *@author RG
  *@version 1.0 2018/06/11
  */
  public class  MoneyBean
  {
	  private double money;//存储用户的存款
	  private static MoneyBean moneyBean;//单例的本类变量

	  
	  private MoneyBean()//私有化的无参构造方法
	  {
		  this.money = 0.0;
	  }

	  /**
	  *<b>设置金额</b>
	  *@param m double 设置的金额
	  */
	  public void setMoney(double m)
	  {
		  this.money = m;
	  }
	  public double getMoney()//获取当前金额
	  {
		  return this.money;
	  }
	  public static MoneyBean getMoneyBean()//返回对象的地址
	  {
		  if(moneyBean == null) moneyBean = new MoneyBean();//饱汉模式，如果对象为空则创建moneyBean对象
		  return moneyBean;
	  }

  }
