package com.cx.administrator.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cx.administrator.form.AdministratorForm;
import com.cx.administrator.manager.AdministratorManager;
import com.cx.bank.action.BaseAction;
import com.cx.bank.util.PageModel;

/**
 * 管理员action
 * @author RG
 * @version 2018/09/25
 */
public class AdministratorAction extends BaseAction {
    private AdministratorManager manager;

    public void setManager(AdministratorManager manager) {
        this.manager = manager;
    }
    /**
     * 用户管理
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
    public ActionForward userManagementInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String administratorName = (String)request.getSession().getAttribute("userName");
        AdministratorForm af = (AdministratorForm)form;
        PageModel pm = manager.getAllUserInformation(administratorName,af.getQueryStr(),af.getIdentity(),af.getPageNo(),af.getPageSize());
        request.setAttribute("pageMode", pm);
        request.setAttribute("queryStr", af.getQueryStr());
        return mapping.findForward("userManagementInfo");
    }
    /**
     * 账户激活冻结
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
    public ActionForward stateOperation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String administratorName = (String)request.getSession().getAttribute("userName");
        AdministratorForm af = (AdministratorForm)form;
        manager.stateOperation(administratorName,af.getId(), af.getOperation());
        /*如果没有查询信息，翻页时会产生null，进而在数据库查询的信息为字符串null
         * return new ActionForward("/Administrator/AdministratorAction.do?command=userManagementInfo&pageNo="+af.getPageNo()
                + "&pageSize=" + af.getPageSize()
                + "&queryStr=" + af.getQueryStr(),false);*/
        return new ActionForward("/Administrator/AdministratorAction.do?command=userManagementInfo&pageNo="+af.getPageNo()
        + "&pageSize=" + af.getPageSize()
        ,false);
    }
    
    /**
     * 日志管理
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
    public ActionForward logManagementInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String administratorName = (String)request.getSession().getAttribute("userName");
        AdministratorForm af = (AdministratorForm)form;
        PageModel pm = manager.getAllLogInformation(administratorName,af.getQueryStr(),af.getType(),af.getPageNo(),af.getPageSize());
        request.setAttribute("pageMode", pm);
        request.setAttribute("queryStr", af.getQueryStr());
        //request.setAttribute("type", af.getType());
        return mapping.findForward("logManagementInfo");
    }
}
