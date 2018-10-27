<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
<title>失败</title>
</head>
<style type="text/css">
body{
	background-image: url("images/main07.png");
	background-size: 100% 100%;
	background-attachment:fixed;
}
</style>
<body>
<center>
<h1><font color="red">${message}失败!</h1></font><hr>
<h2>${errorMessage}</h2>
<h2>请重新${message}!</h2>
</center>
</body>
</html>