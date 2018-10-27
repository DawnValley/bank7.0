  

  package com.cx.bank.test;
  import java.util.*;
  import java.io.*;
  import com.cx.bank.manager.*;
  import com.cx.bank.util.*;
  /**
  *<b>测试层TestBank</b>
  *@author RG
  *@version 1.3 2018/07/15
  *@see com.cx.bank.manager.ManagerImp1
  */
  public class TestBank 
  {

	  public void printMenu1()//银行测试注册登录菜单
	  {
		  System.out.println("**************************银行测试系统1.3**************************");
          System.out.println("                            1.登录                            ");
		  System.out.println("                            2.注册                                 ");
		  System.out.println("                            3.退出系统                             ");
		  System.out.println("******************************欢迎使用*****************************");
	  }
	  public void printMenu2()//银行测试系统菜单
	  {
		  System.out.println("**************************银行测试系统1.3**************************");
          System.out.println("                            1.查询余额                             ");
		  System.out.println("                            2.取款                                 ");
		  System.out.println("                            3.存款                                 ");
		  System.out.println("                            4.转账                                 ");
		  System.out.println("                            5.退出系统                             ");
		  System.out.println("******************************欢迎使用*****************************");
	  }

	  /**
	  *<b>银行登录测试</b>
	  *@param mi 业务层对象
	  *对登录注册测试
	  */
	  public void bankSystem(Manager mi)
	  {
		  Scanner sc = new Scanner(System.in);//接收输入数据的对象
		  //int n = 0;//操作数字
		  String n = null;
		  //Manager mi = ManagerImp1.getInstance();//业务层ManagerImpl对象赋给业务层接口Manager
		  while(true)//当为true时运行银行测试系统
		  {
			  this.printMenu1();
			  System.out.print("请输入操作数字(整数1~3)：");
			  //当输入不是整数时出错：Exception in thread "main" java.util.InputMismatchException
			  //未解决，解决思想:采用抛出异常的处理方式
			  //已解决，更改为用字符串接收输入操作数，不用异常，用String类equals方法比较即可
			  //n = sc.nextInt();
			  n = sc.next();
			  //char n = n1.charAt(0);

			  if("1".equals(n))//登录
			  {
				  System.out.print("输入你的用户名:");
			      String userName=sc.next();
                  System.out.print("输入你的密码:");
                  String userPwd=sc.next();
				  boolean flag = false;
				try{
				  flag = mi.login(userName,userPwd);
				}catch(FileNotFoundException e){
			        System.out.println(e);
		        }catch(IOException e){
					System.out.println(e);
				}catch(Exception e){
					System.out.println(e);
				}
                  if(flag)
				  {
				      System.out.println("登录成功！");
					  this.bankSystemTest(mi);
				  }
				  else System.out.println("登录失败！请注册！");
			  }

			  else if("2".equals(n))//注册
			  {
				  System.out.print("输入你的用户名:");
                  String userName=sc.next();
                  System.out.print("输入你的密码:");
                  String userPwd=sc.next();
				  boolean flag = false;

				try{
				  flag = mi.register(userName,userPwd);
				}catch(FileNotFoundException e){
					/*错误: 已捕获到异常错误FileNotFoundException
                                }catch(FileNotFoundException e){
					原因:因为catch(FileNotFoundException e)放在了catch(IOException e)后面
					没有正金字塔放置捕获的异常类
					*/
			        System.out.println(e);
		        }catch(IOException e){
					System.out.println(e);
				}catch(Exception e){
					System.out.println(e);
				}

			      if(flag){
				      System.out.println("注册成功！");
				  }else{
				      System.out.println("注册失败！");
				  }
			  }
			  else if("3".equals(n))//退出
			  {
				  System.out.println("****************您正在退出银行测试系统，谢谢您的使用***************");
				try{
				  mi.secede();
				}catch(FileNotFoundException e){
			        System.out.println(e);
		        }catch(IOException e){
					System.out.println(e);
				}catch(Exception e){
					System.out.println(e);
				}
			  }
			  else//操作数输入错误
			  {
				  System.out.println("输入错误！请输入正确的操作数字（整数1~3）！");
			  }
		  }

	  }
	  /**
	  *<b>银行测试系统</b>
	  *@param mi 业务层对象
	  *对输入的数据和业务层进行测试
	  */
	  public void bankSystemTest(Manager mi) 
	  {
		  Scanner sc = new Scanner(System.in);//接收输入数据的对象
		  //int n = 0;//操作数字
		  String n = null;

		  while(true)//当为true时运行银行测试系统
		  {
			  this.printMenu2();
			  System.out.print("请输入操作数字(整数1~5)：");
			  //当输入不是整数时出错：Exception in thread "main" java.util.InputMismatchException
			  //未解决，解决思想:采用抛出异常的处理方式
			  //n = sc.nextInt();
			  n = sc.next();

			  if("1".equals(n))//查询
			  {
				  System.out.println("您正在进行查询服务，您的查询结果如下：");
				  mi.inquiry();
			  }
			  else if("2".equals(n))//取款
			  {
				  System.out.print("您正在进行取款服务，请输入取款金额：");
				  double d = sc.nextDouble();
				  
				  try{
				      mi.withDrawals(d);
				  }catch(InvalidWithDrawalstException e){//捕获取款额为负数异常
					  System.out.println(e);
				  }
				  catch(AccountOverDrawnException e){//捕获取款额超过存款异常
					  System.out.println(e);
				  }catch(Exception e){//取款异常
					  System.out.println(e);
				  }
				  finally{
		              mi.inquiry();
		          }
			  }
			  else if("3".equals(n))//存款
			  {
				  System.out.print("您正在进行存款服务，请输入存款金额：");
				  double d = sc.nextDouble();

				  try{
				      mi.deposit(d);
			      }catch(InvalidDepositException e){//捕获存款额为负数异常
					  System.out.println(e);
				  }catch(Exception e){//存款异常
					  System.out.println(e);
				  }
				  finally{
		              mi.inquiry();
		          }
			  }

              else if("4".equals(n))//转账
			  {
				  System.out.print("请输入您要转账的账户:");
				  String account=sc.next();
                  System.out.print("请输入您要转账的金额:");
				  double money=sc.nextDouble();
				  boolean flag = false;
				try{
				  flag = mi.transfer(account,money);
				}catch(FileNotFoundException e){
			        System.out.println(e);
		        }catch(IOException e){
					System.out.println(e);
				}catch(Exception e){
					System.out.println(e);
				}

				  if(flag)
				  {
				      System.out.println("转账成功！");
				  }
				  else
				  {
				      System.out.println("转账失败！");
				  }  
			  }

			  else if("5".equals(n))//退出
			  {
				  System.out.println("****************您正在退出银行测试系统，谢谢您的使用***************");
				try{
				  mi.secede();
				}catch(FileNotFoundException e){
			        System.out.println(e);
		        }catch(IOException e){
					System.out.println(e);
				}catch(Exception e){
					System.out.println(e);
				}
			  }
			  else//操作数输入错误
			  {
				  System.out.println("输入错误！请输入正确的操作数字（整数1~5）！");
			  }
		  }
	  }

	  /**
	  *<b>主方法</b>
	  *@param args String[] 从命令行中带入的字符串
	  *@see TestBank#bankSystemTest(Manager mi)
	  *@see TestBank#bankSystem(Manager mi)
	  */
	  public static void main(String[] args) //throws Exception
	  {
		try{
		  Manager mi = ManagerImp1.getInstance();
		
		  TestBank tb = new TestBank();
		  tb.bankSystem(mi);//对银行系统进行测试
		}catch(ClassCastException e){
			System.out.println(e);
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}catch(RuntimeException e){
			System.out.println(e);
		}catch(Exception e){
			System.out.println(e);
		}
	  }
  }
