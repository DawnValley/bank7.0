<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>   
<html>
<head>
<title>失败</title>
</head>
<style type="text/css">
body{
	background-image: url("../images/main07.png");
	background-size: 100% 100%;
	background-attachment:fixed;
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
    position: relative;
    left: 18px;
    top: 39px;
    padding-bottom: 10px;
    border-bottom: 2px solid #000;
    width: 160px;
    display: inline-block;
    padding-left: 83px;
}
.h2{
    position: relative;
    top: 44px;
    font-size:20px;
}
.h3{
position: relative;
    top: 90px;
    left: -37px;
    font-size:20px;
}
</style>
<body>
<div class="middle">

<h1><font color="red" class="h1">
<html:message id="msg" message="true" property="message">
<bean:write name="msg"/>
</html:message>
失败!
</h1></font>

<span class="h2">${errorMessage}</span>

<span class="h3">请重新
<html:message id="msg" message="true" property="operate">
<bean:write name="msg"/>
</html:message>
!</span>

</div>
</body>
</html>