package com.cx.bank.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cx.bank.form.BusinessForm;
import com.cx.bank.manager.Manager;
import com.cx.bank.mode1.ValueObjectBean;
import com.cx.bank.util.PageModel;

/**
 * 银行业务的action
 * 
 * @author RG
 * @version 2018/09/10
 */
public class BankBusinessAction extends BaseAction {

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
	 * 取款页面
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
	public ActionForward withDrawalsPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("withDrawals");
	}

	/**
	 * 存款页面
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
	public ActionForward depositPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("deposit");
	}

	/**
	 * 转账款页面
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
	public ActionForward transferPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("transfer");
	}

	/**
	 * 查询余额
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
	public ActionForward inquiry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Manager mi = null;
		Double money=null;
		ActionMessages messages = new ActionMessages();

		//mi = ManagerImp1.getInstance();
		
		money = (Double) managerDao.getAccountInformation((String) request.getSession().getAttribute("userName"))
				.getMoney();
		
		if (money != null) {
			//创建国际化消息文本对象
			ActionMessage message = new ActionMessage("bank.java.BankBusinessAction.query_balance", money);//您的余额为 {0} 元
			messages.add("money",message);
			
			//取得操作信息
			ActionMessage operateMessage = null;
			operateMessage = (ActionMessage)request.getAttribute("operateMessage");
			if(operateMessage != null){
				messages.add("message",operateMessage);
			}
			
			this.saveMessages(request, messages);//传递国际化消息文本
			return mapping.findForward("queryResults");
		} else {
			
			//创建国际化消息文本对象
			ActionMessage message = new ActionMessage("bank.java.BankBusinessAction.inquiry", null);//查询
			messages.add("operate",message);
			
			this.saveMessages(request, messages);//传递国际化消息文本
	
			return mapping.findForward("error");
		}
	}

	/**
	 * 查询交易记录
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
	public ActionForward queryTransactionRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BusinessForm bf = (BusinessForm) form;
		//Manager mi = null;
		ValueObjectBean vob = new ValueObjectBean();
        
        BeanUtils.copyProperties(vob,bf);
        vob.setUsername((String) request.getSession().getAttribute("userName"));
        
		//mi = ManagerImp1.getInstance();
		PageModel pm = managerDao.getTransactionRecord(vob.getUsername(),vob.getQueryStr(),vob.getPageNo(),vob.getPageSize());
		request.setAttribute("pageMode", pm);
		request.setAttribute("queryStr", bf.getQueryStr());
		return mapping.findForward("transactionRecord");
	}

	/**
	 * 取款
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
	public ActionForward withDrawals(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BusinessForm bf = (BusinessForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message1 = new ActionMessage("bank.java.BankBusinessAction.withDrawals",null);//取款
		messages.add("operate",message1);

		//Manager mi = null;
		int flag = 0;
		
		ValueObjectBean vob = new ValueObjectBean();
        
        BeanUtils.copyProperties(vob,bf);
        vob.setUsername((String) request.getSession().getAttribute("userName"));

		//mi = ManagerImp1.getInstance();

		flag = managerDao.withDrawals(vob.getUsername(),vob.getToMoney());
		
		if (flag == 0) {
			this.saveMessages(request, messages);
			return mapping.findForward("error");
		} else {
			ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.withDrawals_message",bf.getToMoney());//取款成功！共取款 {0} 元！
			request.setAttribute("operateMessage", message2);
			return new ActionForward("/BankBusiness/BankBusinessAction.do?command=inquiry", false);
		}

	}

	/**
	 * 存款
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
	public ActionForward deposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BusinessForm bf = (BusinessForm) form;
		//Manager mi = null;
		int flag = 0;
		ActionMessages messages = new ActionMessages();
		ActionMessage message1 = new ActionMessage("bank.java.BankBusinessAction.deposit",null);//存款
		messages.add("operate",message1);
		ValueObjectBean vob = new ValueObjectBean();
        
        BeanUtils.copyProperties(vob,bf);
        vob.setUsername((String) request.getSession().getAttribute("userName"));
        
		//mi = ManagerImp1.getInstance();

		flag = managerDao.deposit(vob.getUsername(),vob.getToMoney());

		if (flag == 0) {
			this.saveMessages(request, messages);
			return mapping.findForward("error");
		} else {
			ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.deposit_message",bf.getToMoney());//存款成功！共存款 {0} 元！
			request.setAttribute("operateMessage", message2);
			return new ActionForward("/BankBusiness/BankBusinessAction.do?command=inquiry", false);
		}
	}

	/**
	 * 转账
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
	public ActionForward transfer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BusinessForm bf = (BusinessForm) form;
		//Manager mi = null;
		int flag = 0;
		String username = (String) request.getSession().getAttribute("userName");
		String toName = bf.getToName();
		ActionMessages messages = new ActionMessages();
		ActionMessage message1 = new ActionMessage("bank.java.BankBusinessAction.transfer",null);//转账
		messages.add("operate",message1);
		
		ValueObjectBean vob = new ValueObjectBean();
        
        BeanUtils.copyProperties(vob,bf);
        vob.setUsername(username);
        /*
         * 如果转账的对方账户为当前账户，跳转到出错页面；否则进行转账操作
         */
		if (username.equals(toName)) {
			ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.transfer_fail_message",null);//转账账户不能为您自己的账户！
			messages.add("message",message2);
			this.saveMessages(request, messages);
			return mapping.findForward("error");
		} else {
			//mi = ManagerImp1.getInstance();
			flag = managerDao.transfer(vob.getUsername(),vob.getToName(),vob.getToMoney());

			if (flag == 0) {
				return mapping.findForward("error");
			} else {
				ActionMessage message2 = new ActionMessage("bank.java.BankBusinessAction.transfer_message",bf.getToMoney());//转账成功！共转款 {0} 元！
				request.setAttribute("operateMessage", message2);
				return new ActionForward("/BankBusiness/BankBusinessAction.do?command=inquiry", false);
			}
		}
	}
}
