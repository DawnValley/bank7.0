<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<style type="text/css">
body {
	background-image: url("images/left.jpg");
	background-size: 100% 100%;
	background-attachment: fixed;
}

a {
	text-decoration: none;
	color: black;
}
.char {
color:black;
    width: 200px;
    text-align: center;
	margin: auto;
    padding: 23px 0;
	display: block;
	font-size: 24px;
}

.char:hover {
	color: blue;
}

.span {
	color:black;
    text-align: center;
	font-size: 30px;
	margin: auto;
	width: 200px;
	display: inherit;
}
</style>
<body>
	<span class="span">用户管理</span>
	<div class="middle">
		<a href="Administrator/AdministratorAction.do?command=userManagementInfo&pageNo=1&pageSize=10&queryStr=" target="main"><span class="char">用户管理</span></a>
        <a href="Administrator/AdministratorAction.do?command=logManagementInfo&pageNo=1&pageSize=10&queryStr=&type=" target="main"><span class="char">日志管理</span></a>

		<a href="RegisterLoginAction.do?command=Secede" target="_top"><span class="char"><bean:message key="bank.show_left.hyperlink.secede"/><!-- 退出 --></span></a>
	</div>
</body>
</html>