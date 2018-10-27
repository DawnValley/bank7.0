
<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html>
<head>
<script language="javascript">
	function click1()
	{
		var money = document.depositForm.toMoney.value;
		var reg = /^[0-9]+.?[0-9]*$/;
		if(money=="")
		{
			alert("请输入金额！");
			return false;
		}
		if(reg.test(money))
		{
			document.depositForm.submit();
		}
		else{
			alert("请输入合法数字！");
		}
	}
</script>
</head>
<style type="text/css">
body{
	background-image: url("../images/main04.jpg");
	background-size: 100% 100%;
	background-attachment:fixed;
}
.middle{
       margin: auto;
    border-radius: 200px;
    border: 1px solid black;
    position: relative;
    top: 100px;
    width: 400px;
    height: 400px;
    box-shadow: 0 0 50px orange;
}
.form{
position: relative;
    top: 145px;
}
.input1{
    height: 35px;
    border-radius: 22px;
    opacity: 0.9;
    width: 200px;
}
.button{
   width: 70px;
    height: 37px;
    border-radius: 28px;
    background: border-box;
    font-size: 20px;
    margin: 15px 50px 0 27px;
}
.div1{
	text-align: center;
    width: 400px;
}
</style>
<body>
<div class="middle">
	<form name="depositForm" action="BankBusinessAction.do?command=deposit" method="post" class="form">
			<div class="div1">
				<span style="font-size: 20px"><bean:message key="bank.deposit.text.deposit_money"/><!-- 存款金额： --></span>
				<input type="text" name="toMoney" class="input1">
			</div>
			
			<div class="div1"><input type="button" name="button" value="<bean:message key="bank.business_public.button.submit"/>" onclick="click1()" class="button"><!-- 提交 -->
			<input type="reset" value="<bean:message key="bank.business_public.button.reset"/>" class="button"><!-- 重置 --></div>
		
	</form>
</div>
</body>
</html>