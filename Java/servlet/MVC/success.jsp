<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>MVC TEST</title>
</head>

<body>
	<h3><center>留言成功！页面将在 <span id="t">3</span>s 后自动跳回首页</center></h3>
	<script>
		setInterval(function () {
			t.textContent--;
			if (t.textContent == 0)
				window.location.href = "/index.jsp";
		}, 1000);
	</script>
</body>
</html>
