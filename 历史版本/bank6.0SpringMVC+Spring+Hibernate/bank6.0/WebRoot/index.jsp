<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%String path = request.getContextPath();%>  
<html>
<!-- 添加背景图片 -->
<style type="text/css">
body {
 background-image: url('images/bankBackground01.gif');
 background-size:cover;
}
</style>
<head>
<title><spring:message code="bank.index.text.welcome"/></title><!-- 欢迎来到银行系统！ -->
</head>
<body>
<center>
    <!-- 欢迎来到银行系统！ -->
    <h1><font color="blue"><spring:message code="bank.index.text.welcome"/></font></h1><hr>
    <!-- 为了您享受更优质的服务，请选择 --><!-- 登录 -->
    <h1><spring:message code="bank.index.text.prompt_message"/>&nbsp;<a href="<%=path%>/RegisterLoginController.do?method=loginPage"><spring:message code="bank.index.hyperlink.login"/></a></h1>
    <div class="languageStyle1">
    
        <!-- 国际化切换 -->
        <a href="ChangeLanguageAction.do?method=indexChange&locale=zh_CN">中文</a> 
        <a href="ChangeLanguageAction.do?method=indexChange&locale=en_US">English</a>
        
    </div>
</center>
</body>
</html>