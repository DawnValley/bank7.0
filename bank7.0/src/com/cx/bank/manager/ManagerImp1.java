
package com.cx.bank.manager;

import java.util.*;

import javax.annotation.Resource;

import java.sql.Timestamp;

import com.cx.bank.util.*;
import com.cx.bank.dao.*;
import com.cx.bank.model.*;

/**
 * <b>业务层ManagerImp1类</b>
 * 
 * @author RaoGang
 * @version 5.0 2018/09/23
 * @see com.cx.bank.mode1.MoneyBean
 * @see com.cx.bank.factory.UserDaoFactory
 */
public class ManagerImp1 implements Manager {
    //mybatis的持久层对象，通过注解注入
    @Resource(name="mapper")
    private OperationMapper mapper;
    private BankDaoInterface userDao = null;// 持久层接口变量
    private LogManager logManager = null;

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
     * @param ub
     *            用户注册信息
     */
    public boolean register(UserBean ub) throws Exception {

        if (userDao.register(ub) == true)
            return true;
        else
            return false;
    }

    /**
     * 实现登录方法
     * 
     * @param ub
     *            用户登录信息
     */
    public boolean login(UserBean ub) throws Exception {

        if (userDao.login(ub) == true)
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
        return mapper.selectUserByName(userName);
    }

    /**
     * 查询记录数
     * 
     * @param id 用户id
     * @param queryStr 查询信息
     * @return
     * @throws Exception 
     */
    public int getTotalRecords(int id,String queryStr) throws Exception {

        Long count = null;
        QueryObject qo = new QueryObject();
        qo.setId(id);
        qo.setQueryStr(queryStr);
        
        count = mapper.selectTotalRecords(qo);

        return count.intValue();

    }

    /**
     * 查询交易记录
     * @param myName 当前用户名
     * @param queryStr 查询信息
     * @param pageNo 当前页码
     * @param pageSize 页面尺寸
     * @return 
     * @throws Exception 
     */
    public PageModel getTransactionRecord(String myName,String queryStr,int pageNo,int pageSize) throws Exception {
        
        PageModel pageModel = new PageModel();
        int userId = (Integer) this.getAccountInformation(myName).getId();
        
        QueryObject qo = new QueryObject();
        qo.setId(userId);
        qo.setQueryStr(queryStr);
        qo.setPageNo((pageNo - 1) * pageSize);//不知道能不能在xml中进行
        qo.setPageSize(pageSize);
        
        List<TransactionRecordBean> list = null;
        list = mapper.selectAllTransactionRecord(qo);
        
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setList(list);
        pageModel.setTotalRecords(this.getTotalRecords(userId, queryStr));

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

        int flag = 0;
        double money = 0;
        UserBean ub = this.getAccountInformation(username);
        money = ub.getMoney();// 查询金额
        if (m <= 0)// 当取款数额为非正数数，取款失败，否则输出取款金额及当前账户金额
        {                                                                    
            throw new InvalidWithDrawalstException("您输入的取款金额必须大于0！取款失败！");// 取款数额不为正数异常
        }
        if (m > money)// 当取款数额大于余额，取款失败
        {         
            throw new AccountOverDrawnException("您的账户余额不足！取款失败！");// 取款数额大于余额异常
        }
        
        //更新用户数据
        money = money - m;
        ub.setMoney(money);
        mapper.updateUser(ub);

        // 存储交易记录
        this.storeTransactionRecords("withDrawals", username, username, m);
        flag=1;

        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(username);
        log.setLogType("User actions");
        log.setLogDetail("用户‘" + username + "’取款" + m + "元！");
        log.setLogTime(new Timestamp(System.currentTimeMillis()));
        logManager.storageLog(log);
        return flag;
    }

    /**
     * <b>用户存款</b>
     * @param username
     *            当前账户用户名
     * @param m
     *            存款数额
     */
    public int deposit(String username, double m) throws Exception {

        int flag = 0;
        double money = 0;
        UserBean ub = this.getAccountInformation(username);
        money = ub.getMoney();// 查询金额
        if (m <= 0)// 当取款数额不能为非正数数，取款失败，否则输出取款金额及当前账户金额
        {
            throw new InvalidDepositException("您输入的存款金额必须大于0！存款失败！");
        }
        //更新用户数据
        money = money + m;
        ub.setMoney(money);
        mapper.updateUser(ub);
        
        // 存储交易记录
        this.storeTransactionRecords("deposit", username, username, m);
        flag=1;
        
        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(username);
        log.setLogType("User actions");
        log.setLogDetail("用户‘" + username + "’存款" + m + "元！");
        log.setLogTime(new Timestamp(System.currentTimeMillis()));
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

        int flag = 0;
        double m = 0;

        UserBean user = this.getAccountInformation(username);
        UserBean toUser = this.getAccountInformation(toName);
        m = user.getMoney();//取得自己的余额

        if (money <= 0) {
            throw new BankPublicException("转账金额必须大于0！转账失败！");
        }
        if (money > m) {
            throw new BankPublicException("转账金额不能大于您的余额！");
        }
        if(toUser==null){
            throw new BankPublicException("对方账户不存在，请核对后重新操作！转账失败！");
        }
        //更新转账方用户数据
        user.setMoney(m-money);
        mapper.updateUser(user);
        //更新被转账方用户数据
        toUser.setMoney(toUser.getMoney()+money);
        mapper.updateUser(toUser);
        
        // 存储交易记录
        this.storeTransactionRecords("payment", username, toName, money);
        this.storeTransactionRecords("proceeds", toName, username, money);

        flag =1;

        // 日志存储
        LogBean log = new LogBean();
        log.setUserName(username);
        log.setLogType("User actions");
        log.setLogDetail("用户‘" + username + "’向用户‘" + toName + "’转款" + money + "元！");
        log.setLogTime(new Timestamp(System.currentTimeMillis()));
        logManager.storageLog(log);
        return flag;
    }

    /**
     * 存储交易记录
     * 
     * @param type 交易类型
     * @param myName 用户名
     * @param toName 对方用户名
     * @param money 金额
     * @return
     * @throws Exception
     */
    public boolean storeTransactionRecords(String type, String myName, String toName, double money) throws Exception {
        UserBean ub = null;
        TransactionRecordBean trb = null;

        ub = this.getAccountInformation(myName);

        trb = new TransactionRecordBean();
        trb.setUsers(ub);
        trb.setToName(toName);
        trb.setbMoney(money);
        trb.setTransactionType(type);
        trb.setbDate(new Timestamp(System.currentTimeMillis()));
        mapper.addTransactionRecords(trb);

        return true;
    }

    /**
     * <b>退出系统</b>
     * @param role 角色
     * @param name 用户名
     */
    public void secede(String role,String name) throws Exception {
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
        
        log.setLogTime(new Timestamp(System.currentTimeMillis()));
        logManager.storageLog(log);
    }
}
