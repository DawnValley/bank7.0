package com.cx.administrator.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cx.administrator.manager.AdministratorManager;
import com.cx.administrator.model.AdministratorBean;
import com.cx.bank.util.PageModel;

/**
 * 管理员action
 * @author RaoGang
 * @version 2018/09/25
 */
@Controller
@RequestMapping("/Administrator/AdministratorController.do")
public class AdministratorController {
    
    //管理员业务层对象，通过注解注入
    @Resource(name="administratorManagerImp1")
    private AdministratorManager manager;

    /**
     * 用户管理
     * 
     * @param ab
     *            表单bean
     * @param request
     *            请求参数
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=userManagementInfo")
    public ModelAndView userManagementInfo(AdministratorBean ab, HttpServletRequest request) throws Exception {

        String administratorName = (String)request.getSession().getAttribute("userName");
        PageModel pm = manager.getAllUserInformation(administratorName,ab.getQueryStr(),ab.getIdentity(),ab.getPageNo(),ab.getPageSize());
        request.setAttribute("pageMode", pm);
        request.setAttribute("queryStr", ab.getQueryStr());
        return new ModelAndView("/WEB-INF/Administrator/userManagementInfo.jsp");
    }
    /**
     * 账户激活冻结
     * 
     * @param ab
     *            表单bean
     * @param request
     *            请求参数
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=stateOperation")
    public ModelAndView stateOperation(AdministratorBean ab, HttpServletRequest request) throws Exception {
        
        String administratorName = (String)request.getSession().getAttribute("userName");
        manager.stateOperation(administratorName,ab.getId(), ab.getOperation());
        return new ModelAndView("AdministratorController.do?method=userManagementInfo");
    }
    
    /**
     * 日志管理
     * 
     * @param ab
     *            表单bean
     * @param request
     *            请求参数
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=logManagementInfo")
    public ModelAndView logManagementInfo(AdministratorBean ab, HttpServletRequest request) throws Exception {

        String administratorName = (String)request.getSession().getAttribute("userName");

        PageModel pm = manager.getAllLogInformation(administratorName,ab.getQueryStr(),ab.getType(),ab.getPageNo(),ab.getPageSize());
        request.setAttribute("pageMode", pm);
        request.setAttribute("queryStr", ab.getQueryStr());
        request.setAttribute("type", ab.getType());
        return new ModelAndView("/WEB-INF/Administrator/logManagementInfo.jsp");
    }
}
