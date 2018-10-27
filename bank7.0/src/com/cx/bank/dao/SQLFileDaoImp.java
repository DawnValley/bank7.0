
package com.cx.bank.dao;

import java.util.*;
import java.io.*;
import java.net.URLDecoder;
import java.sql.*;

import javax.annotation.Resource;

import com.cx.bank.util.*;
import com.cx.bank.manager.LogManager;
import com.cx.bank.model.*;

/**
 * <b>数据库和文件dao</b>
 * 
 * @author RaoGang
 * @version 7.0 2018/10/08
 * @see com.cx.bank.util.MD5
 * @see com.cx.bank.model.UserBean
 */
public class SQLFileDaoImp implements BankDaoInterface {
    
    private LogManager logManager = null;
	private MD5 md5 = new MD5();// 私有加密类对象属性
	//mybatis的持久层对象，通过注解注入
	@Resource(name="mapper")
	private OperationMapper mapper;
	//取得存储文件的路径
	private String surl = Thread.currentThread().getContextClassLoader().getResource("../").toString().substring(6)
			+ "properties";
	private String purl;

	// 构造方法
	public SQLFileDaoImp() throws UnsupportedEncodingException {
	    //将路径转换成UTF-8编码格式
		purl = URLDecoder.decode(surl, "UTF-8");
	}
	/**
     * 设置logManager属性
     * @param logManager
     */
    public void setLogManager(LogManager logManager){
        this.logManager = logManager;
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

		if (f.exists()) {
		    throw new BankPublicException("用户名‘"+username+"’已存在，请重新注册！");
		} else {
			// 文件存储
			FileInputStream in = new FileInputStream(purl + "\\system\\Bank.properties");
			prop.load(in);// 把外存模板文件加载到内存
			in.close();
			FileOutputStream out = new FileOutputStream(f);// 内存与外存的通道
			prop.setProperty("username", username);// 设置姓名
			prop.setProperty("password", password);// 设置密码
			prop.store(out, purl + "\\" + username + ".properties");// 将内存文件存储到外存
			out.close();

			// 数据库存储
			//存储用户
			UserBean ub = user;
			ub.setMoney(10);
			ub.setConditions("activation");
			ub.setUdate(new Timestamp(System.currentTimeMillis()));
			mapper.addUser(ub);
			//日志存储
			LogBean log = new LogBean();
			log.setUserName(ub.getUsername());
            log.setLogType("register");
            log.setLogDetail("用户‘"+ub.getUsername()+"’注册账户成功！");
            log.setLogTime(new Timestamp(System.currentTimeMillis()));
            logManager.storageLog(log);

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
        boolean existFlag = false;
        final String username = user.getUsername();
        String pwd = user.getPassword();
        String password = md5.getMD5(pwd);
        String role = user.getRole();
        
        Properties prop = null;
        File f=null;
        
        //日志存储
        LogBean log = new LogBean();
        log.setUserName(user.getUsername());
        log.setLogTime(new Timestamp(System.currentTimeMillis()));
        /**
         * 如果是用户就到数据库查找用户信息，如果存在存在标记为existFlag设为true
         * 如果是管理员则不查找数据库，existFlag直接设为true
         */
        if ("1".equals(role)) {
            
            // 数据库操作
            UserBean ub = null;
            ub = mapper.selectUserByName(username);
            // 文件操作
            prop = new Properties();
            f = new File(purl + "\\" + username + ".properties");
            if (ub != null) {
                existFlag = true;
            }
            log.setLogType("User login");
            log.setLogDetail("用户‘"+username+"’登录银行系统！");
        } else {
            
            existFlag = true;
            // 文件操作
            String url = Thread.currentThread().getContextClassLoader().getResource("../").toString().substring(6)
                    + "AdministratorProperties";
            String turl = URLDecoder.decode(url, "UTF-8");
            prop = new Properties();
            f = new File(turl + "\\" + username + ".properties");
            log.setLogType("Administrator login");
            log.setLogDetail("管理员‘"+username+"’登录银行系统！");
        }
        //如果两个标记位都为true，登录成功，否则抛出用户不存在异常
        if (f.exists() == true && existFlag) {

            FileInputStream in = new FileInputStream(purl + "\\" + username + ".properties");
            prop.load(in);
            in.close();
            if (prop.getProperty("password").equals(password)) {
                logManager.storageLog(log);
                flag = true;
            } else {
                throw new BankPublicException("密码错误，请重新登录！");
            }
        } else {
            throw new BankPublicException("用户不存在，请重新登录！");
        }

        return flag;
    }
}
