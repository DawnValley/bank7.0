<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%String path = request.getContextPath();%>
<html>
<head>
<script language="javascript">
    /*查询检查*/
	function check1() {
		if (document.getElementById("queryStr").value == "") {
			alert("请输入查询关键字");
		}
		else document.formUpdata.submit();
	}
	/*激活验证*/
	function activationValidate(id, money) {
		if (money < 0) {

			if (window.confirm('当前用户金额小于零，是否激活？')) {
				if (window.confirm('确定激活？')) {
					window.self.location = "AdministratorAction.do?command=stateOperation&id=" + id
						+ "&operation=activation&pageNo=${pageMode.pageNo}&pageSize=${pageMode.pageSize}&querStr=${queryStr}";
					//alert("确定");
					return true;
				} else {
					//alert("取消");
					return false;
				}

			} else {
				//alert("取消");
				return false;
			}
		} else {
			if (window.confirm('确定激活？')) {
				window.self.location = "AdministratorAction.do?command=stateOperation&id=" + id
					+ "&operation=activation&pageNo=${pageMode.pageNo}&pageSize=${pageMode.pageSize}&querStr=${queryStr}";
				//alert("确定");
				return true;
			}
		}
	}
	/*冻结验证*/
	function freezeValidate(money,id){
	   if(money > 0) {

			if (window.confirm('当前用户金额大于零，是否冻结？') ) {
			    if(window.confirm('确定冻结？')){
				    window.self.location = "AdministratorAction.do?command=stateOperation&id="+id
				    +"&operation=freeze&pageNo=${pageMode.pageNo}&pageSize=${pageMode.pageSize}&querStr=${queryStr}";
				    //alert("确定");
	                return true;
			    }
			    else{
				    //alert("取消");
	                return false;
			    }
				
			} else {
				//alert("取消");
				return false;
			}	
	   }
	   else {
			if (window.confirm('确定冻结？')) {
				window.self.location = "AdministratorAction.do?command=stateOperation&id=" + id
					+ "&operation=freeze&pageNo=${pageMode.pageNo}&pageSize=${pageMode.pageSize}&querStr=${queryStr}";
				//alert("确定");
				return true;
			}
		}
    }
    /*首页*/
	function topPage() {
		window.self.location = "AdministratorAction.do?command=userManagementInfo&pageNo=${pageMode.topPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}
    /*上一页*/
	function previousPage() {
		window.self.location = "AdministratorAction.do?command=userManagementInfo&pageNo=${pageMode.previousPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}
    /*下一页*/
	function nextPage() {
		window.self.location = "AdministratorAction.do?command=userManagementInfo&pageNo=${pageMode.nextPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}
    /*尾页*/
	function bottomPage() {
		window.self.location = "AdministratorAction.do?command=userManagementInfo&pageNo=${pageMode.bottomPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}
</script>
</head>

<style type="text/css">
body {
	background: #FAFAF1;
}

.div1 {
	background: #eaea9c;
	display: block;
	width: 624px;
	height: 40px;
	line-height: 40px;
	border-radius: 20px;
}

.div2 {
	margin-top: 40px;
	border-radius: 20px;
	background: #eaea9c;
	display: block;
	width: 624px;
	height: 40px;
	line-height: 40px;
	color: black;
}

#queryStr {
	border-radius: 20px;
	opacity: 0.8;
}

.input {
	border-radius: 20px;
	opacity: 0.9;
	background: #d3d3d3;
}
/* .all{
border: 2px dotted blue;  //solid是实线
    border-radius: 10px;
    padding: 2% 1%;
width:700px;
} */
</style>
<body>
<center>
<div class="all">
	<form action="AdministratorAction.do?command=userManagementInfo&pageNo=1&pageSize=${pageMode.pageSize}" name="queryForm" method="post">
		<div class="div1">	
			<span width=100px  align="center"><font color="blue"><bean:message key="bank.transactionRecord.form.record_query"/></font><!-- 记录查询 --></span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span width=150px  align="right"><bean:message key="bank.transactionRecord.form.input"/><!-- 请输入： --></span>
			<input type="text" id="queryStr" name="queryStr" value="${queryStr}" placeholder="请输入状态信息" size="20" /> 
			<input type="submit" class="input" value="<bean:message key="bank.business_public.button.submit"/>" onClick="check1()" /><!-- 提交 -->
		</div>
		
		<c:if test="${empty pageMode.list}">
			<bean:message key="bank.transactionRecord.text.null_record"/><!-- 您当前无交易记录！ -->
		</c:if>
		
		<c:if test="${not empty pageMode.list}">
			<table border="1" cellpadding="10px" cellspacing="0"
				style="border-collapse:collapse">
				<tr rowspan="5"><bean:message key="bank.transactionRecord.table1.title"/><!-- 数据 -->
				</tr>
				<td>用户id</td>
				<td>用户名</td>
				<td>用户余额</td>
				<td>状态</td>
				<td>创建时间</td>
				<td>操作</td>
				<c:forEach items="${pageMode.list}" var="ub">
					<tr>
						<td>${ub.id }</td>
						<td>${ub.username }</td>
						<td>${ub.money }</td>
						<td>${ub.conditions }</td>
						<td>${ub.udate }</td>
						<td>
						<c:if test="${ub.conditions eq 'activation'}">
						  <%-- <a href="AdministratorAction.do?command=stateOperation&id=${ub.id }&operation=freeze&pageNo=${pageMode.pageNo}&pageSize=${pageMode.pageSize}&querStr=${queryStr}"> --%>
						  <%-- <input class="input" name="freeze" type="button" id="freeze" value="<font color="red">冻结</font>" onClick="freezeValidate(${ub.money},${ub.id })" />  --%>
						  <a href="javascript:freezeValidate(${ub.money},${ub.id })"><font color="red">冻结</font></a>
						  </a>
						</c:if>
						 
						<c:if test="${ub.conditions eq 'freeze'}">
						<%-- <a href="AdministratorAction.do?command=stateOperation&id=${ub.id }&operation=activation&pageNo=${pageMode.pageNo}&pageSize=${pageMode.pageSize}&querStr=${queryStr}">
						<font color="blue">激活</font>
						</a> --%>
						<a href="javascript:activationValidate(${ub.id },${ub.money})">
						<font color="blue">激活</font></a>
                          </a>
                        </c:if>
                        </td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="div2">
			
				<div style="float:left;margin-left:5%">
				<!-- 第 --><!-- 页 --><!-- 共 --><!-- 页 -->
					&nbsp;<bean:message key="bank.transactionRecord.table2.present_page"/>&nbsp;
					<font color="red">${pageMode.pageNo }</font>&nbsp; 
					<bean:message key="bank.transactionRecord.table2.page"/>
					&nbsp;<bean:message key="bank.transactionRecord.table2.total_page"/>&nbsp;
					<font color="red">${pageMode.totalPages }</font>&nbsp;
					<bean:message key="bank.transactionRecord.table2.page"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				</div>
					
					
				<div style="float:right;margin-right:5%">
					<!-- 首页 -->
					<input class="input" name="btnTopPage" title="<bean:message key="bank.transactionRecord.table2.hyperlink.home_page"/>" 
					type="button" id="btnTopPage" value="&nbsp;|&lt;&lt;&nbsp;" onClick="topPage()" /> 
					<!-- 上一页 -->
					<input class="input" name="btnPreviousPage" title="<bean:message key="bank.transactionRecord.table2.hyperlink.previous_page"/>" 
					type="button" id="btnPreviousPage" value="&nbsp;&lt;&nbsp; " onClick="previousPage()" /> 
					<!-- 下一页 -->
					<input class="input" name="btnNext" title="<bean:message key="bank.transactionRecord.table2.hyperlink.next_page"/>" 
					type="button" id="btnNext" value="&nbsp;&gt;&nbsp;" onClick="nextPage()" /> 
					<!-- 尾页 -->
					<input class="input" name="btnBottomPage" title="<bean:message key="bank.transactionRecord.table2.hyperlink.end_page"/>" 
					type="button" id="btnBottomPage" value="&nbsp;&gt;&gt;|&nbsp;" onClick="bottomPage()" />
				</div>
				
			</div>
		</c:if>
	</form>
</div>
</center>
</body>
</html>