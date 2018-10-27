<%-- <!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
<script language="javascript">
	function click1()
	{
		var money = document.depositForm.toMoney.value;
		var reg = /^[0-9]+.?[0-9]*$/;
		if(money=="")
		{
			alert("请输入存款金额！");
			return false;
		}
		if(reg.test(money))
		{
			document.depositForm.submit();
		}
		else{
			alert("请输入合法数字");
		}
	}
</script>
<style type="text/css">
body{
	background-image: url("images/main04.jpg");
	background-size: 100% 100%;
	background-attachment:fixed;
}
</style>
</head>
<body>
<center>
	<form name="depositForm" action="accessServlet" method="post">
		<table border="0">
			<tr>
				<td>存款金额：</td>
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
		var money = document.depositForm.toMoney.value;
		var reg = /^[0-9]+.?[0-9]*$/;
		if(money=="")
		{
			alert("请输入取款金额！");
			return false;
		}
		if(reg.test(money))
		{
			document.depositForm.submit();
		}
		else{
			alert("请输入合法数字");
		}
	}
</script>
</head>
<style type="text/css">
body{
	background-image: url("images/main04.jpg");
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
</style>
<body>
<div class="middle">
	<form name="depositForm" action="accessServlet" method="post" class="form">
			<tr>
				<span style="font-size: 20px">存款金额：</span>
				<input type="text" name="toMoney" class="input1">
			</tr>
			<tr>
			<input type="button" name="button" value="提交" onclick="click1()" class="button">
			<input type="reset" value="重置" class="button">
			</tr>
	</form>
</div>
</body>
</html>