
package com.cx.bank.dao;

import java.util.*;
import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Date;

import org.hibernate.Session;

import com.cx.bank.util.*;
import com.cx.bank.mode1.*;
import com.cx.bank.filter.HibernateFilter;

/**
 * <b>数据库和文件dao</b>
 * 
 * @author RG
 * @version 4.0 2018/09/19
 * @see com.cx.bank.util.MD5
 * @see com.cx.bank.mode1.UserBean
 */
public class SQLFileDaoImp implements BankDaoInterface {
	private MD5 md5 = new MD5();// 私有加密类对象属性
	private JDBCManager connPools;
	private String name;
	private String surl = Thread.currentThread().getContextClassLoader().getResource("../").toString().substring(6)
			+ "properties";
	private String purl;

	// 构造方法
	public SQLFileDaoImp() throws UnsupportedEncodingException {
		purl = URLDecoder.decode(surl, "UTF-8");
		name = "mysql";
		connPools = new JDBCManager();
		connPools.createConnPool(name);
	}

	/**
	 * 注册
	 * 
	 * @param user
	 *            用户信息
	 * @throws IOException
	 * @throws SQLException,
	 *             Exception
	 */
	public boolean register(ValueObjectBean vob) throws IOException, SQLException, Exception {
		String username = vob.getUsername();// 拿到模型层用户名
		String pwd = vob.getPassword();// 拿到模型层用户密码
		String password = md5.getMD5(pwd);// 进行MD5加密
		Properties prop = new Properties();
		File f = new File(purl + "\\" + username + ".properties");// 创建文件

		if (f.exists()) {
			//System.out.println("SQLFileDaoImp.register()：用户已存在，请重新注册！");
			throw new BankPublicException("bank.java.error.register.exist", username);
		} else {
			// 文件存储
			FileInputStream in = new FileInputStream(purl + "\\Bank.properties");
			prop.load(in);// 把外存模板文件加载到内存
			in.close();
			FileOutputStream out = new FileOutputStream(f);// 内存与外存的通道
			prop.setProperty("username", username);// 设置姓名
			prop.setProperty("password", password);// 设置密码
			prop.store(out, purl + "\\" + username + ".properties");// 将内存文件存储到外存
			out.close();

			// 数据库存储
			Session session = HibernateFilter.getSession();//获取和当前线程绑定的session
			session.beginTransaction();
			UserBean ub = new UserBean();
			ub.setUsername(username);
			ub.setPassword(password);
			ub.setMoney(10);
			ub.setUdate(new Timestamp(new Date().getTime()));
			session.save(ub);
			session.getTransaction().commit();

			//System.out.println("SQLFileDaoImp.register()：注册成功！");
			return true;
		}
	}

	/**
	 * 登录
	 * 
	 * @param user
	 *            用户信息
	 * @throws SQLException
	 * @throws FileNotFoundException,Exception
	 */
	public boolean login(ValueObjectBean vob) throws SQLException, FileNotFoundException, Exception {
		boolean flag = false;
		String username = vob.getUsername();
		String pwd = vob.getPassword();
		String password = md5.getMD5(pwd);
		// 数据库操作
		
		Session session = HibernateFilter.getSession();//获取和当前线程绑定的session
		UserBean ub = (UserBean)session.createQuery("FROM UserBean u WHERE u.username = ?")
		        .setParameter(0, username).iterate().next();
		//System.out.println("ub= "+ub);
		//System.out.println("ub.id="+ub.getId()+"ub.username"+ub.getUsername());
		// 文件操作
		Properties prop = new Properties();
		File f = new File(purl + "\\" + username + ".properties");
		if (f.exists() == true && ub != null) {
			
			FileInputStream in = new FileInputStream(purl + "\\" + username + ".properties");
			prop.load(in);
			in.close();
			if (prop.getProperty("password").equals(password)) {
				
				flag = true;
			} else {
				// System.out.println("SQLFileDaoImp.login()：密码错误，请重新登录！");
				throw new BankPublicException("bank.java.error.login.password_error");
			}
		} else {
			// System.out.println("SQLFileDaoImp.login()：用户不存在，请重新登录！");
			throw new BankPublicException("bank.java.error.login.no_exist");
		}
		
		return flag;
	}
}
