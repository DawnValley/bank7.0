
   package com.cx.bank.dao;//持久层

   import java.util.*;
   import java.io.*;
   import com.cx.bank.mode1.*;
   import com.cx.bank.util.MD5;

   
   /**定义FileDanImpl
    *采用了单例模式的饥汉模式
	*@author RG
	*@version 1.3 2018/07
	*/
   public class FileDaoImp1 implements BankDaoInterface
   {
	   private MD5 md5=new MD5();//私有加密类对象属性
	   private Properties prop =new Properties();//私有文件类对象属性
	   private static FileDaoImp1 instance=new  FileDaoImp1();//单例属性
      

	   //私有构造方法
	   private FileDaoImp1()
	   {   
	   }

       
	   //得到FileDaoImpl类对象
	   public static FileDaoImp1 getInstance()
	   {
	        return instance;
	   }


       /**
	    *注册
	    *@param user 用户信息
	    */
	   public boolean register(UserBean user)
	   {
           String name=user.getUserName();//拿到模型层用户名
		   String  pwd=user.getPwd();//拿到模型层用户密码
		   String password=md5.getMD5(pwd);//进行MD5加密
		   File f=new File(name+".properties");//创建文件
		   if(f.exists()){
		       System.out.println("用户已存在");
			   return false;
		   }else{
               if("".equals(name)||"".equals(password)){
			   System.out.println("用户名和密码不能为空!");
			   return false;
		   }
           try{            
			   FileInputStream  in  = new FileInputStream (".\\Bank.properties");
			   prop.load(in);//把外存文件加载到内存
			   in.close();
		   }catch(IOException e){
			   System.out.println(e);
		   }
		   try{
			   FileOutputStream out = new FileOutputStream(f);//内存与外存的通道
			   prop.setProperty("userName",name);//设置姓名
			   prop.setProperty("password",password);//设置密码
			   prop.store(out,".\\"+name+".properties");//将内存文件存储到外存
			   out.close();
		   }catch(FileNotFoundException e){
			   System.out.println(e);
		   }catch(IOException e){
		       System.out.println(e);
		   } 
			   return true;
	       } 	
        }

        /**
	    *登录
		*@param user 用户信息
	    */
		public boolean login(UserBean user){
            String name=user.getUserName();//拿到模型层用户名
		    String  pwd=user.getPwd();//拿到模型层用户密码
		    String password=md5.getMD5(pwd);//进行MD5加密
			File f=new File(name+".properties");
		    if(f.exists()==true){
				try{            
			     FileInputStream  in  = new FileInputStream (".\\"+name+".properties");
			     prop.load(in);
			     in.close();
			    }catch(IOException e){
			       System.out.println(e);
			    }
			    if(password.equals(prop.getProperty("password"))){
					   double money=Double.parseDouble((prop.getProperty("money")));
					   MoneyBean.getMoneyBean().setMoney(money);
					   return true;
				}else{
					   prop.getProperty("password");
					   return false;
				 }		        
			}else{
				    System.out.println("用户不存在！");
					return false;
			}				   
		}


       /**
	   *转账
	   *@param name 要转入的账号用户名
	   *@param money 要转入的钱
	   */
	   public boolean  transfer(String name,double money){
		  File f=new File(name+".properties");
		  if(f.exists()==true){//先要判断要转入的账号是否存在
              try{            
			     FileInputStream  in  = new FileInputStream (".\\"+name+".properties");
			     prop.load(in);
			     in.close();
			  }catch(IOException e){
			       System.out.println(e);
			  }
              try{
			      FileOutputStream out = new FileOutputStream(f);  
                  String proMoney=Double.parseDouble((prop.getProperty("money")))+money+"";
			      prop.setProperty("money",proMoney);
                  prop.store(out,".\\"+name+".propertise");
			      out.close();
			  }catch(IOException e){
				   
			  }    
			  return true;
		  }else{
		     System.out.println("该账户不存在！");
			 return false;
		  }	   
	   }

       

	  /**
	  *存储方法
	  *@param user 用户信息
	  */
	   public boolean addBank(UserBean user){
		   String name=user.getUserName();
		   File f=new File(name+".properties");//创建文件
		   try{            
			     FileInputStream  in  = new FileInputStream (".\\"+name+".properties");
			     prop.load(in);
			     in.close();
		   }catch(IOException e){
			       System.out.println(e);
		   } 
		   try{
				  FileOutputStream out=new FileOutputStream(f);
				  prop.setProperty("money",MoneyBean.getMoneyBean().getMoney()+"");
                  prop.store(out,".\\"+name+".propertise");
			      out.close();
		   }catch(IOException e){
				  System.out.println(e);
		   }	
		   return true;
		   
	   }


        /**
	    *获取余额
		*@param user 用户信息
		*/
		public double getBalance(UserBean user){
		     String name=user.getUserName();
			 File f=new File(name+".properties");//创建文件
		     if(f.exists()){
			    try{
				   FileInputStream in = new FileInputStream(f);
				   prop.load(in);
				   in.close();
				}catch(IOException e){
				     System.out.println(e);
				}
                double money=Double.parseDouble(prop.getProperty("money"));
				return money;
			 }else{
			    return -1;//文件不存在返回-1
			 }			
		}
   }
        


		
