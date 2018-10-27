<%@page contentType = "text/html;charset=utf-8"%>
<html>
<head>
<title>银行管理员系统</title>

</head>
<!--框架-->
<frameset frameborder="0" rows="10%,90%">

<frame name="top" noresize="noresize" src="RegisterLoginAction.do?command=administrator_show_top">

<frameset cols="20%,80%">
<frame name="left" noresize="noresize" src="RegisterLoginAction.do?command=administrator_show_left">
<frame name="main" noresize="noresize" src="RegisterLoginAction.do?command=administrator_show_index">
</frameset>

</frameset>

</html>