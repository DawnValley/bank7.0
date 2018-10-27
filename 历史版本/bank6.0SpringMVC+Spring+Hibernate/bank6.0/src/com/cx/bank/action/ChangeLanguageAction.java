package com.cx.bank.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 改变语言设置
 * @author RG
 * @version 2018/10/06
 */
@Controller
@RequestMapping("/ChangeLanguageAction.do")
public class ChangeLanguageAction {
    
    @RequestMapping(params="method=indexChange")
    public String indexChange(){
        
        return "/WEB-INF/bank/index.jsp";
    }
    @RequestMapping(params="method=loginChange")
    public String loginChange(){
        
        return "/WEB-INF/bank/login.jsp";
    }
    
}
