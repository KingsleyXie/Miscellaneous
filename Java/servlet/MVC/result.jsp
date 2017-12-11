<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>MVC TEST</title>
</head>

<body>
	<c:choose>
		<c:when test="${fail != null}">
			出现错误：<h3>${fail}</h3>
		</c:when>

		<c:otherwise>
			<h3><center>留言成功！页面将在 <span id="t">5</span>s 后自动跳回首页</center></h3>
			<script>
				setInterval(function () {
					t.textContent--;
					if (t.textContent == 0)
						window.location.href = "/index.jsp";
				}, 1000);
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>
