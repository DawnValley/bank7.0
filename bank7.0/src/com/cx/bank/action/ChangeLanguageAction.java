package com.cx.bank.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 改变语言设置
 * 
 * @author RaoGang
 * @version 2018/10/06
 */
@Controller
@RequestMapping("/ChangeLanguageAction.do")
public class ChangeLanguageAction {
    // 首页改变语言跳转到首页
    @RequestMapping(params = "method=indexChange")
    public String indexChange() {

        return "/WEB-INF/bank/index.jsp";
    }

    // 登录页面改变语言跳转到登录页面
    @RequestMapping(params = "method=loginChange")
    public String loginChange() {

        return "/WEB-INF/bank/login.jsp";
    }

}
