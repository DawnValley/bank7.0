
package com.cx.bank.dao;

import java.util.*;
import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cx.bank.util.*;
import com.cx.bank.mode1.*;
import com.cx.bank.dao.*;

/**
 * <b>数据库和文件dao</b>
 * 
 * @author RG
 * @version 2.0 2018/08/19 23:01:52
 * @see com.cx.bank.util.MD5
 * @see com.cx.bank.mode1.UserBean
 * @see com.cx.bank.mode1.MoneyBean
 */
public class SQLFileDaoImp implements BankDaoInterface {
	private MD5 md5 = new MD5();// 私有加密类对象属性
	private JDBCManager connPools;
	private String name;
	private String sql;
	private String surl = Thread.currentThread().getContextClassLoader().getResource("../").toString().substring(6)
			+ "properties";
	private String purl;

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
	public boolean register(UserBean user) throws IOException, SQLException, Exception {
		String username = user.getUsername();// 拿到模型层用户名
		String pwd = user.getPassword();// 拿到模型层用户密码
		String password = md5.getMD5(pwd);// 进行MD5加密
		Properties prop = new Properties();
		File f = new File(purl + "\\" + username + ".properties");// 创建文件

		Connection conn = connPools.getConnection(name);
		// Statement stat = conn.createStatement();
		PreparedStatement pstmt = null;

		if (f.exists()) {
			System.out.println("用户已存在，请重新注册！");
			return false;
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
			sql = "INSERT INTO users (username,password,money,udate) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, "10");
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
			System.out.println("数据库注册");
			// stat.executeUpdate(sql);
			// stat.close();
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("数据库注册成功");
			connPools.releaseConnection(name, conn);
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
	public boolean login(UserBean user) throws SQLException, FileNotFoundException, Exception {
		boolean flag = false;
		String username = user.getUsername();
		String pwd = user.getPassword();
		String password = md5.getMD5(pwd);
		// 数据库操作
		sql = "SELECT money FROM users WHERE username='" + username + "';";
		Connection conn = connPools.getConnection(name);
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);

		// 文件操作
		Properties prop = new Properties();
		File f = new File(purl + "\\" + username + ".properties");
		if (f.exists() == true && rs.next()) {
			// FileInputStream in = new
			// FileInputStream(".\\"+name+".properties");
			FileInputStream in = new FileInputStream(purl + "\\" + username + ".properties");
			prop.load(in);
			in.close();
			if (prop.getProperty("password").equals(password)) {
				Double money = rs.getDouble("money");
				MoneyBean.getMoneyBean().setMoney(money);// 将金额放入MoneyBean中
				System.out.println("登录成功！！！");
				flag = true;
			} else {
				System.out.println("密码错误，请重新登录！");
				flag = false;
			}
		} else {
			System.out.println("用户不存在，请重新登录！");
			flag = false;
		}
		rs.close();
		stat.close();
		connPools.releaseConnection(name, conn);
		return flag;
	}
	/**
	 * 查询交易记录
	 * @param myName 当前账号名字
	 * @return 交易记录的List
	 */
	public List transactionRecord(String myName) throws SQLException, Exception {
		boolean flag = false;
		HashMap map = null;
		List list = new ArrayList();
		sql = "SELECT bid,myname,toname,bmoney,bdate FROM business WHERE myname='" + myName + "' OR toname='" + myName + "';";
		Connection conn = connPools.getConnection(name);
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		while (rs.next()) {
			map = new HashMap();
			map.put("bId", (String) rs.getString("bid"));
			map.put("myName", rs.getString("myname"));
			map.put("toName", rs.getString("toname"));
			map.put("bMoney",  rs.getDouble("bmoney"));
			map.put("bDate", (java.util.Date)rs.getTimestamp("bdate"));
			list.add(map);
		} 
		if(list.size() == 0)
		{
			System.out.println("您当前无交易记录！");
			map = new HashMap();
			map.put("message","您当前无交易记录！");
			list.add(map);
		}
		rs.close();
		stat.close();
		connPools.releaseConnection(name, conn);
		return list;
	}

	/**
	 * 转账
	 * 
	 * @param toName
	 *            要转入的账号用户名
	 * @param money
	 *            要转入的钱
	 * @throws SQLException
	 */
	public boolean transfer(String myName, String toName, double money) throws SQLException, Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		sql = "SELECT username,money FROM users WHERE username='" + toName + "';";
		Connection conn = connPools.getConnection(name);
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		// System.out.println("sql转账");
		if (rs.next()) {// System.out.println("sql存在");
			double mon = rs.getDouble("money");
			mon = mon + money;
			sql = "UPDATE users SET money=" + mon + " WHERE username='" + toName + "';";
			// System.out.println(sql);
			stat.executeUpdate(sql);
			sql = "INSERT INTO business (myname,toname,bmoney,bdate) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myName);
			pstmt.setString(2, toName);
			pstmt.setString(3, Double.toString(money));
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();
			/*
			//只能查转出，存两遍太多余
			//存储转账记录--转出方
			sql = "INSERT INTO business (myname,toname,bmoney,bdate) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myName);
			pstmt.setString(2, toName);
			pstmt.setString(3, Double.toString(money));
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();
			
			//存储转账记录--转入方
			sql = "INSERT INTO business (myname,toname,bmoney,bdate) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, toName);
			pstmt.setString(2, myName);
			pstmt.setString(3, Double.toString(money));
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();*/
			flag = true;
		} else {
			flag = false;
		}
		// System.out.println("sql "+flag);
		rs.close();
		stat.close();
		pstmt.close();
		connPools.releaseConnection(name, conn);
		return flag;
	}

	/**
	 * 存储方法
	 * 
	 * @param user
	 *            用户信息
	 * @throws SQLException,Exception
	 */
	public boolean addBank(UserBean user) throws SQLException, Exception {
		Properties prop = new Properties();
		String username = user.getUsername();
		File f = new File(purl + "\\" + username + ".properties");// 创建文件
		double mon = MoneyBean.getMoneyBean().getMoney();
		sql = "UPDATE users SET money=" + mon + " WHERE username='" + username + "';";

		if (f.exists()) {
			Connection conn = connPools.getConnection(name);
			Statement stat = conn.createStatement();
			stat.executeUpdate(sql);
			stat.close();
			connPools.releaseConnection(name, conn);
			return true;
		} else {
			System.out.println("尚未注册或登录，用户不存在！");
			return false;
		}
	}

	/**
	 * 获取余额
	 * 
	 * @param user
	 *            用户信息
	 * @return money 金额或-1
	 */
	public double getBalance(UserBean user) throws Exception {
		return 0;
	}
}
