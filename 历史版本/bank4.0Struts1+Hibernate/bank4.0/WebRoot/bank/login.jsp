<%@page contentType = "text/html;charset=utf-8"%>
<%String path = request.getContextPath();%>
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
		width: 400px;
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
	<title>登录</title>
</head>
<body>
	<form name="LoginForm" action="RegisterLoginAction.do?command=login" method="post">
	<%-- <form name="LoginForm" action="<%=path%>/LoginAction.do" method="post"> --%>
		<div class="formStyle">
			<p>用户登录</p>
			<span class="textStyle">用户名：</span>
			<input class="inputStyle" type="text" id="username" name="username" placeholder="用户名">
			<span class="textStyle" style="padding-left: 10px;">密 码 ：</span>
			<input class="inputStyle" type="password" name="password" placeholder="密码">
			<input class="buttonStyle" type="button" name="buttion1" value="注册" onclick="registerClick()">
			<input class="buttonStyle" type="button" name="buttion2" value="登录" onclick="loginClick()">
			<!--<input class="buttonStyle" type="submit" name="buttion2" value="登录">-->
			<input class="buttonStyle" type="reset" value="重置">
		</div>
	</form>
</body>
</html>

