package com.cx.bank.manager;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cx.bank.mode1.LogBean;
/**
 * 日志管理实现类
 * @author RG
 * @version 5.0 2018/10/01
 */
public class LogManagerImp1 extends HibernateDaoSupport implements LogManager {

    /* (non-Javadoc)
     * @see com.cx.bank.manager.LogManager#storageLog(com.cx.bank.mode1.LogBean)
     */
    @Override
    public void storageLog(LogBean log) throws Exception {
        this.getHibernateTemplate().save(log);
    }
}
