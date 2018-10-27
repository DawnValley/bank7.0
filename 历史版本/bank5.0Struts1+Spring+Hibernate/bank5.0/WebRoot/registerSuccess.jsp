<%@page contentType = "text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<html>
<head>
<!-- 注册成功 -->
<title><bean:message key="bank.registerSuccess.title"/></title>
</head>
<style type="text/css">
body{
	background-image: url("images/main05.jpg");
	background-size: 100% 100%;
	background-attachment:fixed;
}
</style>
<body>
<center>
	<!-- 注册成功 -->
	<h1><bean:message key="bank.registerSuccess.text.title"/></h1><hr>
	<!-- 请 --><!-- 登录 -->
	<h2><bean:message key="bank.registerSuccess.text.prompt_message"/><a href="login.jsp"><bean:message key="bank.registerSuccess.hyperlink.login"/></a>!</h2>
</center>
</body>
</html>