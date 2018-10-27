  
  package com.cx.bank.test;
  import java.awt.*;
  import java.awt.event.*;
  import javax.swing.*;
  import java.util.*;
  import java.io.*;
  import com.cx.bank.manager.*;
  import com.cx.bank.util.*;
  import com.cx.bank.test.*;

  /**
  *银行系统1.5
  *@author RG
  *@version 1.5 2018/07/20
  */
  public class BankSystemScreen extends JFrame
  {
	  BankLoginScreen bls;//登录界面的对象
	  InputMessage ims;//转账界面
	  Manager mi;//业务层对象
	  //菜单
	  MenuBar mb = new MenuBar();
	  
	  //按钮组
	  JButton jb1 = new JButton("查询余额");
	  JButton jb2 = new JButton("取款");
	  JButton jb3 = new JButton("存款");
	  JButton jb4 = new JButton("转账");
	  JButton jb5 = new JButton("退出系统");

	  public BankSystemScreen(BankLoginScreen bl)//构造方法
	  {
		  this.bls = bl;
		  this.mi = bls.mi;
		  this.init();
	  }
	  public void init()//初始化框架
	  {
		  //菜单布局和添加
		  Menu m = new Menu("功能");
		  m.add("欢迎");
		  m.addSeparator();//添加间隔
	      MenuItem mi1 = new MenuItem("返回上一层");
		  MenuItem mi2 = new MenuItem("退出");
		  m.add(mi1);
		  m.add(mi2);
		  mb.add(m);
		  this.setMenuBar(mb);

		  //框架按钮布局-网格布局
		  this.setTitle("银行系统");
		  this.setLayout(new FlowLayout());
		  this.setSize(300,400);
		  this.setResizable(false);//设置框架界面是否可调大小，否
		  this.setLocation(700,300);
		  this.setVisible(true);

		  JPanel jp = new JPanel(new GridLayout(5,1,20,20));
		  jp.add(jb1);
		  jp.add(jb2);
		  jp.add(jb3);
		  jp.add(jb4);
		  jp.add(jb5);
		  this.add(jp);

		  //注册事件
		  this.addWindowListener(new LoginWindowListener(this.bls));//注册Windows事件
		  jb1.addActionListener(new SystemButton1Action(this.mi));//注册查询按钮事件
		  jb2.addActionListener(new SystemButton2Action(this.mi));//注册取款按钮事件
		  jb3.addActionListener(new SystemButton3Action(this.mi));//注册存款按钮事件
		  jb4.addActionListener(new SystemButton4Action(this));//注册转账按钮事件
		  jb5.addActionListener(new LoginMenu1Action(this.bls));//注册退出按钮事件
		  mi1.addActionListener(new SystemMenuItem1Action(this));//注册返回上一层菜单项事件
		  mi2.addActionListener(new LoginMenu1Action(this.bls));//注册退出菜单项事件

		  this.show();//显示本界面
	  }
  }
  /**
  *返回上一层菜单项监听器
  *@author RG
  *@version 1.5 2018/07/20
  */
  class SystemMenuItem1Action implements ActionListener
  {
	  BankSystemScreen bss;
	  public SystemMenuItem1Action(BankSystemScreen bs)
	  {
		  this.bss = bs;
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  this.bss.dispose();//关闭当前窗口
		  this.bss.bls.show();//打开上一层窗口
	  }
  }
  /**
  *查询按钮监听器
  *@author RG
  *@version 1.5 2018/07/20
  */
  class SystemButton1Action implements ActionListener
  {
	  Manager mi;
	  public SystemButton1Action(Manager m)
	  {
		  this.mi = m;
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  String s = null;
		  s = mi.inquiry();//调用业务层查询方法并取得余额字符串
		  JOptionPane.showMessageDialog(null,"您的账户余额为："+s+"元");
	  }
  }
  /**
  *取款按钮监听器
  *@author RG
  *@version 1.5 2018/07/20
  */
  class SystemButton2Action implements ActionListener
  {
	  Manager mi;
	  public SystemButton2Action(Manager m)
	  {
		  this.mi = m;
	  }
	  public void actionPerformed(ActionEvent ae)
	  {
		  String inputValue = null;//接收文本区内容的字符串
		  boolean flag = true;//输入不合法标记位
		  double d = 0.0;//金额
		  int count = 0;//小数点个数计算器
		 
		  inputValue = JOptionPane.showInputDialog("请输入取款金额：");
		  //如果文本区内容为空串或者null，输入不合法标记位设为真
		  if("".equals(inputValue)||inputValue == null)
		  {
			  flag = true;
		  }
		  else
		  {//否则有内容，不合法标记位设假，判断是否为数字或小数点，不是数字不合法标记位设为真，
		  //并判断小数点个数，个数大于1不合法标记位也设真
			  flag = false;
			  char[] ch = inputValue.toCharArray();//将文本区获得的字符串转换成字符数组
			  for(int i = 0;i<inputValue.length();i++)//字符数组的字符依个与合法字符比较
			  {
				  if("0123456789.".indexOf(ch[i])<0||"0123456789.".indexOf(ch[i])>=11)
				  {//如果不在合法字符范围内，不合法标记位设真，退出循环比较
					  flag = true;
					  break;
				  }
				  if(".".indexOf(ch[i])==0)//如果一次循环的字符是小数点，小数点个数加1
				  {
					  count=count+1;
				  }
			  }
			  if(count > 1) //如果小数点个数大于1，不合法标记位设真
			  {
				  flag = true;
			  }
		  }
		  if(flag == true)//如果不合法标记位为真，弹出请重新输入弹窗
		  {
			  JOptionPane.showMessageDialog(null,"请重新输入");
		  }
		  if(flag == false)//如果不合法标记位为假，进行取款操作
		  {
			  boolean f = false;//取款标记位
			  d = Double.parseDouble(inputValue);
			  try{
				  f = mi.withDrawals(d);
			  }catch(InvalidWithDrawalstException e)
			  {//捕获取款额为负数异常
				  System.out.println(e);
			  }catch(AccountOverDrawnException e)
			  {//捕获取款额超过存款异常
				  System.out.println(e);
			  }catch(Exception e)
			  {//捕获取款异常
				  System.out.println(e);
			  }finally
			  {
				  if(f == true)//取款标记位为真，取款成功，否则失败
				  {
					  JOptionPane.showMessageDialog(null,"取款成功,共取款："+d+"元！");
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null,"取款失败！");
				  }
				  mi.inquiry();//查询余额
			  }
		  }  
	  }
  }
  /**
  *存款按钮监听器
  *@author RG
  *@version 1.5 2018/07/21
  */
  class SystemButton3Action implements ActionListener//存款按钮事件
  {
	  Manager mi;
	  public SystemButton3Action(Manager m)
	  {
		  this.mi = m;
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  String inputValue = null;//接收文本区内容的字符串
		  double d = 0.0;//金额
		  boolean flag = true;//输入不合法标记位
		  int count = 0;//小数点个数计算器
		  
		  inputValue = JOptionPane.showInputDialog("请输入存款金额：");
		  //如果文本区内容为空串或者null，输入不合法标记位设为真
		  if("".equals(inputValue)||inputValue == null)
		  {//否则有内容，不合法标记位设假，判断是否为数字或小数点，不是数字不合法标记位设为真，
		  //并判断小数点个数，个数大于1不合法标记位也设真
			  flag = true;
		  }
		  else
		  {
			  flag = false;
			  char[] ch = inputValue.toCharArray();//将文本区获得的字符串转换成字符数组
			  for(int i = 0;i<inputValue.length();i++)//字符数组的字符依个与合法字符比较
			  {
				  if("0123456789.".indexOf(ch[i])<0||"0123456789.".indexOf(ch[i])>=11)
				  {//如果不在合法字符范围内，不合法标记位设真，退出循环比较
					  flag = true;
					  break;
				  }
				  if(".".indexOf(ch[i])==0)//如果一次循环的字符是小数点，小数点个数加1
				  {
					  count=count+1;
				  }
			  }
			  if(count > 1)//如果小数点个数大于1，不合法标记位设真
			  {
				  flag = true;
			  }
		  }
		  if(flag == true)//如果不合法标记位为真，弹出请重新输入弹窗
		  {
			  JOptionPane.showMessageDialog(null,"存款失败，请重新输入！");
		  }
		  if(flag == false)//如果不合法标记位为假，进行取款操作
		  {
			  boolean f = false;//存款标记位
			  d = Double.parseDouble(inputValue);
			  try{
				  f = mi.deposit(d);
			  }catch(InvalidDepositException ee)
			  {//捕获存款额为负数异常
				  System.out.println(ee);
			  }catch(Exception ee)
			  {//捕获取款异常
				  System.out.println(ee);
			  }finally
			  {//取款标记位为真，取款成功，否则失败
				  if(f == true)
				  {
					  JOptionPane.showMessageDialog(null,"存款成功,共存款："+d+"元！");
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null,"存款失败！");
				  }
				  mi.inquiry();//查询余额
			  }
		  }  
	  }
  }
  /**
  *转账按钮监听器
  *@author RG
  *@version 1.5 2018/07/21
  */
  class SystemButton4Action implements ActionListener
  {
	  BankSystemScreen bss;//系统界面
	  public SystemButton4Action(BankSystemScreen bs)
	  {
		  this.bss = bs;//获取系统界面对象
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  bss.setEnabled(false);//设置系统界面窗口为不可输入
		  //如果转账界面为null新建转账界面对象，否则显示转账界面
		  if(this.bss.ims == null)
		  {
			  this.bss.ims = new InputMessage(this.bss);
		  }
		  else this.bss.ims.show();
	  }
  }

