package com.cx.bank.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转控制
 * @author RaoGang
 * @version 6.0 2018/10/06
 */
@Controller("/PageController.do")
public class PageController {

    /**
     * 错误页面
     * @return
     * @throws Exception
     */
    @RequestMapping(params="method=errorPage")
    public ModelAndView errorPage() throws Exception {
        
        return new ModelAndView("/WEB-INF/bank/error.jsp");
    }
    
    
    @RequestMapping(params="method=show_top")
    public ModelAndView show_top() throws Exception {
        
        return new ModelAndView("/WEB-INF/BankBusiness/show_top.jsp");
    }
    
    @RequestMapping(params="method=show_left")
    public ModelAndView show_left() throws Exception {
        
        return new ModelAndView("/WEB-INF/BankBusiness/show_left.jsp");
    }
    
    @RequestMapping(params="method=show_index")
    public ModelAndView show_index() throws Exception {
        
        return new ModelAndView("/WEB-INF/BankBusiness/show_index.jsp");
    }
    
    @RequestMapping(params="method=administrator_show_top")
    public ModelAndView administrator_show_top() throws Exception {

        return new ModelAndView("/WEB-INF/Administrator/administrator_show_top.jsp");
    }
    
    @RequestMapping(params="method=administrator_show_left")
    public ModelAndView administrator_show_left() throws Exception {

        return new ModelAndView("/WEB-INF/Administrator/administrator_show_left.jsp");
    }
    
    @RequestMapping(params="method=administrator_show_index")
    public ModelAndView administrator_show_index() throws Exception {

        return new ModelAndView("/WEB-INF/Administrator/administrator_show_index.jsp");
    }
}
