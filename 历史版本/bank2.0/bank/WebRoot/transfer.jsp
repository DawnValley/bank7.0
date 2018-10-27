<%-- <!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
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
<style type="text/css">
body{
	background-image: url("images/main09.jpg");
	background-size: 100% 100%;
	background-attachment:fixed;
}
</style>
</head>
<body>
<center>
	<form name="transferForm" action="transferForm" method="post">
		<table border="0">
			<tr>
				<td>对方账户名：</td>
				<td><input type="text" name="toName"></td>
			</tr>
			<tr>
				<td>转账金额：</td>
				<td><input type="text" name="toMoney"></td>
			</tr>
			<tr>
				<td><input type="button" name="button" value="提交" onclick="click1()"></td>
				<td><input type="reset" value="重置"></td>
			</tr>
		</table>
	</form>
</center>
</body>
</html> --%>
<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
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
	background-image: url("images/main10.jpg");
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
		<form name="transferForm" action="transferForm" method="post">
			<div class="div1">
				<span style="font-size: 20px">对方账户名：</span>
				<input type="text" name="toName" class="input1">
			</div>
			<div class="div2">
				<span style="font-size: 20px">转账金额：</span>
				<input type="text" name="toMoney" class="input1">
			</div>
			<div class="div3">
			<input type="button" name="button" value="提交" onclick="click1()" class="button">
			<input type="reset" value="重置" class="button">
			</div>
	</form>
</div>
</body>
</html>