<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>  
<html>
	<!-- 添加背景图片 -->
	<style type="text/css">
		body {
		 background-image: url('images/registerBackground.jpg');
		 background-size:cover;
		}
		.formStyle{
	        margin:50px auto;
	        width: 600px;
	        line-height: 60px;
	    }
		.formTitle{
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
	        var username = document.registerForm.username.value;
	        var password = document.registerForm.password.value;
	        var password2 = document.registerForm.password2.value;
	        var realName = document.registerForm.realName.value;
	        var email = document.registerForm.email.value;
	
			if (username == "") {
				alert("请输入用户名！");
				username.focus();
				return false;
			}
			if (username.length>16) {
                alert("用户名不得大于16位！");
                username.focus();
                return false;
            }
			if(password.length<6 || password2.length<6)
            {
                alert("密码长度不得小于6位！");
                password.focus();
                return false;
            }
	        
            if(password!=password2){
                alert("两次输入的密码必须一致！");
                document.registerForm.password.focus();
                return false;
			}
	
	        if (realName == "") {
                alert("请输入真实姓名！");
                realName.focus();
                return false;
            }
            if (realName.length>12) {
                alert("真实姓名不得大于12位！");
                realName.focus();
                return false;
            }
            
			if (document.registerForm.sex[0].checked == false && document.registerForm.sex[1].checked == false) {
				alert("请指定性别！");
				document.registerForm.sex[0].focus();
				return false;
			}
			var q1 = email.indexOf("@");
			var q2 = email.indexOf(".");
			if (q1 == -1 || q2 == -1 ||email.length>24) {
				alert("请输入有效的电子邮件地址！");
				email.focus();
				return false;
			}
	
			document.registerForm.submit();  
	    }
	   
	   
	</script>
	<head>
	   <title><spring:message code="bank.register.text.title"/></title><!-- 欢迎注册银行账户！ -->
	</head>
	<body>
	    <form class="formStyle" name="registerForm" action="RegisterLoginController.do?method=register" method="post">
	       <div class="formTitle"><spring:message code="bank.register.form.title"/></div>
	       <div>
	            <span class="textStyle" style="padding-left: 127px;">
	               <spring:message code="bank.login.text.user.name"/>
	            </span><!-- 用户名： -->
                <input class="inputStyle" type="text" id="username" name="username" 
                    placeholder="<spring:message code="bank.login.placeholder.user.name"/>"/><!-- 用户名 -->
           </div>
            
            <div>
                <span class="textStyle" style="padding-left: 137px;">
                    <spring:message code="bank.login.text.user.password"/><!-- 密 码 ： -->
                </span>
                <input class="inputStyle" type="password" name="password" 
                    placeholder="<spring:message code="bank.login.placeholder.user.password"/>"/><!-- 密 码  -->
            </div>
            
	       <div>
	           <span class="textStyle" style="padding-left: 50px;">
                    <spring:message code="bank.register.text.user.password2"/><!-- 重复输入密 码 ： -->
                </span>
                <input class="inputStyle" type="password" name="password2" 
                    placeholder="<spring:message code="bank.register.placeholder.user.password2"/>"/><!-- 密 码  -->
	       </div>
	       
	       <div>
	           <span class="textStyle" style="padding-left: 108px;">
                    <spring:message code="bank.register.text.user.real_name"/><!-- 真实姓名：  -->
                </span>
                <input class="inputStyle" type="text" name="realName" 
                    placeholder="<spring:message code="bank.register.placeholder.user.real_name"/>"/><!-- 请输入真实姓名  -->
	       </div>
	       
	       <div>
                <span class="textStyle" style="padding-left: 154px;">
                    <spring:message code="bank.register.text.user.sex"/><!-- 性别：  -->
                </span>
                <input type="radio" name="sex" value="男"><spring:message code="bank.register.radio.user.boy"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="女"><spring:message code="bank.register.radio.user.girl"/>
           </div>
	       
	       <div>
               <span class="textStyle" style="padding-left: 154px;">
                    <spring:message code="bank.register.text.user.email"/><!-- 邮箱：  -->
                </span>
                <input class="inputStyle" type="text" name="email" 
                    placeholder="<spring:message code="bank.register.placeholder.user.email"/>"/><!-- 请输入邮箱  -->
           </div>
           
	       <div style="padding-left: 148px;padding-top: 40px;">
				<input class="buttonStyle" type="button" name="buttion2" value="<spring:message 
				    code="bank.business_public.button.submit"/>" onclick="registerClick()"/><!-- 提交 -->
				<input class="buttonStyle" type="reset" value="<spring:message 
				    code="bank.login.button.reset"/>"/><!-- 重置 -->
				<input  class="buttonStyle" type="button" name="goback" value="<spring:message 
				    code="bank.error.text.goback"/>" onClick="javascript:window.history.go(-1);">
           </div>
	    </form>
	        
	</body>
</html>