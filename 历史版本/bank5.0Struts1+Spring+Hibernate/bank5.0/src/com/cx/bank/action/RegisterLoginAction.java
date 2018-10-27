package com.cx.bank.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.cx.bank.form.LoginForm;
import com.cx.bank.manager.Manager;
import com.cx.bank.mode1.ValueObjectBean;

/**
 * 注册的Action类
 * 
 * @author RG
 * @version 1.0 2018/09/10
 */
public class RegisterLoginAction extends DispatchAction {

    private Manager managerDao;
    
    public void setManagerDao(Manager managerDao){
        this.managerDao = managerDao;
    }
    
	/**
	 * 如果没有传递任何标识参数（如command参数），则默认调用unspecified方法
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//System.out.println("ItemAction=>>unspecified()");
		return mapping.findForward("login");
	}

	/**
	 * 注册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginForm lb = (LoginForm) form;
		ValueObjectBean vob = new ValueObjectBean();
		
		BeanUtils.copyProperties(vob,lb);
		//Manager mi = null;
		boolean flag = false;

		//mi = ManagerImp1.getInstance();
		flag = managerDao.register(vob);

		return mapping.findForward("registerSuccess");
	}

	/**
	 * 登录
	 * 
	 * @param mapping 
	 * @param form 表单bean
	 * @param request 请求参数
	 * @param response
	 * @return 跳转信息
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginForm lb = (LoginForm) form;
	
		boolean flag = false;
		HttpSession session = request.getSession();
		ValueObjectBean vob = new ValueObjectBean();
        
        BeanUtils.copyProperties(vob,lb);
        
		flag = managerDao.login(vob);// 登录

		//如果没有登录的验证信息，设置验证信息和角色信息
		if (session.getAttribute("userName") == null) {
			session.setAttribute("userName", lb.getUsername());
			session.setAttribute("role",lb.getRole());
		}
		//System.out.println("lb.getRole()::"+lb.getRole());
		if("1".equals(lb.getRole())){
		    return mapping.findForward("loginSuccess");
		}else{
		    return mapping.findForward("administratorInfo");
		}
	}
	
	
	public ActionForward show_top(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("show_top");
	}
	public ActionForward show_left(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("show_left");
	}
	public ActionForward show_index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("show_index");
	}
	
    public ActionForward administrator_show_top(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return mapping.findForward("administrator_show_top");
    }

    public ActionForward administrator_show_left(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return mapping.findForward("administrator_show_left");
    }

    public ActionForward administrator_show_index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return mapping.findForward("administrator_show_index");
    }
    /**
     * 退出
     * 
     * @param mapping
     * @param form
     *            表单bean
     * @param request
     *            请求参数
     * @param response
     * @return 跳转信息
     * @throws Exception
     */
    public ActionForward Secede(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String role = (String)request.getSession().getAttribute("role");
        String name = (String)request.getSession().getAttribute("userName");
        managerDao.secede(role, name);
        return mapping.findForward("logout");
    }
}
