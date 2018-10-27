<%@page contentType = "text/html;charset=utf-8"%>
<html>
<head>
<title>银行系统</title>

<!--
宽     *  高
1920 * 270
480 *  810
1440 * 810
-->

</head>
<!--框架-->
<frameset frameborder="0" rows="10%,90%">

<frame name="top" noresize="noresize" src="show_top.jsp">

<frameset cols="20%,80%">
<frame name="left" noresize="noresize" src="show_left.jsp">
<frame name="main" noresize="noresize" src="show_index.jsp">
</frameset>
<!-- <frameset rows="20%,80%">

<frame name="top" src="show_top.jsp">

<frameset cols="20%,80%">
<frame name="left" src="show_left.jsp">
<frame name="main" src="show_index.jsp">
</frameset> -->
</frameset>
<!--
	<h1>登录成功</h1><hr>
	<h2>离开请<a href="logout.jsp">注销</a></h2>
-->

</html>