
package com.cx.bank.manager;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Timestamp;

import com.cx.bank.mode1.*;
import com.cx.bank.util.*;
import com.cx.bank.dao.*;
import com.cx.bank.factory.*;
import com.cx.bank.filter.HibernateFilter;

/**
 * <b>业务层ManagerImp1类</b>
 * 
 * @author RG
 * @version 2.0 2018/08/26
 * @see com.cx.bank.mode1.MoneyBean
 * @see com.cx.bank.factory.UserDaoFactory
 */
public class ManagerImp1 implements Manager {

    private BankDaoInterface userDao = null;// 持久层接口变量
    private static ManagerImp1 instance = null;// 创建业务层对象

    /* 私有构造方法 */
    private ManagerImp1() throws Exception {
        UserDaoFactory udf = UserDaoFactory.getInstance();// 通过单例创建持久层的工厂类对象
        userDao = udf.createUserDao();// 通过工厂类对象拿到持久层类对象
    }

    /**
     * 拿到对象地址的方法-饱汉模式 同步锁，保证线程安全
     */
    public static synchronized ManagerImp1 getInstance() throws Exception {
        if (instance == null) {
            instance = new ManagerImp1();
        }
        return instance;
    }

    /**
     * 实现注册方法
     * 
     * @param userName
     *            用户名
     * @param userPwd
     *            用户密码
     */
    public boolean register(ValueObjectBean lb) throws Exception {

        if (userDao.register(lb) == true)
            return true;
        else
            return false;
    }

    /**
     * 实现登录方法
     * 
     * @param userName
     *            用户名
     * @param userPwd
     *            用户密码
     */
    public boolean login(ValueObjectBean lb) throws Exception {

        if (userDao.login(lb) == true)
            return true;
        else
            return false;
    }

    /**
     * 查询用户信息
     * 
     * @param userName
     *            用户名
     * @return 用户信息
     */
    public UserBean getAccountInformation(String userName) throws Exception {
    //可优化？
        Session session = HibernateFilter.getSession();
        Iterator iter = session.createQuery("SELECT u.id,u.money FROM UserBean u WHERE u.username=?")
                .setParameter(0, userName).list().iterator();
        //Itertor iter = list.iterator();
        Object[] obj = (Object[]) iter.next();
        UserBean ub = new UserBean();
        ub.setId((Integer)obj[0]);
        ub.setMoney((Double)obj[1]);
        
        return ub;
    }

    /**
     * 查询记录数
     * 
     * @param myName
     * @param queryStr
     * @return
     */
    public int getTotalRecords(int id, String queryStr) {

        Long count = null;
        Session session = HibernateFilter.getSession();

        if (queryStr != null && queryStr.trim().length() != 0) {
            count = (Long) session
                    .createQuery("SELECT COUNT(*) FROM TransactionRecordBean trb WHERE trb.users.id=? AND (trb.toName LIKE ? OR trb.transactionType LIKE ?)")
                    .setParameter(0, id).setParameter(1, "%" + queryStr + "%").setParameter(2, "%" + queryStr + "%").uniqueResult();
        } else {
            count = (Long) session.createQuery("SELECT COUNT(*) FROM TransactionRecordBean trb WHERE trb.users.id=?")
                    .setParameter(0, id).uniqueResult();
        }
        //System.out.println("Long "+count);
        //System.out.println("int "+count.intValue());
        return count.intValue();

    }

    // 查询交易记录
    public PageModel getTransactionRecord(ValueObjectBean vob) throws Exception {
        String myName = vob.getUsername();
        String queryStr = vob.getQueryStr();
        int pageNo = vob.getPageNo();
        int pageSize = vob.getPageSize();
        PageModel pageModel = new PageModel();
        
        int userId = (Integer) this.getAccountInformation(myName).getId();
        Session session = HibernateFilter.getSession();
        Query query = null;
        List list = null;

        if (queryStr != null && queryStr.trim().length() != 0) {
            query = session
                    .createQuery(
                            "FROM TransactionRecordBean trb WHERE trb.users.id=? AND (trb.toName LIKE ? OR trb.transactionType LIKE ?) ORDER BY trb.bId")
                    .setParameter(0, userId).setParameter(1, "%" + queryStr + "%").setParameter(2, "%" + queryStr + "%");
        } else {
            query = session.createQuery("FROM TransactionRecordBean trb WHERE trb.users.id=? ORDER BY trb.bId")
                    .setParameter(0, userId);
        }
        list = query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
        
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(getTotalRecords(userId, queryStr));

        return pageModel;
    }

