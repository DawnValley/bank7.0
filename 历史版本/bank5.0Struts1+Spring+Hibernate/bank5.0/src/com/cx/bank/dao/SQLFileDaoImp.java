
package com.cx.bank.dao;

import java.util.*;
import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cx.bank.util.*;
import com.cx.bank.mode1.*;
import com.cx.bank.manager.LogManager;

/**
 * <b>数据库和文件dao</b>
 * 
 * @author RG
 * @version 5.0 2018/09/25
 * @see com.cx.bank.util.MD5
 * @see com.cx.bank.mode1.UserBean
 */
public class SQLFileDaoImp extends HibernateDaoSupport implements BankDaoInterface {
    
    private LogManager logManager = null;
	private MD5 md5 = new MD5();// 私有加密类对象属性
	//private JDBCManager connPools;
	private String name;
	private String surl = Thread.currentThread().getContextClassLoader().getResource("../").toString().substring(6)
			+ "properties";
	private String purl;

	// 构造方法
	public SQLFileDaoImp() throws UnsupportedEncodingException {
		purl = URLDecoder.decode(surl, "UTF-8");
		name = "mysql";
		//connPools = new JDBCManager();
		//connPools.createConnPool(name);
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
			//Session session = HibernateFilter.getSession();//获取和当前线程绑定的session
			//session.beginTransaction();
			UserBean ub = new UserBean();
			ub.setUsername(username);
			ub.setPassword(password);
			ub.setMoney(10);
			ub.setConditions("activation");
			ub.setUdate(new Timestamp(new Date().getTime()));
			//session.save(ub);
			//session.getTransaction().commit();
			this.getHibernateTemplate().save(ub);
			
			/*模板
			//日志存储
			LogBean log = new LogBean();
			log.setLogType("");
			log.setLogDetail("");
			log.setLogTime(new Timestamp(new Date().getTime()));
			logManager.storageLog(log);
			*/
			//日志存储
			LogBean log = new LogBean();
			log.setUserName(ub.getUsername());
            log.setLogType("register");
            log.setLogDetail("用户‘"+ub.getUsername()+"’注册账户成功！");
            log.setLogTime(new Timestamp(new Date().getTime()));
            logManager.storageLog(log);
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
        boolean existFlag = false;
        final String username = vob.getUsername();
        String pwd = vob.getPassword();
        String password = md5.getMD5(pwd);
        String role = vob.getRole();
        
        Properties prop = null;
        File f=null;
        
        //日志存储
        LogBean log = new LogBean();
        log.setUserName(vob.getUsername());
        
        
        log.setLogTime(new Timestamp(new Date().getTime()));
        /**
         * 如果是用户就到数据库查找用户信息，如果存在存在标记为existFlag设为true
         * 如果是管理员则不查找数据库，existFlag直接设为true
         */
        if ("1".equals(role)) {
            // 数据库操作
            UserBean ub = (UserBean) this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    return session.createQuery("FROM UserBean u WHERE u.username = ?").setParameter(0, username).list();
                }
            }).get(0);
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
       
        if (f.exists() == true && existFlag) {

            FileInputStream in = new FileInputStream(purl + "\\" + username + ".properties");
            prop.load(in);
            in.close();
            if (prop.getProperty("password").equals(password)) {
                
                logManager.storageLog(log);
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
