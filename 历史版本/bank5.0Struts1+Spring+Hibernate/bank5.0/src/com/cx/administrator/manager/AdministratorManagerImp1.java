package com.cx.administrator.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cx.bank.manager.LogManager;
import com.cx.bank.mode1.LogBean;
import com.cx.bank.mode1.UserBean;
import com.cx.bank.util.PageModel;
/**
 * 管理员业务层实现类
 * @author RG
 * @version 2018/09/25
 */
public class AdministratorManagerImp1 extends HibernateDaoSupport implements AdministratorManager {
    private LogManager logManager = null;
    /**
     * 设置logManager属性
     * @param logManager
     */
    public void setLogManager(LogManager logManager){
        this.logManager = logManager;
    }
    
    /**
     * 查询记录数
     * 
     * @param queryStr
     * @param identity
     * @return
     */
    public int getTotalRecords(String queryStr,String identity)  throws Exception{

        Long count = null;
        /*
         * 当查询条件不为空时，进行条件查询，否则查询总记录数
         */
        if (queryStr != null && queryStr.trim().length() != 0) {
            count = (Long) this.getHibernateTemplate()
                    .find("SELECT COUNT(*) FROM UserBean u WHERE u.conditions = ?",
                            new Object[] {queryStr})
                    .get(0);
        } else {
            count = (Long) this.getHibernateTemplate()
                    .find("SELECT COUNT(*) FROM UserBean u")
                    .get(0);
        }
        //System.out.println("Long "+count);
        //System.out.println("int "+count.intValue());
        return count.intValue();

    }
    /**
     * 查询所有用户信息
     * @param administratorName
     * @param queryStr
     * @param identity
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public PageModel getAllUserInformation( String administratorName, final String queryStr,String identity,final int pageNo,final int pageSize) throws Exception {

        List list = null;
        PageModel pageModel = new PageModel();
        /*
         * 当查询条件不为空时，进行条件查询，否则查询所有用户信息
         */
        if (queryStr != null && queryStr.trim().length() != 0) {
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    return session
                            .createQuery("FROM UserBean u WHERE u.conditions = ?")
                            .setParameter(0,queryStr)
                            .setFirstResult((pageNo - 1) * pageSize)
                            .setMaxResults(pageSize).list();
                }
            });
            //System.out.println("queryStr不空执行");
        } else {
            list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    return session
                            .createQuery("FROM UserBean u")
                            .setFirstResult((pageNo - 1) * pageSize)
                            .setMaxResults(pageSize).list();
                }
            });
            //System.out.println("queryStr为空执行");
        }
        
        // 日志存储
        //操作太频繁，生成记录过多
        /*LogBean log = new LogBean();
        log.setUserName(administratorName);
        log.setLogType("Administrator actions");
        log.setLogDetail("管理员‘"+administratorName+"’查询了用户信息");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);*/

        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(getTotalRecords(queryStr,identity));

        return pageModel;
    }
    
    /**
     * 账户激活解冻
     * @param administratorName
     * @param id
     * @param operation
     * @return
     * @throws Exception
     */
    public void stateOperation(String administratorName, int id,String operation) throws Exception{
        UserBean ub = (UserBean)this.getHibernateTemplate().load(UserBean.class,id);
        ub.setConditions(operation);
        this.getHibernateTemplate().save(ub);
        
        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(administratorName);
        log.setLogType("Administrator actions");
        if("activation".equals(operation)){
            log.setLogDetail("管理员‘" + administratorName + "’激活了用户‘"+ub.getUsername()+"’的账户！");
        }
        if("freeze".equals(operation)){
            log.setLogDetail("管理员‘" + administratorName + "’冻结了用户‘"+ub.getUsername()+"’的账户！");
        }
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);
    }
    
    /**
     * 查询日志总记录数
     * @param queryStr
     * @param type
     * @return
     * @throws Exception
     */
    public int getTotalLogRecords(String queryStr, String type) throws Exception {

        Long count = null;
        String hql = null;
        boolean queryFlag = queryStr != null && queryStr.trim().length() != 0;
        boolean typeFlag = type != null && type.trim().length() != 0;
        /*
         * 通过查询参数生成hql语句
         */
        if (queryFlag && typeFlag) {
            hql = "SELECT COUNT(*) FROM LogBean l WHERE l.userName = '"+queryStr+"' AND l.logType = '"+type+"'";
        } else if (queryFlag) {
            hql = "SELECT COUNT(*) FROM LogBean l WHERE l.userName = '"+queryStr+"'";
        } else if (typeFlag) {
            hql = "SELECT COUNT(*) FROM LogBean l WHERE l.logType = '"+type+"'";
        } else {
            hql = "SELECT COUNT(*) FROM LogBean l";
        }

        count = (Long) this.getHibernateTemplate().find(hql).get(0);

        // System.out.println("Long "+count);
        // System.out.println("int "+count.intValue());
        return count.intValue();

    }
    /**
     * 查询日志信息
     * @param administratorName
     * @param queryStr
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public PageModel getAllLogInformation( String administratorName, final String queryStr,String type,final int pageNo,final int pageSize) throws Exception {

        List list = null;
        PageModel pageModel = new PageModel();
        String hql = null;
        boolean queryFlag = queryStr != null && queryStr.trim().length() != 0;
        boolean typeFlag = type != null && type.trim().length() != 0;
        /*
         * 通过查询参数生成查询日志信息的hql语句
         */
        if (queryFlag && typeFlag) {
            hql = "SELECT l FROM LogBean l WHERE l.userName = '"+queryStr+"' AND l.logType = '"+type+"'";
        } else if (queryFlag) {
            hql = "SELECT l FROM LogBean l WHERE l.userName = '"+queryStr+"'";
        } else if (typeFlag) {
            hql = "SELECT l FROM LogBean l WHERE l.logType = '"+type+"'";
        } else {
            hql = "SELECT l FROM LogBean l";
        }
        final String logHql = hql;
        list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                return session
                        .createQuery(logHql)
                        .setFirstResult((pageNo - 1) * pageSize)
                        .setMaxResults(pageSize).list();
            }
        });
        
        // 日志存储
        //每次都查。每次翻页都生成一条记录，太频繁了
        /*LogBean log = new LogBean();
        log.setUserName(administratorName);
        log.setLogType("Administrator actions");
        log.setLogDetail("管理员‘"+administratorName+"’查询了日志信息！");
        log.setLogTime(new Timestamp(new Date().getTime()));
        logManager.storageLog(log);*/

        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(getTotalLogRecords(queryStr,type));

        return pageModel;
    }
}
