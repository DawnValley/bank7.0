<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<html>
<!-- 添加背景图片 -->
<style type="text/css">
body {
 background-image: url('images/bankBackground01.gif');
 background-size:cover;
}
</style>
<head>
<title><bean:message key="bank.index.text.welcome"/></title><!-- 欢迎来到银行系统！ -->
</head>
<body>
<center>
	<!-- 欢迎来到银行系统！ -->
	<h1><font color="blue"><bean:message key="bank.index.text.welcome"/></font></h1><hr>
	<!-- 为了您享受更优质的服务，请选择 --><!-- 登录 -->
	<h1><bean:message key="bank.index.text.prompt_message"/>&nbsp;<a href="login.jsp"><bean:message key="bank.index.hyperlink.login"/></a></h1>
    <div class="languageStyle1">
        <a href="ChangeLanguageAction.do?language=zh">中文</a> 
        <a href="ChangeLanguageAction.do?language=en">English</a>
    </div>
</center>
</body>
</html>