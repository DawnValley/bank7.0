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
 * @author RG
 * @version 2018/09/25
 */
@Controller
@RequestMapping("/Administrator/AdministratorController.do")
public class AdministratorController {
    
    @Resource(name="administratorManagerImp1")
    private AdministratorManager manager;

    /*public void setManager(AdministratorManager manager) {
        this.manager = manager;
    }*/
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
        /*System.out.println("------------------进入userManagementInfo------------------");
        System.out.println("pageNo = "+ab.getPageNo()+"      pageSize = "+ab.getPageSize());
        System.out.println("------------------退出userManagementInfo------------------");*/
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
        
        /*如果没有查询信息，翻页时会产生null，进而在数据库查询的信息为字符串null
         * return new ModelAndView("/Administrator/AdministratorController.do?method=userManagementInfo&pageNo="+ab.getPageNo()
                + "&pageSize=" + ab.getPageSize()
                + "&queryStr=" + ab.getQueryStr(),false);*/
        /*System.out.println("*******************进入stateOperation*******************");
        System.out.println("pageNo = "+ab.getPageNo()+"      pageSize = "+ab.getPageSize());
        
        String url = "AdministratorController.do?method=userManagementInfo"
                + "&pageNo="+ab.getPageNo()
        + "&pageSize=" + ab.getPageSize();
        System.out.println("url = "+url);
        System.out.println("*******************退出stateOperation*******************");
        return new ModelAndView(url);*/
        
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
