  
  package com.cx.bank.manager;
  import com.cx.bank.model.MoneyBean;
  /**
  *<b>业务层Manager类</b>
  *@author RG
  *@version 1.0 2018/06/11
  *@see com.cx.bank.model.MoneyBean
  */
  public class  ManagerImpl
  {
	  MoneyBean mb = MoneyBean.getMoneyBean();//MoneyBean的单例变量
	  public void inquiry()//查询金额
	  {
		  System.out.println("您的账户当前金额为："+mb.getMoney()+"元");
	  }
	  /**
	  *<b>用户取款</b>
	  *@param m double 取款数额
	  */
	  public void withDrawals(double m)
	  {
		  if(m > mb.getMoney())//当取款数额大于余额，取款失败
		  {
			  System.out.println("您的账户余额不足，取款失败！");
			  this.inquiry();
		  }
		  else if(m < 0)//当取款数额为负数，取款失败，否则输出取款金额及当前账户金额
		  {
			  System.out.println("您的取款金额设置错误，取款失败，请重新输入！");
			  this.inquiry();
		  }
		  else
		  {
			  mb.setMoney(mb.getMoney() - m);//取款后重新设置金额
			  System.out.println("取款成功！共取款："+m+"元");
			  this.inquiry();
		  }
	  }
	  /**
	  *<b>用户存款</b>
	  *@param m double 存款数额
	  */
	  public void deposit(double m)
	  {
		  if(m < 0)//当存款数额为负数，存款失败，否则输出存款金额及当前账户金额
		  {
			  System.out.println("您的存款金额设置错误，存款失败，请重新输入！");
			  this.inquiry();
		  }
		  else
		  {
			  mb.setMoney(mb.getMoney() + m);
			  System.out.println("存款成功！共存款："+m+"元");//存款后重新设置金额
			  this.inquiry();
		  }
	  }
  }