    /**
     * <b>用户取款</b>
     * 
     * @param m
     *            取款数额
     */
    public int withDrawals(ValueObjectBean vob) throws Exception {

        String username = vob.getUsername();
        double m = vob.getToMoney();
        int flag = 0;
        double money = 0;// 查询金额
        Session session = null;

        money = this.getAccountInformation(username).getMoney();
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
            session = HibernateFilter.getSession();
            session.beginTransaction();
            Query query = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?").setParameter(0, money)
                    .setParameter(1, username);
            flag = query.executeUpdate();
            session.getTransaction().commit();

            // 存储交易记录
            this.StoreTransactionRecords("withDrawals", username, username, m);

        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }

        return flag;
    }

    /**
     * <b>用户存款</b>
     * 
     * @param m
     *            存款数额
     */
    public int deposit(ValueObjectBean vob) throws Exception {
        String username = vob.getUsername();
        double m = vob.getToMoney();
        int flag = 0;
        double money = 0;
        Session session = null;

        // 查询金额
        money = this.getAccountInformation(username).getMoney();

        if (m <= 0)// 当取款数额不能为非正数数，取款失败，否则输出取款金额及当前账户金额
        {
            throw new InvalidDepositException("bank.java.error.withDrawals.Non-positive_number");// 您输入的存款金额必须大于0！存款失败！
        }

        money = money + m;
        try {
            session = HibernateFilter.getSession();
            session.beginTransaction();
            Query query = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?").setParameter(0, money)
                    .setParameter(1, username);
            flag = query.executeUpdate();
            session.getTransaction().commit();

            // 存储交易记录
            this.StoreTransactionRecords("deposit", username, username, m);

        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        return flag;
    }

    /**
     * 转账方法
     * 
     * @param username
     *            对方用户名
     * @param money
     *            转账金额
     */
    public int transfer(ValueObjectBean vob) throws Exception {
        String username = vob.getUsername();
        String toName = vob.getToName();
        double money = vob.getToMoney();
        int flag = 0;
        double m = 0;
        Session session = null;

        m = this.getAccountInformation(username).getMoney();//取得自己的余额

        if (money <= 0) {
            // System.out.println("转账金额必须大于0！");
            throw new BankPublicException("bank.java.error.transfer.Non-positive_number");
        }
        if (money > m) {
            // System.out.println("转账金额不能大于您的余额！");
            throw new BankPublicException("bank.java.error.transfer.exceed");
        }

        try {
            session = HibernateFilter.getSession();
            session.beginTransaction();

            Query query1 = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?")
                    .setParameter(0, m - money).setParameter(1, username);
            flag = query1.executeUpdate();
            m = this.getAccountInformation(toName).getMoney();//取得对方的余额
            Query query2 = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?")
                    .setParameter(0, m + money).setParameter(1, toName);
            query2.executeUpdate();
            session.getTransaction().commit();

            // 存储交易记录
            this.StoreTransactionRecords("payment", username, toName, money);
            this.StoreTransactionRecords("proceeds", toName, username, money);

        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }

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
        UserBean ub = null;
        Session session = null;
        TransactionRecordBean trb = null;

        try {
            ub = this.getAccountInformation(myName);

            session = HibernateFilter.getSession();
            session.beginTransaction();
            trb = new TransactionRecordBean();
            trb.setUsers(ub);
            trb.setToName(toName);
            trb.setbMoney(money);
            trb.setTransactionType(type);
            trb.setbDate(new Timestamp(new Date().getTime()));
            session.save(trb);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        return true;
    }

    /**
     * <b>退出系统</b>
     * 
     * @Deprecated 不建议使用
     */
    public void secede() throws Exception {
        // userDao.addBank(user);// 存储用户数据
        System.out.println("系统已退出！");
        // System.exit(0);//会关闭tomcat服务器
    }
}
