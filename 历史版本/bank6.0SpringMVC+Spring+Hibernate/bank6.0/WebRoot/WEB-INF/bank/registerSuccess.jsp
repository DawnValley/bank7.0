<%@page contentType = "text/html;charset=utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<!-- 注册成功 -->
<title><spring:message code="bank.registerSuccess.title"/></title>
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
	<h1><spring:message code="bank.registerSuccess.text.title"/></h1><hr>
	<!-- 请 --><!-- 登录 -->
	<h2><spring:message code="bank.registerSuccess.text.prompt_message"/><a href="RegisterLoginController.do?method=loginPage"><spring:message code="bank.registerSuccess.hyperlink.login"/></a>!</h2>
</center>
</body>
</html>