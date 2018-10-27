<%@page contentType = "text/html;charset=utf-8"%>
<%String path = request.getContextPath();%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
		if(password.length<6)
        {
            alert("密码长度不得小于6位！");
            document.LoginForm.password.focus();
            return false;
        }
		var url = "<%=path%>/RegisterLoginController.do?method=register&username="+username+"&password="+password;
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
		/* if(password.length<6)
        {
            alert("密码长度不得小于6位！");
            document.LoginForm.password.focus();
            return false;
        } */
		
		document.LoginForm.submit();
	}
</script>
	<title><spring:message code="bank.login.text.title"/></title><!-- 用户登录 -->
</head>
<body>
	<form name="LoginForm" action="RegisterLoginController.do?method=login" method="post">
		<div class="formStyle">
			<p><spring:message code="bank.login.text.title"/></p><!-- 用户登录 -->
			<div>
			<span class="textStyle"><spring:message code="bank.login.text.user.name"/></span><!-- 用户名： -->
			<input class="inputStyle" type="text" id="username" name="username" placeholder="<spring:message code="bank.login.placeholder.user.name"/>"/><!-- 用户名 -->
			</div>
			
			<div>
			<span class="textStyle" style="padding-left: 10px;"><spring:message code="bank.login.text.user.password"/><!-- 密 码 ： --></span>
			<input class="inputStyle" type="password" name="password" placeholder="<spring:message code="bank.login.placeholder.user.password"/>"/><!-- 密 码  -->
			</div>
			
			<input class="buttonStyle" type="button" name="buttion1" value="<spring:message code="bank.login.button.register"/>" onclick="registerClick()"/><!-- 注册 -->
			<input class="buttonStyle" type="button" name="buttion2" value="<spring:message code="bank.login.button.login"/>" onclick="loginClick()"/><!-- 登录 -->
			<input class="buttonStyle" type="reset" value="<spring:message code="bank.login.button.reset"/>"/><!-- 重置 -->
			
			<div class="languageStyle1">
				<div>
	            <input type="radio" name="role" id="role" value="0" />管理员
	                    &nbsp;&nbsp;&nbsp;
	            <input type="radio" name="role" id="role" value="1" checked/>用户
                </div>    
                <!-- 国际化切换 -->
                <a href="ChangeLanguageAction.do?method=loginChange&locale=zh_CN">中文</a> 
                <a href="ChangeLanguageAction.do?method=loginChange&locale=en_US">English</a>
		    </div>
			
	</form>

</body>
</html>

