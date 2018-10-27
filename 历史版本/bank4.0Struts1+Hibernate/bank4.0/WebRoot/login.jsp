<%@page contentType = "text/html;charset=utf-8"%>
<%String path = request.getContextPath();%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<html>
<head>
<style type="text/css">
	body{
		background-image: url("images/loginBackground.jpg");
		background-size: cover;
		margin:0px; padding:0px; width:100%;
	}
	.formStyle{
		margin:100px auto;
		width: 441px;
		line-height: 70px;
	}
	p{
	    font-size: 32px;
		text-align: center;
	}
	.inputStyle{
	    width: 300px;
		height: 45px;
		border-radius: 30px;
		opacity:0.8;
	}
    .buttonStyle{
	    margin-right: 30px;
	    width: 100px;
		height: 40px;
		border-radius: 20px;
		opacity: 0.8;
	}
	.textStyle{
		font-size: 22px;
	}
	.languageStyle1{
	    font-size: 25px;
    	text-align: center;
	}
	a{
		color:#0000ff;
		padding:0 20px;
	}
	a:hover{
		color:red;
	}
</style>
<script language="javascript">
	function registerClick()
	{
		var username = document.LoginForm.username.value;
		//var username = document.getElementById("username").value;
		var password = document.LoginForm.password.value;
		
		if(username=="")
		{
			alert("请输入用户名");
			document.LoginForm.username.focus();
			return false;
		}
		if(password=="")
		{
			alert("请输入密码");
			document.LoginForm.password.focus();
			return false;
		}
		var url = "<%=path%>/RegisterLoginAction.do?command=register&username="+username+"&password="+password;
		window.location.href=url;	
	}
	function loginClick()
	{
		var username = document.LoginForm.username.value;
		var password = document.LoginForm.password.value;
		if(username=="")
		{
			alert("请输入用户名");
			document.LoginForm.username.focus();
			return false;
		}
		if(password=="")
		{
			alert("请输入密码");
			document.LoginForm.password.focus();
			return false;
		}
		
		document.LoginForm.submit();
	}
</script>
	<title><bean:message key="bank.login.text.title"/></title><!-- 用户登录 -->
</head>
<body>
	<form name="LoginForm" action="RegisterLoginAction.do?command=login" method="post">
		<div class="formStyle">
			<p><bean:message key="bank.login.text.title"/></p><!-- 用户登录 -->
			<div><span class="textStyle"><bean:message key="bank.login.text.user.name"/></span><!-- 用户名： -->
			<input class="inputStyle" type="text" id="username" name="username" placeholder="<bean:message key="bank.login.placeholder.user.name"/>"/><!-- 用户名 --></div>
			<div><span class="textStyle" style="padding-left: 10px;"><bean:message key="bank.login.text.user.password"/><!-- 密 码 ： --></span>
			<input class="inputStyle" type="password" name="password" placeholder="<bean:message key="bank.login.placeholder.user.password"/>"/><!-- 密 码  --></div>
			<input class="buttonStyle" type="button" name="buttion1" value="<bean:message key="bank.login.button.register"/>" onclick="registerClick()"/><!-- 注册 -->
			<input class="buttonStyle" type="button" name="buttion2" value="<bean:message key="bank.login.button.login"/>" onclick="loginClick()"/><!-- 登录 -->
			<input class="buttonStyle" type="reset" value="<bean:message key="bank.login.button.reset"/>"/><!-- 重置 -->
			<div class="languageStyle1">
			<a href="ChangeLanguageAction.do?language=zh">中文</a> 
			<a href="ChangeLanguageAction.do?language=en">English</a>
		</div>
			
	</form>

</body>
</html>

