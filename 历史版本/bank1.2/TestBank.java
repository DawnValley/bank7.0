  

  package com.cx.bank.test;
  import com.cx.bank.manager.Manager;
  import com.cx.bank.manager.ManagerImp1;
  import java.util.*;
  import com.cx.bank.util.*;
  /**
  *<b>测试层TestBank</b>
  *@author RG
  *@version 1.2 2018/06/21
  */
  public class TestBank 
  {
      public void printMenu()//银行测试系统菜单
	  {
		  System.out.println("**************************银行测试系统1.2**************************");
          System.out.println("                            1.查询余额                             ");
		  System.out.println("                            2.取款                                 ");
		  System.out.println("                            3.存款                                 ");
		  System.out.println("                            4.退出系统                             ");
		  System.out.println("******************************欢迎使用*****************************");
	  }
	  /**
	  *<b>银行测试系统</b>
	  *@see com.cx.bank.manager.ManagerImp1
	  *对输入的数据和业务层进行测试
	  */
	  public void bankSystemTest() throws Exception
	  {
		  Scanner sc = new Scanner(System.in);//接收输入数据的对象
		  int n = 0;//操作数字
		  Manager mi = new ManagerImp1();//业务层ManagerImpl对象赋给业务层接口Manager

		  while(true)//当为true时运行银行测试系统
		  {
			  this.printMenu();
			  System.out.print("请输入操作数字(整数1~4)：");
			  //当输入不是整数时出错：Exception in thread "main" java.util.InputMismatchException
			  //未解决，解决思想:采用抛出异常的处理方式
			  n = sc.nextInt();

			  if(n == 1)//查询
			  {
				  System.out.println("您正在进行查询服务，您的查询结果如下：");
				  mi.inquiry();
			  }
			  else if(n == 2)//取款
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
				  }
				  finally{
		              mi.inquiry();
		          }
			  }
			  else if(n == 3)//存款
			  {
				  System.out.print("您正在进行存款服务，请输入存款金额：");
				  double d = sc.nextDouble();

				  try{
				      mi.deposit(d);
			      }catch(InvalidDepositException e){//捕获存款额为负数异常
					  System.out.println(e);
				  }
				  finally{
		              mi.inquiry();
		          }
			  }
			  else if(n == 4)//退出
			  {
				  System.out.println("****************您正在退出银行测试系统，谢谢您的使用***************");
				  mi.secede();
			  }
			  else//操作数输入错误
			  {
				  System.out.println("输入错误！请输入正确的操作数字（整数1~4）！");
			  }
		  }
	  }
	  /**
	  *<b>主方法</b>
	  *@param args String[] 从命令行中带入的字符串
	  *@see TestBank#bankSystemTest
	  */
	  public static void main(String[] args) throws Exception
	  {
		  TestBank tb = new TestBank();
		  tb.bankSystemTest();//对银行系统进行测试
	  }
  }
