
package com.cx.bank.manager;

import java.util.*;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.Timestamp;

import com.cx.bank.mode1.*;
import com.cx.bank.util.*;
import com.cx.bank.dao.*;

/**
 * <b>业务层ManagerImp1类</b>
 * 
 * @author RG
 * @version 5.0 2018/09/23
 * @see com.cx.bank.mode1.MoneyBean
 * @see com.cx.bank.factory.UserDaoFactory
 */
public class ManagerImp1 extends HibernateDaoSupport implements Manager {

    private BankDaoInterface userDao = null;// 持久层接口变量
    //private static ManagerImp1 instance = null;// 创建业务层对象
    private LogManager logManager = null;

    /* 私有构造方法 */
    private ManagerImp1() throws Exception {
    }
    /*public ManagerImp1() throws Exception {
    }*/

    /**
     * 拿到对象地址的方法-饱汉模式 同步锁，保证线程安全
     */
//    public static synchronized ManagerImp1 getInstance() throws Exception {
//        if (instance == null) {
//            instance = new ManagerImp1();
//        }
//        return instance;
//    }
    /**
     * 设置userDao属性
     * @param userDao
     */
    public void setUserDao(BankDaoInterface userDao){
        this.userDao = userDao;
    }
    /**
     * 设置logManager属性
     * @param logManager
     */
    public void setLogManager(LogManager logManager){
        this.logManager = logManager;
    }
    /**
     * 实现注册方法
     * 
     * @param lb
     *            用户注册信息
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
     * @param lb
     *            用户登录信息
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
    public UserBean getAccountInformation(final String userName) throws Exception {
    //可优化？
        //System.out.println("this.getHibernateTemplate():"+this.getHibernateTemplate());
        List list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                return session.createQuery("SELECT u FROM UserBean u WHERE u.username=?")
                        .setParameter(0, userName).list();
            }
        });
        UserBean ub = (UserBean) list.get(0);
        //日志存储
        //操作太频繁，生成记录过多
        /*LogBean log = new LogBean();
        log.setUserName(userName);
        log.setLogType("User actions");
        log.setLogDetail("用户‘"+userName+"’查询了用户余额！");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);*/
        /*Object[] obj = (Object[]) iter.next();
        UserBean ub = new UserBean();
        ub.setId((Integer)obj[0]);
        ub.setMoney((Double)obj[1]);*/
        
