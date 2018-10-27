<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
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
.middle {
	margin: auto;
	width: 50px;
}

.char {
	margin: 23px 0;
	display: inline-block;
	font-size: 24px;
}

.char:hover {
	color: blue;
}

.span {
	font-size: 30px;
	margin: auto;
	width: 120px;
	display: inherit;
}
</style>
<body>
	<span class="span">业务办理</span>
	<div class="middle">
		<a href="inquiryServlet" target="main"><span class="char">查询</span></a>

		<a href="deposit.jsp" target="main"><span class="char">存款</span></a> <a
			href="withDrawals.jsp" target="main"><span class="char">取款</span></a>

		<a href="transfer.jsp" target="main"><span class="char">转账</span></a>
		<a href="transactionRecordServlet" target="main"><span class="char">交易记录</span></a>

		<a href="secedeServlet" target="_top"><span class="char">退出</span></a>
	</div>
</body>
</html>