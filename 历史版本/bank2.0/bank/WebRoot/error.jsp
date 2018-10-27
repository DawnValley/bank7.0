<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
<title>失败</title>
</head>
<style type="text/css">
body{
	background-image: url("images/failBackground01.gif");
}
</style>
<body>
<center>
<h1><font color="red">${message}失败!</h1></font><hr>
<h2>请重新<a href="login.jsp">${message}</a>!</h2>
</center>
</body>
</html>