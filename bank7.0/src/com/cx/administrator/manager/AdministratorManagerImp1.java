package com.cx.administrator.manager;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import com.cx.bank.dao.OperationMapper;
import com.cx.bank.manager.LogManager;
import com.cx.bank.model.LogBean;
import com.cx.bank.model.QueryObject;
import com.cx.bank.model.UserBean;
import com.cx.bank.util.PageModel;
/**
 * 管理员业务层实现类
 * @author RaoGang
 * @version 7.0 2018/10/08
 */
public class AdministratorManagerImp1 implements AdministratorManager {
    //mybatis的持久层对象，通过注解注入
    @Resource(name="mapper")
    private OperationMapper mapper;
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
     * @param queryStr 查询参数
     * @param identity 查询备用参数
     * @return
     */
    public int getUserTotalRecords(String queryStr,String identity)  throws Exception{

        Long count = null;
        
        QueryObject qo = new QueryObject();
        qo.setQueryStr(queryStr);
        
        count = mapper.selectUserTotalRecords(qo);

        return count.intValue();

    }
    /**
     * 查询所有用户信息
     * @param administratorName 管理员用户名
     * @param queryStr 查询参数
     * @param identity 查询备用参数
     * @param pageNo 当前页码
     * @param pageSize 页面尺寸
     * @return
     * @throws Exception
     */
    public PageModel getAllUserInformation( String administratorName,String queryStr,String identity,int pageNo,int pageSize) throws Exception {

        List<UserBean> list = null;
        PageModel pageModel = new PageModel();
        QueryObject qo = new QueryObject();
        qo.setQueryStr(queryStr);
        qo.setPageNo((pageNo - 1) * pageSize);
        qo.setPageSize(pageSize);
        
        list = mapper.selectAllUserInformation(qo);

        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(getUserTotalRecords(queryStr,identity));

        return pageModel;
    }
    
    /**
     * 账户激活解冻
     * @param administratorName 管理员用户名
     * @param id 用户id 
     * @param operation 操作
     * @return
     * @throws Exception
     */
    public void stateOperation(String administratorName, int id,String operation) throws Exception{
        UserBean ub = mapper.selectUserById(id);
        ub.setConditions(operation);
        mapper.updateUser(ub);
        
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
        log.setLogTime(new Timestamp(System.currentTimeMillis()));
        logManager.storageLog(log);
    }
    
    /**
     * 查询日志总记录数
     * @param queryStr 查询参数
     * @param type 日志类型
     * @return
     * @throws Exception
     */
    public int getTotalLogRecords(String queryStr, String type) throws Exception {
        
        QueryObject qo = new QueryObject();
        qo.setQueryStr(queryStr);
        qo.setType(type);
        Long count = null;
        count = mapper.selectTotalLogRecords(qo);
        return count.intValue();

    }
    /**
     * 查询日志信息
     * @param administratorName 管理员用户名
     * @param queryStr 查询参数
     * @param type 日志类型
     * @param pageNo 当前页码
     * @param pageSize 页面尺寸
     * @return
     * @throws Exception
     */
    public PageModel getAllLogInformation( String administratorName, final String queryStr,String type,final int pageNo,final int pageSize) throws Exception {
        QueryObject qo = new QueryObject();
        qo.setQueryStr(queryStr);
        qo.setType(type);
        qo.setPageNo((pageNo - 1) * pageSize);
        qo.setPageSize(pageSize);
        
        List<LogBean> list = null;
        list = mapper.selectAllLogInformation(qo);
        
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(getTotalLogRecords(queryStr,type));

        return pageModel;
    }
}
