<%@ page contentType="text/html;charset=utf-8"%>
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
	<h1>注销成功！将在3秒后返回登录页面！</h1>
	<h1>如未返回，请点击<a href="login.jsp">登录</a></h1>
	<%
		session.invalidate();//销毁session
		response.setHeader("refresh","3;URL=login.jsp");
	%>
</center>
</body>
</html>