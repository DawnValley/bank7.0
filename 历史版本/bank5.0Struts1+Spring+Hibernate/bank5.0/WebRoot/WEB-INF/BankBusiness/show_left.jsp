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
	<span class="span"><bean:message key="bank.show_left.text.title"/><!-- 业务办理 --></span>
	<div class="middle">
		<a href="BankBusiness/BankBusinessAction.do?command=inquiry" target="main"><span class="char"><bean:message key="bank.show_left.hyperlink.inquiry"/><!-- 查询 --></span></a>

		<a href="BankBusiness/BankBusinessAction.do?command=depositPage" target="main"><span class="char"><bean:message key="bank.show_left.hyperlink.deposit"/><!-- 存款 --></span></a> 
		<a href="BankBusiness/BankBusinessAction.do?command=withDrawalsPage" target="main"><span class="char"><bean:message key="bank.show_left.hyperlink.withDrawals"/><!-- 取款 --></span></a>

		<a href="BankBusiness/BankBusinessAction.do?command=transferPage" target="main"><span class="char"><bean:message key="bank.show_left.hyperlink.transfer"/><!-- 转账 --></span></a>
		<a href="BankBusiness/BankBusinessAction.do?command=queryTransactionRecord&pageNo=1&pageSize=10&queryStr=" target="main"><span class="char" 
   ><bean:message key="bank.show_left.hyperlink.queryTransactionRecord"/><!-- 交易记录 --></span></a>

		<a href="RegisterLoginAction.do?command=Secede" target="_top"><span class="char"><bean:message key="bank.show_left.hyperlink.secede"/><!-- 退出 --></span></a>
	</div>
</body>
</html>