<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>成功</title>
</head>
<body>
	<center>
		<c:if test="${fn:length(list) == 1}">
			<c:forEach items="${list}" var="m">
				<td>${m.message }</td>
			</c:forEach>
		</c:if>
		
		<c:if test="${fn:length(list) != 1}">
			<table border="1">
				<tr rowspan="5">数据
				</tr>
				<td>记录id</td>
				<td>交易内容</td>
				<td>交易时间</td>
				<c:forEach items="${list}" var="m">
					<tr>
						<td>id:${m.bId }</td>
						<td>${m.myName }向${m.toName }转出${m.bMoney }元</td>
						<td>${m.bDate }</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</center>
</body>
</html>