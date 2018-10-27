
<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html>
<head>
<script language="javascript">
	function click1()
	{
		var money = document.transferForm.toMoney.value;
		var name = document.transferForm.toName.value;
		var reg = /^[0-9]+.?[0-9]*$/;
		if(money=="" || name=="")
		{
			alert("请输入信息！");
			return false;
		}
		if(reg.test(money))
		{
			document.transferForm.submit();
		}
		else{
			alert("请输入合法数字");
		}
	}
</script>
</head>
<style type="text/css">
body{
	background-image: url("../images/main10.jpg");
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
    top: 170px;
    left: 52px;
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
    position: relative;
    top: 105px;
    left: 29px;
    color: red;
}
.div2{
    position: relative;
    top: 135px;
    left: 49px;
    color: red;
}
.div3{
  position: relative;
    top: 150px;
    left: 60px;
}
</style>
<body>
<div class="middle">
		<form name="transferForm" action="BankBusinessAction.do?command=transfer" method="post">
			<div class="div1">
				<span style="font-size: 20px"><bean:message key="bank.transfer.text.account"/><!-- 对方账户名： --></span>
				<input type="text" name="toName" class="input1">
			</div>
			<div class="div2">
				<span style="font-size: 20px"><bean:message key="bank.transfer.text.transfer_money"/><!-- 转账金额： --></span>
				<input type="text" name="toMoney" class="input1">
			</div>
			<div class="div3">
			<input type="button" name="button" value="<bean:message key="bank.business_public.button.submit"/>" onclick="click1()" class="button"><!-- 提交 -->
			<input type="reset" value="<bean:message key="bank.business_public.button.reset"/>" class="button"><!-- 重置 -->
			</div>
	</form>
</div>
</body>
</html>