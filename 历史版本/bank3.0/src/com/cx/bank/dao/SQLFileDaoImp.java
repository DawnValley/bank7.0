
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
	public boolean register(UserBean user) throws IOException, SQLException, Exception {
		String username = user.getUsername();// 拿到模型层用户名
		String pwd = user.getPassword();// 拿到模型层用户密码
		String password = md5.getMD5(pwd);// 进行MD5加密
		Properties prop = new Properties();
		File f = new File(purl + "\\" + username + ".properties");// 创建文件

		Connection conn = connPools.getConnection(name);
		PreparedStatement pstmt = null;

		if (f.exists()) {
			System.out.println("SQLFileDaoImp.register()：用户已存在，请重新注册！");
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
			sql = "INSERT INTO users (username,password,money,udate) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, "10");
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();
			pstmt.close();
			connPools.releaseConnection(name, conn);

			System.out.println("SQLFileDaoImp.register()：注册成功！");
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
				// 考虑撤掉，原来moneybean是为了使金额同步，现在用数据库完全不必
				Double money = rs.getDouble("money");
				MoneyBean.getMoneyBean().setMoney(money);// 将金额放入MoneyBean中

				// System.out.println("SQLFileDaoImp.login()：登录成功！！！");
				flag = true;
			} else {
				// System.out.println("SQLFileDaoImp.login()：密码错误，请重新登录！");
				throw new BankPublicException("bank.java.error.login.password_error");
			}
		} else {
			// System.out.println("SQLFileDaoImp.login()：用户不存在，请重新登录！");
			throw new BankPublicException("bank.java.error.login.no_exist");
		}
		rs.close();
		stat.close();
		connPools.releaseConnection(name, conn);
		return flag;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户信息map
	 */
	public Map getAccountInformation(String userName) throws Exception {

		Map map = null;
		Connection conn = connPools.getConnection(name);// 通过数据库名获取连接
		Statement stat = conn.createStatement();
		// 查询金额
		sql = "SELECT id,money FROM users WHERE username='" + userName + "'";
		ResultSet rs = stat.executeQuery(sql);
		while (rs.next()) {
			map = new HashMap();
			map.put("userId", rs.getInt("id"));
			map.put("money", rs.getDouble("money"));
		}
		rs.close();
		stat.close();
		connPools.releaseConnection(name, conn);
		return map;
	}

	/**
	 * 查询交易记录
	 * 
	 * @param myName
	 *            当前账号名字
	 * @return 交易记录的PageModel对象
	 */
	public PageModel getTransactionRecord(String myName, String queryStr, int pageNo, int pageSize)
			throws SQLException, Exception {
		// boolean flag = false;
		TransactionRecordBean trb = null;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		List list = new ArrayList();
		PageModel pageModel = new PageModel();
		try {
			int userId = (Integer) this.getAccountInformation(myName).get("userId");
			sql = "SELECT * FROM business WHERE userid='" + userId + "'";
			if (queryStr != null && queryStr.trim().length() != 0) {
				sql += " AND (toName LIKE '%" + queryStr + "%' OR bMoney LIKE '%" + queryStr;
				sql += "%' OR transactionType LIKE '%" + queryStr + "%')";
			}
			sql += " ORDER BY bId LIMIT " + ((pageNo - 1) * pageSize) + "," + pageSize;
			conn = connPools.getConnection(name);
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				trb = new TransactionRecordBean();// 忘记给其设值，空指针异常，并不是封装类的锅

				trb.setbId(Integer.valueOf(rs.getString("bId")));
				trb.setUserId(Integer.valueOf(rs.getString("userId")));
				trb.setbId(Integer.valueOf(rs.getInt("bId")));
				trb.setUserId(Integer.valueOf(rs.getInt("userId")));
				trb.setMyName(rs.getString("myName"));
				trb.setToName(rs.getString("toName"));
				trb.setbMoney(Double.valueOf(rs.getString("bMoney")));
				trb.setTransactionType(rs.getString("transactionType"));
				trb.setbDate((java.util.Date) rs.getTimestamp("bDate"));
				list.add(trb);
			}
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(list);
			pageModel.setTotalRecords(getTotalRecords(myName, conn, queryStr));
		} finally {
			/*
			 * //可在struts中，采用国际化方法，抛异常处理提示信息 if (list.size() == 0) {
			 * //System.out.println("您当前无交易记录！"); //map = new HashMap();
			 * //map.put("message", "您当前无交易记录！"); list.add(map); }
			 */
			rs.close();
			stat.close();
			connPools.releaseConnection(name, conn);
		}
		return pageModel;
	}

	/**
	 * 查询记录数
	 * 
	 * @param Connection
	 *            数据库连接
	 * @param String
	 *            查询条件
	 * @return 符合条件的记录数
	 * @throws Exception
	 */
	public int getTotalRecords(String myName, Connection conn, String queryStr) throws Exception {

		int userId = (Integer) this.getAccountInformation(myName).get("userId");
		String s = "SELECT COUNT(*) FROM business WHERE userid='" + userId + "'";

		if (queryStr != null && queryStr.trim().length() != 0) {
			s += " AND (toName LIKE '%" + queryStr + "%' OR bMoney LIKE '%" + queryStr;
			s += "%' OR transactionType LIKE '%" + queryStr + "%')";
		}

		int totalRecords = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(s);
			if (rs.next()) {
				totalRecords = rs.getInt(1);// 拿到总记录数
			}
		} finally {
			rs.close();
			stmt.close();
		}
		// System.out.println("GradeDao4MySqlImpl 总记录数："+totalRecords);
		return totalRecords;
	}

	// 用户取款
	public int withDrawals(String username, double m) throws Exception {
		int flag = 0;
		double money = 0;
		Connection conn = null;
		Statement stat = null;
		// 查询金额
		money = (Double) this.getAccountInformation(username).get("money");

		if (m <= 0)// 当取款数额为非正数数，取款失败，否则输出取款金额及当前账户金额
		{
			throw new InvalidWithDrawalstException("bank.java.error.withDrawals.Non-positive_number");// 您输入的取款金额必须大于0！取款失败！
																										// 取款数额为负数异常
		}
		if (m > money)// 当取款数额大于余额，取款失败
		{
			throw new AccountOverDrawnException("bank.java.error.withDrawals.exceed");// 您的账户余额不足！取款失败！
																						// 取款数额大于余额异常
		}
		money = money - m;
		try {
			conn = connPools.getConnection(name);// 通过数据库名获取连接
			stat = conn.createStatement();
			// 取款
			sql = "UPDATE users SET money=" + money + " WHERE username='" + username + "';";
			flag = stat.executeUpdate(sql);

			// 存储交易记录
			this.StoreTransactionRecords("withDrawals", username, username, m);
		} finally {
			stat.close();
			connPools.releaseConnection(name, conn);
		}

		return flag;

	}

	// 用户存款
	public int deposit(String username, double m) throws Exception {
		int flag = 0;
		double money = 0;
		Connection conn = null;
		Statement stat = null;

		// 查询金额
		money = (Double) this.getAccountInformation(username).get("money");

		if (m <= 0)// 当取款数额不能为非正数数，取款失败，否则输出取款金额及当前账户金额
		{
			throw new InvalidDepositException("bank.java.error.withDrawals.Non-positive_number");// 您输入的存款金额必须大于0！存款失败！
		}

		money = money + m;
		try {
			conn = connPools.getConnection(name);// 通过数据库名获取连接
			stat = conn.createStatement();
			// 存款
			sql = "UPDATE users SET money=" + money + " WHERE username='" + username + "';";
			flag = stat.executeUpdate(sql);

			// 存储交易记录

			this.StoreTransactionRecords("deposit", username, username, m);
		} finally {
			stat.close();
			connPools.releaseConnection(name, conn);
		}
		return flag;
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
		Connection conn = null;
		Statement stat = null;

		double m = 0;
		// 取得本账户金额，并判断
		m = (Double) this.getAccountInformation(myName).get("money");
		if (money <= 0) {
			// System.out.println("转账金额必须大于0！");
			throw new BankPublicException("bank.java.error.transfer.Non-positive_number");
		}
		if (money > m) {
			// System.out.println("转账金额不能大于您的余额！");
			throw new BankPublicException("bank.java.error.transfer.exceed");
		}

		sql = "SELECT username,money FROM users WHERE username='" + toName + "';";
		conn = connPools.getConnection(name);// 通过数据库名获取连接
		stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		if (rs.next()) {
			// 更新被转账方金额
			double mon = rs.getDouble("money");
			mon = mon + money;
			sql = "UPDATE users SET money=" + mon + " WHERE username='" + toName + "';";
			stat.executeUpdate(sql);
			// 更新转账方金额
			m = m - money;
			sql = "UPDATE users SET money=" + m + " WHERE username='" + myName + "';";
			stat.executeUpdate(sql);

			// 插入转账记录
			this.StoreTransactionRecords("payment", myName, toName, money);
			this.StoreTransactionRecords("proceeds", toName, myName, money);

			flag = true;
		} else {
			// flag = false;
			// System.out.println("对方账户不存在，请核对后重新操作！转账失败！");
			rs.close();
			stat.close();
			connPools.releaseConnection(name, conn);
			throw new BankPublicException("bank.java.error.transfer.non-existent");
		}
		rs.close();
		stat.close();
		connPools.releaseConnection(name, conn);
		return flag;
	}

	/**
	 * 存储交易记录
	 * 
	 * @param myName
	 * @param toName
	 * @param money
	 * @return
	 * @throws Exception
	 */
	public boolean StoreTransactionRecords(String type, String myName, String toName, double money) throws Exception {
		Connection conn = connPools.getConnection(name);
		PreparedStatement pstmt = null;
		int userId = (Integer) this.getAccountInformation(myName).get("userId");
		sql = "INSERT INTO business (userId,myName,toName,bMoney,transactionType,bDate) VALUES(?,?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setString(2, myName);
		pstmt.setString(3, toName);
		pstmt.setString(4, Double.toString(money));
		pstmt.setString(5, type);
		pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
		pstmt.executeUpdate();

		pstmt.close();
		connPools.releaseConnection(name, conn);
		return false;
	}

}
