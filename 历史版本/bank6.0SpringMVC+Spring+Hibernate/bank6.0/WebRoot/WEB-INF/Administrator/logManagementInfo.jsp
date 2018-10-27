<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%String path = request.getContextPath();%>
<html>
<head>
<script language="javascript">
    /*查询检查*/
    function check1() {
        var type = document.getElementsByName("type");
        var typeValue = "-1";
	    for(var i=0;i<type.length;i++){
	        if(type[i].checked){
	            typeValue = type[i].value;       
	        }
	    }
        if (document.getElementById("queryStr").value == "" && typeValue == "-1") {
            alert("请输入查询关键字或者选择查询类型！");
        }
        else document.formUpdata.submit();
    }
    
    /*首页*/
    function topPage() {
        window.self.location = "AdministratorController.do?method=logManagementInfo&pageNo=${pageMode.topPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}&type=${type}";
    }
    /*上一页*/
    function previousPage() {
        window.self.location = "AdministratorController.do?method=logManagementInfo&pageNo=${pageMode.previousPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}&type=${type}";
    }
    /*下一页*/
    function nextPage() {
        window.self.location = "AdministratorController.do?method=logManagementInfo&pageNo=${pageMode.nextPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}&type=${type}";
    }
    /*尾页*/
    function bottomPage() {
        window.self.location = "AdministratorController.do?method=logManagementInfo&pageNo=${pageMode.bottomPageNo}&pageSize=${pageMode.pageSize}&queryStr=${queryStr}&type=${type}";
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
    width: 1000px;
    height: 80px;
    line-height: 40px;
    border-radius: 20px;
}

.div2 {
    margin-top: 40px;
    border-radius: 20px;
    background: #eaea9c;
    display: block;
    width: 1000px;
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
    <form action="AdministratorController.do?method=logManagementInfo&pageNo=1&pageSize=${pageMode.pageSize}" name="queryForm" method="post">
        <div class="div1">  
            <div>
	            <span width=100px  align="center"><font color="blue"><spring:message code="bank.transactionRecord.form.record_query"/></font><!-- 记录查询 --></span>
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            <span width=150px  align="right"><spring:message code="bank.transactionRecord.form.input"/><!-- 请输入： --></span>
	            <input type="text" id="queryStr" name="queryStr" value="${queryStr}" placeholder="请输入操作者姓名" size="20" /> 
	            <input type="submit" class="input" value="<spring:message code="bank.business_public.button.submit"/>" onClick="check1()" /><!-- 提交 -->
	            <input type="reset" class="input" value="<spring:message code="bank.business_public.button.reset"/>"/><!-- 重置 -->
            </div>
            <input type="radio" name="type" id="type" value="Administrator login" />管理员登录
            &nbsp;&nbsp;&nbsp;
            <input type="radio" name="type" id="type" value="Administrator actions" />管理员操作
            &nbsp;&nbsp;&nbsp;
            <input type="radio" name="type" id="type" value="Administrator secede" />管理员退出
            &nbsp;&nbsp;&nbsp;
            <input type="radio" name="type" id="type" value="register" />用户注册
            &nbsp;&nbsp;&nbsp;
            <input type="radio" name="type" id="type" value="User login" />用户登录
            &nbsp;&nbsp;&nbsp;
            <input type="radio" name="type" id="type" value="User actions" />用户操作
            &nbsp;&nbsp;&nbsp;
            <input type="radio" name="type" id="type" value="User secede" />用户退出
            &nbsp;&nbsp;&nbsp;
      
                      
            
        </div>
        
        <c:if test="${empty pageMode.list}">
            <font color="red" size="30">查询到的日志信息为空！</font>
        </c:if>
        
        <c:if test="${not empty pageMode.list}">
            <table border="1" cellpadding="10px" cellspacing="0"
                style="border-collapse:collapse">
                <tr rowspan="5"><spring:message code="bank.transactionRecord.table1.title"/><!-- 数据 -->
                </tr>
                <td>日志id</td>
                <td>操作者姓名</td>
                <td>日志类型</td>
                <td>详细信息</td>
                <td>创建时间</td>
                <c:forEach items="${pageMode.list}" var="lb">
                    <tr>
                        <td>${lb.logId }</td>
                        <td>${lb.userName }</td>
                        <td>${lb.logType }</td>
                        <td>${lb.logDetail }</td>
                        <td>${lb.logTime }</td> 
                    </tr>
                </c:forEach>
            </table>
            
            <div class="div2">
            
                <div style="float:left;margin-left:5%">
                <!-- 第 --><!-- 页 --><!-- 共 --><!-- 页 -->
                    &nbsp;<spring:message code="bank.transactionRecord.table2.present_page"/>&nbsp;
                    <font color="red">${pageMode.pageNo }</font>&nbsp; 
                    <spring:message code="bank.transactionRecord.table2.page"/>
                    &nbsp;<spring:message code="bank.transactionRecord.table2.total_page"/>&nbsp;
                    <font color="red">${pageMode.totalPages }</font>&nbsp;
                    <spring:message code="bank.transactionRecord.table2.page"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                </div>
                    
                    
                <div style="float:right;margin-right:5%">
                    <!-- 首页 -->
                    <input class="input" name="btnTopPage" title="<spring:message code="bank.transactionRecord.table2.hyperlink.home_page"/>" 
                    type="button" id="btnTopPage" value="&nbsp;|&lt;&lt;&nbsp;" onClick="topPage()" /> 
                    <!-- 上一页 -->
                    <input class="input" name="btnPreviousPage" title="<spring:message code="bank.transactionRecord.table2.hyperlink.previous_page"/>" 
                    type="button" id="btnPreviousPage" value="&nbsp;&lt;&nbsp; " onClick="previousPage()" /> 
                    <!-- 下一页 -->
                    <input class="input" name="btnNext" title="<spring:message code="bank.transactionRecord.table2.hyperlink.next_page"/>" 
                    type="button" id="btnNext" value="&nbsp;&gt;&nbsp;" onClick="nextPage()" /> 
                    <!-- 尾页 -->
                    <input class="input" name="btnBottomPage" title="<spring:message code="bank.transactionRecord.table2.hyperlink.end_page"/>" 
                    type="button" id="btnBottomPage" value="&nbsp;&gt;&gt;|&nbsp;" onClick="bottomPage()" />
                </div>
                
            </div>
        </c:if>
    </form>
</div>
</center>
</body>
</html>