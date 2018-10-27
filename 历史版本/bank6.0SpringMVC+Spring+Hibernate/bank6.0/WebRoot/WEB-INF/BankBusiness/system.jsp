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

<frame name="top" noresize="noresize" src="PageController.do?method=show_top">

<frameset cols="20%,80%">
<frame name="left" noresize="noresize" src="PageController.do?method=show_left">
<frame name="main" noresize="noresize" src="PageController.do?method=show_index">
</frameset>

</html>