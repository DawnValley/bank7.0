<!DOCTYPE html>
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="java.util.* ,java.text.SimpleDateFormat"%><!-- 多了分号会出错 -->
<html>
<!-- <style type="text/css">
body {
  background-image: url('images/20081226_23dbfb2df73e54502cb7wOEGI0yBboXI.gif');
  background-repeat:no-repeat;
  background-size:100% 100%;
  background-position:-337px -0px;
  background-attachment:fixed;
}
</style> -->
<style type="text/css">
body{
	background-image: url("images/main02.jpg");
	background-size: 100% 100%;
	background-attachment:fixed;
}
.main{
	color:yellow;
}
.main_con_item1{width:20%;}
.main_con_item2{width:30%;}
.main_con_item3{width:20%;}
.main_con_item4{width:30%;}
</style>
<%!String getDate() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(now);
	}%>
<body>
<center>
<h1 style="
    font-size: 35px;
    color:  beige;
"><spring:message code="bank.show_index.text.title"/><!-- 银行系统 --></h1>


<table class="main" cellpadding="0" cellspacing="0">
	<tr class="">
		<td colspan="4"><spring:message code="bank.show_index.text.server_parameter"/><!-- 服务器的有关参数 --></td>

	<tr id="maindetail1" onmouseover="changecolor(this);"
		onmouseout="changecolor(this);">
		<td class="main_con_item1"><spring:message code="bank.show_index.text.system_name"/><!-- 系统名称： --></td>
		<td class="main_con_item2"><spring:message code="bank.show_index.input.system_name"/><!-- 银行管理系统 --></td>
		<td class="main_con_item3"><spring:message code="bank.show_index.text.Server_system"/><!-- 服务器操作系统： --></td>
		<td class="main_con_item4"><spring:message code="bank.show_index.input.Server_system"/></td>

	<tr id="maindetail1" onmouseover="changecolor(this);"
		onmouseout="changecolor(this);">
		<td class="main_con_item1"><spring:message code="bank.show_index.text.Server"/><!-- 服务器： --></td>
		<td class="main_con_item2"><%=request.getServerName()%></td>
		<td class="main_con_item3"><spring:message code="bank.show_index.text.port"/><!-- 服务器端口： --></td>
		<td class="main_con_item4"><%=request.getServerPort()%></td>
	
	<tr id="maindetail1" onmouseover="changecolor(this);"
		onmouseout="changecolor(this);">
		<td class="main_con_item1"><spring:message code="bank.show_index.text.agreement"/><!-- 使用协议： --></td>
		<td class="main_con_item2"><%=request.getProtocol()%></td>
		<td class="main_con_item3"><spring:message code="bank.show_index.text.version_number"/><!-- 系统版本号： --></td>
		<td class="main_con_item4">3.0</td>

	<tr id="maindetail1" onmouseover="changecolor(this);"
		onmouseout="changecolor(this);">
		<td class="main_con_item1"><spring:message code="bank.show_index.text.time"/><!-- 服务器时间： --></td>
		<td class="main_con_item2"><%=getDate()%></td>
		<td class="main_con_item3"><spring:message code="bank.show_index.text.resolution_ratio"/><!-- 建议分辩率： --></td>
		<td class="main_con_item4">1920*1080</td>

</table>

<table class="main" cellpadding="0" cellspacing="0">
	<tr class="main_con_title">
		<td colspan="4"><spring:message code="bank.show_index.text.technical_support"/><!-- 技术支持 --></td>
	</tr>
	<tr id="maindetail1" onmouseover="changecolor(this);"
		onmouseout="changecolor(this);">
		<td class="main_con_item1"><spring:message code="bank.show_index.text.corporate_name"/><!-- 学校名称： --></td>
		<td class="main_con_item2"><spring:message code="bank.show_index.input.corporate_name"/></td>
		<td class="main_con_item3"><spring:message code="bank.show_index.text.mailing_address"/><!-- 通迅地址： --></td>
		<td class="main_con_item4">aaaaaaaaaa</td>
	</tr>
	<tr id="maindetail1" onmouseover="changecolor(this);"
		onmouseout="changecolor(this);">
		<td class="main_con_item1"><spring:message code="bank.show_index.text.linkman"/><!-- 联系人： --></td>
		<td class="main_con_item2"><spring:message code="bank.show_index.input.linkman"/></td>
		<td class="main_con_item3"><spring:message code="bank.show_index.text.postalcode"/><!-- 邮政编码： --></td>
		<td class="main_con_item4">000000</td>
	</tr>
</table>



</center>
</body>
</html>