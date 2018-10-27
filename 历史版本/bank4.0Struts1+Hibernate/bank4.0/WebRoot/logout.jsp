<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<html>
<head>
<title>注销</title>
</head>
<style type="text/css">
body{
	background-image: url("images/logoutBackground01.jpg");
	background-size: cover;
}
</style>
<body>
<center>
	<h1><bean:message key="bank.logout.text.title"/><!-- 注销成功！将在3秒后返回登录页面！ --></h1>
	<h1><bean:message key="bank.logout.text.message"/><!-- 如未返回，请点击 -->&nbsp;<a href="login.jsp"><bean:message key="bank.logout.hyperlink.login"/><!-- 登录 --></a></h1>
	<%
		session.invalidate();//销毁session
		response.setHeader("refresh","3;URL=login.jsp");
	%>
</center>
</body>
</html>