package com.cx.bank.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cx.bank.manager.Manager;
import com.cx.bank.model.BusinessBean;
import com.cx.bank.util.PageModel;

/**
 * 银行业务的action
 * 
 * @author RaoGang
 * @version 7.0 2018/10/08
 */
@Controller
@RequestMapping("/BankBusiness/BankBusinessController.do")
public class BankBusinessController{
    //银行业务业务层对象，通过注解注入
    @Resource(name="ManagerImp1")
    private Manager managerDao;
    
    /**
     * 查询余额
     * 
     * @param request
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=inquiry")
    public ModelAndView inquiry(HttpServletRequest request) throws Exception {
        Double money=null;
        money = (Double) managerDao.getAccountInformation((String) request.getSession().getAttribute("userName"))
                .getMoney();
        
        if (money != null) {
            
            request.setAttribute("balance", "您的余额为"+money+"元");
            
            //取得操作信息
            String operateMessage = (String)request.getAttribute("operateMessage");
            
            if(operateMessage != null){
                request.setAttribute("operateMessage", operateMessage);
            }
            
            return new ModelAndView("/WEB-INF/BankBusiness/queryResults.jsp");
        } else {
            
            request.setAttribute("operate", "查询");
            return new ModelAndView("/WEB-INF/bank/error.jsp");
        }
    }

	/**
	 * 取款页面
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=withDrawalsPage")
	public ModelAndView withDrawalsPage() throws Exception {

		return new ModelAndView("/WEB-INF/BankBusiness/withDrawals.jsp");
	}

	/**
	 * 存款页面
	 * 
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=depositPage")
	public ModelAndView depositPage() throws Exception {

		return new ModelAndView("/WEB-INF/BankBusiness/deposit.jsp");
	}

	/**
	 * 转账款页面
	 * 
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=transferPage")
	public ModelAndView transferPage() throws Exception {

		return new ModelAndView("/WEB-INF/BankBusiness/transfer.jsp");
	}


	/**
	 * 查询交易记录
	 * 
	 * @param bb 请求参数
	 * @param request
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=queryTransactionRecord")
	public ModelAndView queryTransactionRecord(BusinessBean bb,HttpServletRequest request) throws Exception {
        
        bb.setUsername((String) request.getSession().getAttribute("userName"));
		PageModel pm = managerDao.getTransactionRecord(bb.getUsername(),bb.getQueryStr(),bb.getPageNo(),bb.getPageSize());
		request.setAttribute("pageMode", pm);
		request.setAttribute("queryStr", bb.getQueryStr());
		return new ModelAndView("/WEB-INF/BankBusiness/transactionRecord.jsp");
	}

	/**
	 * 取款
	 * 
     * @param bb 请求参数
     * @param request
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=withDrawals")
	public ModelAndView withDrawals(BusinessBean bb,HttpServletRequest request) throws Exception {
		
        request.setAttribute("operate", "取款");
        
		int flag = 0;
		
        bb.setUsername((String) request.getSession().getAttribute("userName"));


		flag = managerDao.withDrawals(bb.getUsername(),bb.getToMoney());
		
		if (flag == 0) {
			return new ModelAndView("/WEB-INF/bank/error.jsp");
		} else {
		    request.setAttribute("operateMessage",  "取款成功！共取款 "+bb.getToMoney()+" 元！");
			return new ModelAndView("BankBusinessController.do?method=inquiry");
		}

	}

	/**
	 * 存款
	 * 
	 * @param bb 请求参数
     * @param request
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=deposit")
	public ModelAndView deposit(BusinessBean bb,HttpServletRequest request) throws Exception {

		int flag = 0;
		request.setAttribute("operate", "存款");

        bb.setUsername((String) request.getSession().getAttribute("userName"));

		flag = managerDao.deposit(bb.getUsername(),bb.getToMoney());

		if (flag == 0) {
			return new ModelAndView("/WEB-INF/bank/error.jsp");
		} else {
		    request.setAttribute("operateMessage", "存款成功！共存款 "+bb.getToMoney()+" 元！");
			return new ModelAndView("BankBusinessController.do?method=inquiry");
		}
	}

	/**
	 * 转账
	 * 
	 * @param bb 请求参数
     * @param request
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=transfer")
	public ModelAndView transfer(BusinessBean bb,HttpServletRequest request) throws Exception {

		int flag = 0;
		String username = (String) request.getSession().getAttribute("userName");
		String toName = bb.getToName();
		request.setAttribute("operate", "转账");
		
        bb.setUsername(username);
        /*
         * 如果转账的对方账户为当前账户，跳转到出错页面；否则进行转账操作
         */
		if (username.equals(toName)) {
		    request.setAttribute("message", "转账账户不能为您自己的账户！");
			return new ModelAndView("/WEB-INF/bank/error.jsp");
		}
		flag = managerDao.transfer(bb.getUsername(),bb.getToName(),bb.getToMoney());

		if (flag == 0) {
			return new ModelAndView("/WEB-INF/bank/error.jsp");
		} else {
		    request.setAttribute("operateMessage", "转账成功！共转款 "+bb.getToMoney()+" 元！");
			return new ModelAndView("BankBusinessController.do?method=inquiry");
		}
	}
}
