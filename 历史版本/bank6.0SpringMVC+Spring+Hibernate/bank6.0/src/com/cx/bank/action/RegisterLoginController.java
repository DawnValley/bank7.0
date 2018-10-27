package com.cx.bank.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cx.bank.manager.Manager;
import com.cx.bank.model.UserBean;

/**
 * 注册登录的控制层类
 * 
 * @author RG
 * @version 6.0 2018/10/03
 */
@Controller
@RequestMapping("/RegisterLoginController.do")
public class RegisterLoginController{

    @Resource(name="ManagerImp1")
    private Manager managerDao;
    
    /*public void setManagerDao(Manager managerDao){
        this.managerDao = managerDao;
    }*/
    
	/**
	 * 注册
	 * 
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(params="method=register")
	public ModelAndView register(UserBean ub) throws Exception {

		boolean flag = false;

		flag = managerDao.register(ub);
		
		return new ModelAndView("/WEB-INF/bank/registerSuccess.jsp");
	}

	/**
	 * 登录
	 * 
	 * @param UserBean 表单bean
	 * @param request 请求参数
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=login")
	public ModelAndView login(UserBean ub,HttpServletRequest request) throws Exception {
	
		boolean flag = false;
		HttpSession session = request.getSession();
        
		flag = managerDao.login(ub);// 登录

		//如果没有登录的验证信息，设置验证信息和角色信息
		if (session.getAttribute("userName") == null) {
			session.setAttribute("userName", ub.getUsername());
			session.setAttribute("role",ub.getRole());
		}
		//System.out.println("lb.getRole()::"+lb.getRole());
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
     * 注册页面---还未编写
     * @return
     * @throws Exception
     */
    /*@RequestMapping(params="method=registerPage")
    public ModelAndView registerPage() throws Exception {
        
        return new ModelAndView("/WEB-INF/bank/register.jsp");
    }*/

    

    /**
     * 退出
     * @param request
     *            请求参数
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
