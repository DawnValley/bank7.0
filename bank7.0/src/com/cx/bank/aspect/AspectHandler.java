package com.cx.bank.aspect;

import org.aspectj.lang.JoinPoint;

import com.cx.bank.manager.Manager;

import com.cx.bank.util.BankPublicException;

/**
 * 切面类
 * @author RaoGang
 * @version 5.0 2018/09/30
 */
public class AspectHandler {
    
    private Manager manager;
    /**
     * 用户权限验证
     * @throws Exception 
     */
    public boolean userRightsValidation(JoinPoint joinPoint) throws Exception{
        // 获取操作内容
        Object[] args = joinPoint.getArgs();
        String userName = (String)args[0];
        
        manager = (Manager)joinPoint.getTarget();//得到目标对象
        
        //通过用户名取得该用户状态信息
        String state = manager.getAccountInformation(userName).getConditions();
        if("activation".equals(state)){
            return true;
        }
        else{
            throw new BankPublicException("您的账号已被冻结，无法进行此项操作！请联系客服进行解冻操作，再在退出浏览器后重新登录！");
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
