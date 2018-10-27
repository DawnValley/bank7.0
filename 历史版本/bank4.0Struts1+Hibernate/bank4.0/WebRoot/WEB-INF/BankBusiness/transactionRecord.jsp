<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%String path = request.getContextPath();%>
<html>
<head>
<script language="javascript">
	function check1() {
		if (document.getElementById("queryStr").value == "") {
			alert("请输入查询关键字");
		}
		else document.formUpdata.submit();
	}
	function topPage() {
		window.self.location = "BankBusinessAction.do?command=queryTransactionRecord&pageNo=${pageMode.topPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}

	function previousPage() {
		window.self.location = "BankBusinessAction.do?command=queryTransactionRecord&pageNo=${pageMode.previousPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}

	function nextPage() {
		window.self.location = "BankBusinessAction.do?command=queryTransactionRecord&pageNo=${pageMode.nextPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}

	function bottomPage() {
		window.self.location = "BankBusinessAction.do?command=queryTransactionRecord&pageNo=${pageMode.bottomPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}";
	}
</script>
</head>

<style type="text/css">
body{
background:#FAFAF1;
}
.div1{
background:#eaea9c;
    display: block;
    width: 624px;
    height: 40px;
    line-height: 40px;
    border-radius: 20px;
}
.div2{
margin-top: 40px;
    border-radius: 20px;
    background: #eaea9c;
    display: block;
    width: 624px;
    height: 40px;
    line-height: 40px;
    color: black;
}
#queryStr{
border-radius:20px;
opacity: 0.8;
}
.input{
border-radius:20px;
opacity: 0.9;
background:#d3d3d3;
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
	<form action="BankBusinessAction.do?command=queryTransactionRecord&pageNo=1&pageSize=${pageMode.pageSize}" name="queryForm" method="post">
		<div class="div1">	
			<span width=100px  align="center"><font color="blue"><bean:message key="bank.transactionRecord.form.record_query"/></font><!-- 记录查询 --></span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span width=150px  align="right"><bean:message key="bank.transactionRecord.form.input"/><!-- 请输入： --></span>
			<input type="text" id="queryStr" name="queryStr" value="${queryStr}" placeholder="<bean:message key="bank.transactionRecord.form.placeholder"/>" size="20" /> 
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
				<td><bean:message key="bank.transactionRecord.table1.number"/><!-- 交易号 --></td>
				<td><bean:message key="bank.transactionRecord.table1.content"/><!-- 交易内容 --></td>
				<td><bean:message key="bank.transactionRecord.table1.time"/><!-- 交易时间 --></td>
				<c:forEach items="${pageMode.list}" var="trb">
					<tr>
						<td>${trb.bId }</td>
						<c:if test="${trb.transactionType eq 'deposit'}">
						<!-- 存款 --><!-- 元 -->
							<td><bean:message key="bank.transactionRecord.table1.deposit"/>&nbsp;${trb.bMoney }&nbsp;<bean:message key="bank.transactionRecord.table1.yuan"/></td>
						</c:if>
						<c:if test="${trb.transactionType eq 'withDrawals'}">
						<!-- 取款 --><!-- 元 -->
							<td><bean:message key="bank.transactionRecord.table1.withDrawals"/>&nbsp;${trb.bMoney }&nbsp;<bean:message key="bank.transactionRecord.table1.yuan"/></td>
						</c:if>
						<c:if test="${trb.transactionType eq 'payment'}">
						<!-- 向 --><!--  转款 --><!-- 元 -->
							<td><bean:message key="bank.transactionRecord.table1.to"/>&nbsp;${trb.toName }&nbsp;<bean:message key="bank.transactionRecord.table1.transfer"/>${trb.bMoney }&nbsp;<bean:message key="bank.transactionRecord.table1.yuan"/></td>
						</c:if>
						<c:if test="${trb.transactionType eq 'proceeds'}">
						<!-- 收到 --> <!-- 转款 --><!-- 元 -->
							<td><bean:message key="bank.transactionRecord.table1.receive"/>&nbsp;${trb.toName }&nbsp;<bean:message key="bank.transactionRecord.table1.transfer"/> ${trb.bMoney }<bean:message key="bank.transactionRecord.table1.yuan"/></td>
						</c:if>
						<td>${trb.bDate }</td>
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