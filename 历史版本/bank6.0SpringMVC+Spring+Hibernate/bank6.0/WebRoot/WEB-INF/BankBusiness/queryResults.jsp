
<%@page contentType="text/html;charset=utf-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<html>
<head>
<title>成功</title>
<style type="text/css">
	body {
		background-image: url("../images/main06.png");
		background-size: 100% 100%;
		background-attachment: fixed;
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
	
	.span1 {
		position: relative;
		top: 30px;
		font-size: 20px;
		border-bottom: 1px solid black;
		padding-bottom: 5px;
	}
	
	.span2 {
		position: relative;
		top: 110px;
		width: 228px;
		display: inline-block;
		font-size: 18px;
	}
</style>
</head>
<body>
	<center>
		<div class="middle">
			<span class="span1">
				<!-- 您的余额为 {0} 元 -->
				<!-- <html:messages id="msg" message="true" property="money">
					<bean:write name="msg"/>
				</html:messages> -->
			    ${balance }
			</span> 
			
			<span class="span2">
				<!-- 操作信息 -->
				<!-- <html:messages id="message" message="true" property="message">
					<bean:write name="message"/>
				</html:messages> -->
				${operateMessage }
			</span>
			
		</div>
	</center>
</body>
</html>