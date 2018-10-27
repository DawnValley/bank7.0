  
  
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
  *转账界面1.5
  *@author RG
  *@version 1.5 2018/07/21
  */
  public class InputMessage extends Frame
  {
	  BankSystemScreen bss;//银行系统对象
	  Manager mi;//业务层对象

	  //标签组
	  JLabel jl1 = new JLabel("请输入对方用户名：");
	  JLabel jl2 = new JLabel("请输入转账金额：  ");
	  //文本区组
	  JTextField jtf1 = new JTextField("请输入用户名");
	  JTextField jtf2 = new JTextField("请输入金额");
	  //按钮组
	  JButton jb1 = new JButton("确定");
	  JButton jb2 = new JButton("否");
	  public InputMessage(BankSystemScreen bs)
	  {
		  this.bss = bs;
		  this.mi = bss.mi;
		  this.init();
	  }
	  public void init()
	  {
		  jtf1.setForeground(Color.gray);//设置文本区字体颜色默认为灰色
		  jtf2.setForeground(Color.gray);//设置文本区字体颜色默认为灰色
		  //设置框架并添加组件
		  this.setLayout(new GridLayout(3,2,5,5));
		  this.setSize(300,100);
		  this.setResizable(false);
		  this.setLocation(700,450);
		  this.setVisible(true);
		  this.setTitle("转账");
		  this.add(jl1);
		  this.add(jtf1);
		  this.add(jl2);
		  this.add(jtf2);
		  this.add(jb1);
		  this.add(jb2);
		  //this.setAlwaysOnTop(true);//把窗口设为最前
		  this.show();

		  //注册事件
		  jtf1.addFocusListener(new LoginTextFocusListener1());//注册输入用户名文本框焦点事件
		  jtf2.addFocusListener(new SystemTextFocusListener1());//注册输入金额文本框焦点事件
		  jb1.addActionListener(new InputButton1Action(this));//注册确定按钮
		  jb2.addActionListener(new InputButton2Action(this));//注册否定按钮
		  this.addWindowListener(new SystemWindowListener(this));//注册Windows事件
	  }
  }

  /**
  *金额文本区焦点监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class SystemTextFocusListener1 implements FocusListener
  {
	  public void focusGained(FocusEvent event)//组件获得键盘焦点时调用
	  {
		  JTextField tf = (JTextField)event.getSource();//获取焦点事件文本区原对象
		  String s = tf.getText();//获取文本区内容
		  if(s.equals("请输入金额"))//如果文本区为默认内容，则将其设空，并释放焦点
		  {
			  tf.setForeground(Color.black);//设置文本区字体颜色默认为灰色
			  tf.setText("");
			  tf.requestFocus();
		  }
	  }
	  public void focusLost(FocusEvent event)//组件失去键盘焦点时调用
	  {
		  JTextField tf = (JTextField)event.getSource();
		  String s = tf.getText();
		  if(s.equals(""))//如果文本区为空值，设为默认值
		  {
			  tf.setText("请输入金额");
			  tf.setForeground(Color.gray);//设置文本区字体颜色默认为灰色
		  }
	  }
  }
  /**
  *转账确定按钮监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class InputButton1Action implements ActionListener//确定按钮监听器
  {
	  InputMessage im;//转账界面
	  public InputButton1Action(InputMessage ims)
	  {
		  this.im = ims;//获取转账界面对象
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  String name = null;//已登录的用户的用户名
		  String userName = im.jtf1.getText();//转账的对方用户名
		  String money = im.jtf2.getText();//转账输入金额
		  double m = 0.0;//金额
		  boolean flag = false;//转账金额不合法标记位
		  boolean fg = false;//转账标记位
		  int count = 0;//小数个数计算器
		  char[] ch = money.toCharArray();//将金额文本区获得的字符串转换成字符数组
		  name = this.im.bss.bls.userName;//得到当前用户名
		  //如果对方账号为登录用户，弹出提示框，否则进行下一步判断
		  if(userName.equals(name) == true)
		  {
			  JOptionPane.showMessageDialog(null,"转账用户为当前用户，请重新输入");
		  }
		  else
		  {
			  for(int i = 0;i<money.length();i++)
			  {//字符数组的字符依个与合法字符比较
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
				  count = 0;
			  }
			  if(flag == false)//如果不合法标记位为假，进行转账操作
			  {
				  m = Double.parseDouble(money);//将金额字符串转换成double类型
				  try
				  {
					  fg = im.mi.transfer(userName,m);//执行业务层转账方法
				  }
				  catch(Exception ee)
				  {
					  System.out.println(ee);
				  }finally
				  {
					  if(fg == true)//转账标记位为真，转账成功，否则失败
					  {
						  System.out.println("转账成功！");
						  JOptionPane.showMessageDialog(null,"转账成功！共转款："+m+"元");//显示转款成功提示信息
						  //重置文本框
						  im.jtf1.setText("请输入用户名");
						  im.jtf2.setText("请输入金额");
						  this.im.dispose();//关闭当前窗口
						  this.im.bss.setEnabled(true);//上级窗口设为可输入状态
					  }
					  else
					  {
						  System.out.println("转账失败！");
						  JOptionPane.showMessageDialog(null,"转账失败！");
					  }
				  }
			  }
			  else//如果不合法标记位为真，弹出请重新输入弹窗
			  {
				  JOptionPane.showMessageDialog(null,"请重新输入");
				  //重置文本区内容为默认值
				  im.jtf1.setText("请输入用户名");
				  im.jtf2.setText("请输入金额");
			  }
			  
		  }
	  }	  
  }
  /**
  *转账否定按钮监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class InputButton2Action implements ActionListener//否按钮监听器
  {
	  InputMessage im;//转账界面
	  public InputButton2Action(InputMessage ims)
	  {
		  this.im = ims;//转账界面对象
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  //重置文本区内容为默认值
		  im.jtf1.setText("请输入用户名");
		  im.jtf2.setText("请输入金额");
	  }	  
  }
  /**
  *Windows退出监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class SystemWindowListener extends WindowAdapter
  {
	  InputMessage im;//转账界面
	  public SystemWindowListener(InputMessage ims)
	  {
		  this.im = ims;//转账界面对象
	  }
	  public void windowClosing(WindowEvent wevent)//关闭
	  {
		  //重置文本区内容为默认值
		  im.jtf1.setText("请输入用户名");
		  im.jtf2.setText("请输入金额");
		  im.dispose();//关闭当前窗口
		  im.bss.setEnabled(true);//设置银行系统界面为可输入状态
      }
  }

