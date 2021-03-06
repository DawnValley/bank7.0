package com.cx.bank.aspect;

import org.aspectj.lang.JoinPoint;

import com.cx.bank.filter.StateFilter;
import com.cx.bank.manager.Manager;
import com.cx.bank.manager.ManagerImp1;
import com.cx.bank.mode1.ValueObjectBean;
import com.cx.bank.util.BankPublicException;

/**
 * 切面类
 * @author RG
 * @version 5.0 2018/09/30
 */
public class AspectHandler {
    
    private Manager manager;
    /**
     * 用户权限验证
     * @throws Exception 
     */
    public boolean userRightsValidation(JoinPoint joinPoint) throws Exception{
        /*System.out.println("----------userRightsValidation被执行------------");
        System.out.println("方法名："+joinPoint.getSignature().getName());*/
        // 获取操作内容
        Object[] args = joinPoint.getArgs();
        String userName = (String)args[0];
        //System.out.println("userName："+userName);
        
        /*System.out.println("getTarget() ="+joinPoint. getTarget() );
        System.out.println("getThis()  ="+joinPoint. getThis()  );*/
        
        manager = (Manager)joinPoint.getTarget();//得到目标对象
        
        //manager = new ManagerImp1();
        /*System.out.println("manager："+manager);
        System.out.println("userBean："+manager.getAccountInformation(userName));*/
        
        //通过用户名取得该用户状态信息
        String state = manager.getAccountInformation(userName).getConditions();
        if("activation".equals(state)){
            return true;
        }
        else{
            throw new BankPublicException("bank.java.error.filter.no_permissions");
        }
    }
    
    /**
     * 存储日志
     * 该方法并未使用，使用了其他方法
     * @param joinPoint
     */
    /*public void storageLog(JoinPoint joinPoint) {
        // 获取操作内容
        Object[] args = joinPoint.getArgs();
        // 判断参数
        if (args == null) {
            // 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        
        if ("register".equals(methodName)) {
            
        }
        if ("login".equals(methodName)) {
            ValueObjectBean vob = (ValueObjectBean)args[0];
            System.out.print("用户"+vob.getUsername()+"登录系统！");
        }
        if ("getAccountInformation".equals(methodName)) {

        }
        if ("getTransactionRecord".equals(methodName)) {

        }
        if ("withDrawals".equals(methodName)) {

        }
        if ("deposit".equals(methodName)) {

        }
        if ("transfer".equals(methodName)) {

        }
        if ("getAllUserInformation".equals(methodName)) {

        }
        if ("stateOperation".equals(methodName)) {

        }

          
        
        
        
        
        System.out.print("参数信息 = 【");
        for (int i = 0; i < args.length; i++) {
            System.out.print(" "+args[i]+" ");
        }
        // 取得调用方法的方法名
        System.out.println("】methodName = "+methodName);

    }*/
    
}
