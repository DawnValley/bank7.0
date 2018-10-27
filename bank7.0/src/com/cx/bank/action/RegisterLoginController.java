package com.cx.bank.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cx.bank.manager.Manager;
import com.cx.bank.model.UserBean;

/**
 * 注册登录的控制层类
 * 
 * @author RaoGang
 * @version 6.0 2018/10/03
 */
@Controller
@RequestMapping("/RegisterLoginController.do")
public class RegisterLoginController{
    //银行业务业务层对象，通过注解注入
    @Resource(name="ManagerImp1")
    private Manager managerDao;
    
	/**
	 * 注册
	 * @param ub 用户信息
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(params="method=register")
	public ModelAndView register(UserBean ub) throws Exception {

		managerDao.register(ub);
		return new ModelAndView("/WEB-INF/bank/registerSuccess.jsp");
	}

	/**
	 * 登录
	 * 
	 * @param ub 用户信息
	 * @param request 
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=login")
	public ModelAndView login(UserBean ub,HttpServletRequest request) throws Exception {
	
		HttpSession session = request.getSession();
        
		managerDao.login(ub);// 登录

		//如果没有登录的验证信息，设置验证信息和角色信息
		if (session.getAttribute("userName") == null) {
			session.setAttribute("userName", ub.getUsername());
			session.setAttribute("role",ub.getRole());
		}
		//如果是管理员跳转到管理员页面，如果是普通用户跳转到用户页面
		if("1".equals(ub.getRole())){
		    return new ModelAndView("/WEB-INF/BankBusiness/system.jsp");
		}else{
		    return new ModelAndView("/WEB-INF/Administrator/administratorInfo.jsp");
		}
	}

    /**
     * 登录页面
     * @return
     * @throws Exception
     */
    @RequestMapping(params="method=loginPage")
    public ModelAndView loginPage() throws Exception {
        
        return new ModelAndView("/WEB-INF/bank/login.jsp");
    }
    /**
     * 注册页面
     * @return
     * @throws Exception
     */
    @RequestMapping(params="method=registerPage")
    public ModelAndView registerPage() throws Exception {
        
        return new ModelAndView("/WEB-INF/bank/register.jsp");
    }

    
    /**
     * 退出
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="method=secede")
    public ModelAndView secede(HttpServletRequest request) throws Exception {
        String role = (String)request.getSession().getAttribute("role");
        String name = (String)request.getSession().getAttribute("userName");
        managerDao.secede(role, name);
        return new ModelAndView("/WEB-INF/bank/logout.jsp");
    }
}
