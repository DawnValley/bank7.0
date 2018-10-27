
<%@page contentType="text/html;charset=utf-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%String path = request.getContextPath();%>  
<html>
<head>
<title>失败</title>
</head>
<style type="text/css">
	body{
		background-image: url("<%=path%>/images/errorBackground.png");
		background-size: 100% 100%;
		background-attachment:fixed;
		list-style:none;
	}
	.middle {
		margin: auto;
		border-radius: 140px;
		border: 1px solid black;
		position: relative;
		top: 180px;
		width: 280px;
		height: 280px;
		box-shadow: 0 0 500px #bce8f1;
	}
	.h1{
	    text-align: center;
	    position: relative;
	    color:red;
	    left: 18px;
	    top: 39px;
	    padding-bottom: 10px;
	    border-bottom: 2px solid #000;
	    width: 245px;
	    font-size:20px;
	    display: inline-block;
	}
	.h2{
	    position: relative;
	    top: 63px;
	    font-size:18px;
	    color:yellow;
	}
	.h3{
	    display: block;
	    text-align: center;
	    position: relative;
	    top: 90px;
	    font-size: 20px;
	}
</style>
<body>
<div class="middle">

<span class="h1">
<!-- （？操作）失败！ -->
<%-- <html:messages id="operate" message="true" property="operate">
	<bean:write name="operate"/>
</html:messages> --%>
${operate }
<spring:message code="bank.error.text.title"/>
</span>

<!-- 操作结果提示信息 -->
<div class="h2">
<!-- <html:messages id="message" message="true" property="message">
	<bean:write name="message"/>
</html:messages> -->
${message }
</div>

<!-- 异常提示信息 -->
<div class="h2">

<!-- <html:errors/> -->

${ex.message }
<%-- 输出异常，测试时候用
${ex } --%>

</div>

<span class="h3"><!-- 请返回 -->
<input type="button" name="goback" value="<spring:message code="bank.error.text.goback"/>" onClick="javascript:window.history.go(-1);">
</span>

</div>
</body>
</html>