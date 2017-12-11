<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<title>Java Bean Test</title>
</head>

<body>
	<%@ page import = "tb.testBean" %>

	<h3>
		<% testBean name = new testBean("Naive"); %>
		The Name Is: <%= name.get() %>

		<%
			out.println("<hr>Setting Another Name<hr>");
			name.set("Excited!");
		%>
		The Name Is: <%= name.get() %>
	</h3>
</html>
