  
  package com.cx.bank.test;
  import java.awt.*;
  import java.awt.event.*;
  import javax.swing.*;
  import java.util.*;
  import java.io.*;
  import com.cx.bank.manager.*;
  import com.cx.bank.util.*;
  import com.cx.bank.test.*;
  //javac -d . *.java          java com.cx.bank.test.BankLoginScreen

  /**
  *银行登录界面
  *@author RG
  *@version 1.6 2018/07/22
  */
  public class BankLoginScreen extends JFrame
  {  
	  String userName = null;//已登录的用户名
	  Manager mi;//业务层对象
	  BankSystemScreen bss = null;//系统界面
	  //标签组
	  JLabel jl1 = new JLabel("用 户 名");
	  JLabel jl2 = new JLabel("用户密码");

	  //文本区组
	  JTextField jt1 = new JTextField("请输入用户名",10);
	  JTextField jt2 = new JTextField("请输入密码",10);

	  //按钮组
	  JButton jb1 = new JButton("注册");
	  JButton jb2 = new JButton("登录");

	  //菜单
	  MenuBar mb = new MenuBar();

	  public void init()//初始化框架
	  {
		  //设置各组件字体，颜色
		  jl1.setFont(new java.awt.Font("Dialog",1,24));//“dialog”代表字体，1代表样式(1是粗体，0是平常的）24是字号
		  jl2.setFont(new java.awt.Font("Dialog",1,24));
		  jt1.setFont(new java.awt.Font("Dialog",0,18));
		  jt2.setFont(new java.awt.Font("Dialog",0,18));
		  jb1.setFont(new java.awt.Font("Dialog",1,20));
		  jb2.setFont(new java.awt.Font("Dialog",1,20));
		  jt1.setForeground(Color.gray);//设置文本区字体颜色默认为灰色
		  jt2.setForeground(Color.gray); 

          Container ct;//容器
          BackgroundPanel bgp;//自定义的背景类 
		  bgp=new BackgroundPanel((new ImageIcon("image01.jpg")).getImage()); //参数是一个Image对象
		  bgp.setLayout(null);//要想显示自定义布局，布局必须设为null
		  jl1.setBounds(125,275,100,50);//设置组件大小和位置。分别为左上角x,y坐标，组件x,y大小
		  jt1.setBounds(275,275,200,50);
		  jl2.setBounds(125,350,100,50);
		  jt2.setBounds(275,350,200,50);
		  jb1.setBounds(150,600,100,50);
		  jb2.setBounds(350,600,100,50);
		  //添加组件
		  bgp.add(jl1);
	      bgp.add(jt1);
	      bgp.add(jl2);
	      bgp.add(jt2);
		  bgp.add(jb1);
	      bgp.add(jb2);
		  
		  //设置框架布局和添加面板显示
		  this.setSize(600,800);//设置框架界面尺寸
		  this.setResizable(false);//设置框架界面是否可调大小，否
		  this.setLocation(750,200);//设置启动位置
		  ct=this.getContentPane();		  
		  bgp.setBounds(0,0,600,800);  
		  ct.add(bgp);

		  //添加菜单和菜单项
		  this.setMenuBar(mb);
		  Menu m = new Menu("功能");
		  mb.add(m);
		  MenuItem mi1 = new MenuItem("退出");
		  m.add(mi1);

		  //注册监听事件
		  //注册文本区键盘焦点事件
		  jt1.addFocusListener(new LoginTextFocusListener1());
		  jt2.addFocusListener(new LoginTextFocusListener2());
		  //注册注册登录按钮点击事件
		  jb1.addActionListener(new LoginButton1Action(this));
		  jb2.addActionListener(new LoginButton2Action(this));
		  //注册菜单退出事件
		  mi1.addActionListener(new LoginMenu1Action(this));
		  //注册Windows事件
		  this.addWindowListener(new LoginWindowListener(this));
	  }
	  public BankLoginScreen(Manager mai)//构造方法
	  {
		  this.setTitle("银行登录系统");
		  this.mi = mai;
		  init();
	  }
	  /**
	  *银行登录系统的主方法
	  *@param args 从命令行接收的字符串
	  */
	  public static void main(String[] args) 
	  {  
		  try
		  {
			  Manager mai = ManagerImp1.getInstance();//为了延长业务层对象的生命周期，在主方法创建，用成员变量接收
			  BankLoginScreen b = new BankLoginScreen(mai);
			  b.show();
		  }
		  catch (Exception ee)
		  {
			  System.out.println(ee);
		  }
	  }
  }


  /**
  *注册按钮监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class LoginButton1Action implements ActionListener
  {
	  BankLoginScreen bls;//登录界面对象
	  public LoginButton1Action(BankLoginScreen bls)
	  {
		  this.bls = bls;
	  }
	  public void actionPerformed(ActionEvent e)//点击事件
	  {
		  String userName = bls.jt1.getText();//获取登录界面用户名文本框内容
		  String userPwd = bls.jt2.getText();//获取登录界面密码文本框内容
		  boolean flag = false;//判断注册是否成功的标记位
		  //如果登录界面两个文本框的内容不是默认内容，注册用户，如果是把标记位设为假
		  if("请输入用户名".equals(userName)==false&&"请输入密码".equals(userPwd)==false)
		  {
			  if(userName.length()>10) 
				  JOptionPane.showMessageDialog(null,"用户名不得小于1位,大于10位");
			  else
			  {
				  if(userPwd.length()>=6&&userPwd.length()<=16)
				  {
					  try
					  {
						  flag = bls.mi.register(userName,userPwd);//标记位设为业务层注册方法的返回值
					  }
					  catch(FileNotFoundException ee)
					  {
						  System.out.println(ee);
					  }catch(IOException ee)
					  {
						  System.out.println(ee);
					  }catch(Exception ee)
					  {
						  System.out.println(ee);
					  }
				  }
				  else JOptionPane.showMessageDialog(null,"密码不得小于6位,大于16位");
			  }
				  
		  }
		  if(flag)//标记位为真，注册成功
		  {
			  //System.out.println("注册成功！");
			  JOptionPane.showMessageDialog(null,"注册成功，请登录！");
			  bls.jt1.setText("请输入用户名");
			  bls.jt2.setText("请输入密码"); 
		  }
		  else
		  {
			  JOptionPane.showMessageDialog(null,"注册失败,请重新注册！");
			  //System.out.println("注册失败！");
		  }
	  }	  
  }
  /**
  *登录按钮监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class LoginButton2Action implements ActionListener
  {
	  BankLoginScreen bls;
	  public LoginButton2Action(BankLoginScreen bls)
	  {
		  this.bls = bls;
	  }
	  public void actionPerformed(ActionEvent e)
	  {
		  String userName=bls.jt1.getText();//获取登录界面用户名文本框内容
		  String userPwd=bls.jt2.getText();//获取登录界面密码文本框内容
		  boolean flag = false;//判断登录是否成功的标记位
		  //如果登录界面两个文本框的内容不是默认内容，用户登录
		  if("请输入用户名".equals(userName)==false&&"请输入密码".equals(userPwd)==false)
		  {
			  try
			  {
				  flag = bls.mi.login(userName,userPwd);//标记位设为业务层登录方法的返回值
			  }
			  catch(FileNotFoundException ee)
			  {
				  System.out.println(ee);
			  }catch(IOException ee)
			  {
				  System.out.println(ee);
			  }catch(Exception ee)
			  {
				  System.out.println(ee);
			  }
		  }
		  else flag = false;
		  if(flag == true)//如果标记位为真，登录成功,否则弹出登录失败提示窗口
		  {
			  System.out.println("登录成功！");
			  JOptionPane.showMessageDialog(null,"登录成功！");
			  //this.bankSystemTest(mi);
			  /*类重复错误
			  已解决，解决方式：在BankSystemScreen类中导入了相关的包
			  */
			  //将文本框复位
			  bls.jt1.setText("请输入用户名");
			  bls.jt2.setText("请输入密码");
			  bls.jt1.setForeground(Color.gray);
			  bls.jt2.setForeground(Color.gray);

			  bls.userName = userName;//取得当前登录的用户名
			  bls.dispose();//关闭当前登录窗口
			  //如果系统界面对象为null则创建对象，否则显示银行系统窗口
			  if(this.bls.bss == null)
			  {
				  this.bls.bss = new BankSystemScreen(bls);
			  }
			  else
			  {
				  this.bls.bss.show();
			  }
		  }
		  else
		  {
			  JOptionPane.showMessageDialog(null,"登录失败！请重新登录！");
			  System.out.println("登录失败！请重新登录！");
		  }
	  }
  }
  /**
  *菜单退出监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class LoginMenu1Action implements ActionListener
  {
	  BankLoginScreen bls;
	  public LoginMenu1Action(BankLoginScreen bls)
	  {
		  this.bls = bls;
	  }
	  public void actionPerformed(ActionEvent ee)
	  {
		  try{
			  bls.mi.secede();//调用业务层退出方法
		  }catch(FileNotFoundException e){
			  System.out.println(e);
		  }catch(IOException e){
			  System.out.println(e);
		  }catch(Exception e){
			  System.out.println(e);
		  }
      }
  }
  /**
  *用户名文本区焦点监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class LoginTextFocusListener1 implements FocusListener
  {
	  public void focusGained(FocusEvent event)//组件获得键盘焦点时调用
	  {
		  JTextField tf = (JTextField)event.getSource();//获取焦点事件文本区原对象
		  String s = tf.getText();//获取文本区内容
		  if(s.equals("请输入用户名"))//如果文本区为默认内容，则将其设空，并释放焦点
		  {
			  tf.setForeground(Color.black);//设置文本区字体颜色为黑色
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
			  tf.setText("请输入用户名");
			  tf.setForeground(Color.gray);//设置文本区字体颜色默认为灰色
		  }
	  }
  }
  /**
  *密码文本区焦点监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class LoginTextFocusListener2 implements FocusListener
  {
	  public void focusGained(FocusEvent event)//组件获得键盘焦点时调用
	  {
		  JTextField tf = (JTextField)event.getSource();//获取焦点事件文本区原对象
		  String s = tf.getText();//获取文本区内容
		  if(s.equals("请输入密码"))//如果文本区为默认内容，则将其设空，并释放焦点
		  {
			  tf.setForeground(Color.black);
			  tf.setText("");
			  tf.requestFocus();
		  }
	  }
	  public void focusLost(FocusEvent event)//组件失去键盘焦点时调用。
	  {
		  JTextField tf = (JTextField)event.getSource();
		  String s = tf.getText();
		  if(s.equals(""))//如果文本区为空值，设为默认值
		  {
			  tf.setText("请输入密码");
			  tf.setForeground(Color.gray);
		  }
	  }
  }
  /**
  *Windows退出监听器
  *@author RG
  *@version 1.5 2018/07/19
  */
  class LoginWindowListener extends WindowAdapter
  {
	  BankLoginScreen bls;
	  public LoginWindowListener(BankLoginScreen bls)
	  {
		  this.bls = bls;
	  }
	  public void windowClosing(WindowEvent wevent)
	  {
		  try{
		      //bls.getManager().secede();
			  bls.mi.secede();//调用业务层退出方法
		  }catch(FileNotFoundException e){
			  System.out.println(e);
		  }catch(IOException e){
			  System.out.println(e);
		  }catch(Exception e){
			  System.out.println(e);
		  }
      }
  }
  /**
  *自定义背景类
  *@author RG
  *@version 1.5 2018/07/22
  */
  class BackgroundPanel extends JPanel  
  {  
	  Image im;  
	  public BackgroundPanel(Image im)  
	  {  
		  this.im=im;  
		  this.setOpaque(true);//设置控件不透明,若是false,那么就是透明
	  }  
	  public void paintComponent(Graphics g)//绘图类,详情可见博主的Java 下 java-Graphics 
	  {  
		  super.paintComponents(g);  
		  g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  //绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。图像中的透明像素不影响该处已存在的像素
	  }  
  }

