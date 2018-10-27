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
 * @author RG
 * @version 2018/09/10
 */
@Controller
@RequestMapping("/BankBusiness/BankBusinessController.do")
public class BankBusinessController{

    @Resource(name="ManagerImp1")
    private Manager managerDao;
    
    /*public void setManagerDao(Manager managerDao){
        this.managerDao = managerDao;
    }*/
    
    /**
     * 查询余额
     * 
     
     * @param form
     *            表单bean
     * @param request
     *            请求参数
     * @param response
     * @return 跳转信息
     * @throws Exception
     */
    @RequestMapping(params="method=inquiry")
    public ModelAndView inquiry(HttpServletRequest request) throws Exception {
        //Manager mi = null;
        Double money=null;
        
        
        money = (Double) managerDao.getAccountInformation((String) request.getSession().getAttribute("userName"))
                .getMoney();
        
        if (money != null) {
            /*//创建国际化消息文本对象
            ActionMessage message = new ActionMessage("bank.java.BankBusinessAction.query_balance", money);//您的余额为 {0} 元
            messages.add("money",message);*/
            
            request.setAttribute("balance", "您的余额为"+money+"元");
            
            
            //取得操作信息
            /*ActionMessage operateMessage = null;
            operateMessage = (ActionMessage)request.getAttribute("operateMessage");*/
            String operateMessage = (String)request.getAttribute("operateMessage");
            
            if(operateMessage != null){
                /*messages.add("message",operateMessage);*/
                request.setAttribute("operateMessage", operateMessage);
            }
            
            //this.saveMessages(request, messages);//传递国际化消息文本
            return new ModelAndView("/WEB-INF/BankBusiness/queryResults.jsp");
        } else {
            
            /*//创建国际化消息文本对象
            ActionMessage message = new ActionMessage("bank.java.BankBusinessAction.inquiry", null);//查询
            messages.add("operate",message);
            this.saveMessages(request, messages);//传递国际化消息文本
*/          
            request.setAttribute("operate", "查询");
    
            return new ModelAndView("/error.jsp");//不要加反斜杠？？？
        }
    }

	/**
	 * 取款页面
	 * 
	 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
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
	 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
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
	 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
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
	 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
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
	 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=withDrawals")
	public ModelAndView withDrawals(BusinessBean bb,HttpServletRequest request) throws Exception {
		
		/*ActionMessages messages = new ActionMessages();
		ActionMessage message1 = new ActionMessage("bank.java.BankBusinessAction.withDrawals",null);//取款
		messages.add("operate",message1);*/
        request.setAttribute("operate", "取款");
        
		int flag = 0;
		
        bb.setUsername((String) request.getSession().getAttribute("userName"));

		//mi = ManagerImp1.getInstance();

		flag = managerDao.withDrawals(bb.getUsername(),bb.getToMoney());
		
		if (flag == 0) {
			//this.saveMessages(request, messages);
			return new ModelAndView("error");
		} else {
			/*ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.withDrawals_message",bf.getToMoney());//取款成功！共取款 {0} 元！
			request.setAttribute("operateMessage", message2);*/
		    request.setAttribute("operateMessage",  "取款成功！共取款 "+bb.getToMoney()+" 元！");
			return new ModelAndView("BankBusinessController.do?method=inquiry");
		}

	}

	/**
	 * 存款
	 * 
	 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=deposit")
	public ModelAndView deposit(BusinessBean bb,HttpServletRequest request) throws Exception {

		int flag = 0;
		/*ActionMessages messages = new ActionMessages();
		ActionMessage message1 = new ActionMessage("bank.java.BankBusinessAction.deposit",null);//存款
		messages.add("operate",message1);*/
		request.setAttribute("operate", "存款");

        bb.setUsername((String) request.getSession().getAttribute("userName"));

		flag = managerDao.deposit(bb.getUsername(),bb.getToMoney());

		if (flag == 0) {
			//this.saveMessages(request, messages);
			return new ModelAndView("error");
		} else {
			/*ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.deposit_message",bf.getToMoney());//存款成功！共存款 {0} 元！
			request.setAttribute("operateMessage", message2);*/
		    request.setAttribute("operateMessage", "存款成功！共存款 "+bb.getToMoney()+" 元！");
			return new ModelAndView("BankBusinessController.do?method=inquiry");
		}
	}

	/**
	 * 转账
	 * 
	 * @param form
	 *            表单bean
	 * @param request
	 *            请求参数
	 * @param response
	 * @return 跳转信息
	 * @throws Exception
	 */
    @RequestMapping(params="method=transfer")
	public ModelAndView transfer(BusinessBean bb,HttpServletRequest request) throws Exception {

		int flag = 0;
		String username = (String) request.getSession().getAttribute("userName");
		String toName = bb.getToName();
		/*ActionMessages messages = new ActionMessages();
		ActionMessage message1 = new ActionMessage("bank.java.BankBusinessAction.transfer",null);//转账
		messages.add("operate",message1);*/
		request.setAttribute("operate", "转账");
		
        bb.setUsername(username);
        /*
         * 如果转账的对方账户为当前账户，跳转到出错页面；否则进行转账操作
         */
		if (username.equals(toName)) {
			/*ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.transfer_fail_message",null);//转账账户不能为您自己的账户！
			messages.add("message",message2);
			this.saveMessages(request, messages);*/
		    request.setAttribute("message", "转账账户不能为您自己的账户！");
			return new ModelAndView("error");
		} else {
		    
			flag = managerDao.transfer(bb.getUsername(),bb.getToName(),bb.getToMoney());

			if (flag == 0) {
				return new ModelAndView("error");
			} else {
				/*ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.transfer_message",bb.getToMoney());//转账成功！共转款 {0} 元！
				request.setAttribute("operateMessage", message2);*/
			    request.setAttribute("operateMessage", "转账成功！共转款 "+bb.getToMoney()+" 元！");
				return new ModelAndView("BankBusinessController.do?method=inquiry");
			}
		}
	}
}



