        return ub;
    }

    /**
     * 查询记录数
     * 
     * @param id
     * @param queryStr
     * @return
     */
    public int getTotalRecords(int id,String queryStr) {

        Long count = null;
        //Session session = HibernateFilter.getSession();

        if (queryStr != null && queryStr.trim().length() != 0) {
            /*count = (Long) session
                    .createQuery("SELECT COUNT(*) FROM TransactionRecordBean trb WHERE trb.users.id=? AND (trb.toName LIKE ? OR trb.transactionType LIKE ?)")
                    .setParameter(0, id).setParameter(1, "%" + queryStr + "%").setParameter(2, "%" + queryStr + "%").uniqueResult();*/
            count = (Long) this.getHibernateTemplate()
                    .find("SELECT COUNT(*) FROM TransactionRecordBean trb WHERE trb.users.id=? AND (trb.toName LIKE ? OR trb.transactionType LIKE ?)",
                            new Object[] { id, "%" + queryStr + "%", "%" + queryStr + "%" })
                    .get(0);
        } else {
            /*count = (Long) session.createQuery("SELECT COUNT(*) FROM TransactionRecordBean trb WHERE trb.users.id=?")
                    .setParameter(0, id).uniqueResult();*/
            count = (Long) this.getHibernateTemplate()
                    .find("SELECT COUNT(*) FROM TransactionRecordBean trb WHERE trb.users.id=?",
                            new Object[] { id})
                    .get(0);
        }
        //System.out.println("Long "+count);
        //System.out.println("int "+count.intValue());
        return count.intValue();

    }

    // 查询交易记录
    public PageModel getTransactionRecord(String myName, final String queryStr,final int pageNo,final int pageSize) throws Exception {
        /*String myName = vob.getUsername();
        final String queryStr = vob.getQueryStr();
        final int pageNo = vob.getPageNo();
        final int pageSize = vob.getPageSize();*/
        PageModel pageModel = new PageModel();
        
        final int userId = (Integer) this.getAccountInformation(myName).getId();
        //Session session = HibernateFilter.getSession();
        //Query query = null;
        List list = null;

        if (queryStr != null && queryStr.trim().length() != 0) {
            /*query = session
                    .createQuery(
                            "FROM TransactionRecordBean trb WHERE trb.users.id=? AND (trb.toName LIKE ? OR trb.transactionType LIKE ?) ORDER BY trb.bId")
                    .setParameter(0, userId).setParameter(1, "%" + queryStr + "%").setParameter(2, "%" + queryStr + "%");*/
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    return session
                            .createQuery(
                                    "FROM TransactionRecordBean trb WHERE trb.users.id=? AND (trb.toName LIKE ? OR trb.transactionType LIKE ?) ORDER BY trb.bId")
                            .setParameter(0, userId)
                            .setParameter(1, "%" + queryStr + "%")
                            .setParameter(2, "%" + queryStr + "%")
                            .setFirstResult((pageNo - 1) * pageSize)
                            .setMaxResults(pageSize).list();
                }
            });
        } else {
            /*query = session.createQuery("FROM TransactionRecordBean trb WHERE trb.users.id=? ORDER BY trb.bId")
                    .setParameter(0, userId);*/
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    return session
                            .createQuery(
                                    "FROM TransactionRecordBean trb WHERE trb.users.id=? ORDER BY trb.bId")
                            .setParameter(0, userId)
                            .setFirstResult((pageNo - 1) * pageSize)
                            .setMaxResults(pageSize).list();
                }
            });
        }
        /*list = query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();*/
        //日志存储
        //每次都查。每次翻页都生成一条记录，太频繁了
        /*LogBean log = new LogBean();
        log.setUserName(myName);
        log.setLogType("User actions");
        log.setLogDetail("用户‘"+myName+"’查询了交易记录！");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);*/
        
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(getTotalRecords(userId, queryStr));

        return pageModel;
    }

    /**
     * <b>用户取款</b>
     * 
     * @param username
     *            当前账户用户名
     * @param m
     *            取款数额
     */
    public int withDrawals(String username, double m) throws Exception {

        /*String username = vob.getUsername();
        double m = vob.getToMoney();*/
        int flag = 0;
        double money = 0;// 查询金额
        int userId = (Integer) this.getAccountInformation(username).getId();
        //Session session = null;

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

//        try {
            /*session = HibernateFilter.getSession();
            session.beginTransaction();
            Query query = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?").setParameter(0, money)
                    .setParameter(1, username);
            flag = query.executeUpdate();
            session.getTransaction().commit();*/
        //如果更新所有字段，效率太低了，待优化
            UserBean ub = (UserBean) this.getHibernateTemplate().get(UserBean.class, userId);
            ub.setMoney(money);
            this.getHibernateTemplate().update(ub);

            // 存储交易记录
            this.storeTransactionRecords("withDrawals", username, username, m);
            flag=1;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            session.getTransaction().rollback();
//        }
        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(username);
        log.setLogType("User actions");
        log.setLogDetail("用户‘" + username + "’取款" + m + "元！");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);
        return flag;
    }

    /**
     * <b>用户存款</b>
     * 
     * @param m
     *            存款数额
     */
    public int deposit(String username, double m) throws Exception {
        /*String username = vob.getUsername();
        double m = vob.getToMoney();*/
        int flag = 0;
        double money = 0;
        //Session session = null;
        int userId = (Integer) this.getAccountInformation(username).getId();

        // 查询金额
        money = this.getAccountInformation(username).getMoney();

        if (m <= 0)// 当取款数额不能为非正数数，取款失败，否则输出取款金额及当前账户金额
        {
            throw new InvalidDepositException("bank.java.error.withDrawals.Non-positive_number");// 您输入的存款金额必须大于0！存款失败！
        }

        money = money + m;
       /* try {
            session = HibernateFilter.getSession();
            session.beginTransaction();
            Query query = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?").setParameter(0, money)
                    .setParameter(1, username);
            flag = query.executeUpdate();
            session.getTransaction().commit();
*/
        UserBean ub = (UserBean) this.getHibernateTemplate().get(UserBean.class, userId);
        ub.setMoney(money);
        this.getHibernateTemplate().update(ub);
            // 存储交易记录
            this.storeTransactionRecords("deposit", username, username, m);
            flag=1;
        /*} catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }*/
        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(username);
        log.setLogType("User actions");
        log.setLogDetail("用户‘" + username + "’存款" + m + "元！");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);
        return flag;
    }

    /**
     * 转账方法
     * 
     * @param username
     *            当前账户用户名
     * @param toName 对方用户名
     * @param money
     *            转账金额
     */
    public int transfer(String username,String toName, double money) throws Exception {
        /*String username = vob.getUsername();
        String toName = vob.getToName();
        double money = vob.getToMoney();*/
        int flag = 0;
        double m = 0;
        //Session session = null;

        m = this.getAccountInformation(username).getMoney();//取得自己的余额

        if (money <= 0) {
            // System.out.println("转账金额必须大于0！");
            throw new BankPublicException("bank.java.error.transfer.Non-positive_number");
        }
        if (money > m) {
            // System.out.println("转账金额不能大于您的余额！");
            throw new BankPublicException("bank.java.error.transfer.exceed");
        }

/*        try {
            session = HibernateFilter.getSession();
            session.beginTransaction();

            Query query1 = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?")
                    .setParameter(0, m - money).setParameter(1, username);
            flag = query1.executeUpdate();
            m = this.getAccountInformation(toName).getMoney();//取得对方的余额
            Query query2 = session.createQuery("UPDATE UserBean u SET u.money=? WHERE username=?")
                    .setParameter(0, m + money).setParameter(1, toName);
            query2.executeUpdate();
            session.getTransaction().commit();*/
        int userId = (Integer) this.getAccountInformation(username).getId();
        UserBean ub1 = (UserBean) this.getHibernateTemplate().get(UserBean.class, userId);
        ub1.setMoney(m-money);
        this.getHibernateTemplate().update(ub1);
        
        int toId = (Integer) this.getAccountInformation(toName).getId();
        UserBean ub2 = (UserBean) this.getHibernateTemplate().get(UserBean.class, toId);
        ub2.setMoney(ub2.getMoney()+money);
        this.getHibernateTemplate().update(ub2);
        
            // 存储交易记录
            this.storeTransactionRecords("payment", username, toName, money);
            this.storeTransactionRecords("proceeds", toName, username, money);

            flag =1;
/*        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }*/
        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(username);
        log.setLogType("User actions");
        log.setLogDetail("用户‘" + username + "’向用户‘" + toName + "’转款" + money + "元！");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);
        return flag;
    }

    /**
     * 存储交易记录
     * 
     * @param type
     * @param myName
     * @param toName
     * @param money
     * @return
     * @throws Exception
     */
    public boolean storeTransactionRecords(String type, String myName, String toName, double money) throws Exception {
        UserBean ub = null;
        //Session session = null;
        TransactionRecordBean trb = null;

//        try {
            ub = this.getAccountInformation(myName);

            /*session = HibernateFilter.getSession();
            session.beginTransaction();*/
            trb = new TransactionRecordBean();
            trb.setUsers(ub);
            trb.setToName(toName);
            trb.setbMoney(money);
            trb.setTransactionType(type);
            trb.setbDate(new Timestamp(new Date().getTime()));
            this.getHibernateTemplate().save(trb);
            /*session.save(trb);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }*/
        return true;
    }

    /**
     * <b>退出系统</b>
     * 
     */
    public void secede(String role,String name) throws Exception {
        // userDao.addBank(user);// 存储用户数据
       // System.out.println("系统已退出！");
        // System.exit(0);//会关闭tomcat服务器
     // 日志存储
        LogBean log = new LogBean();
        log.setUserName(name);
        
        if("1".equals(role)){
            log.setLogType("User secede");
            log.setLogDetail("用户‘" + name + "’退出系统！");
        }else{
            log.setLogType("Administrator secede");
            log.setLogDetail("管理员‘" + name + "’退出系统！");
        }
        
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);
    }
}
