<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>JavaBean</title>
</head>

<body>
	<%@ page import = "tb.testBean" %>
	<% testBean name = new testBean("Naive"); %>
	The Name Is: <%= name.get() %>

	<%
		out.println("<br>Setting Another Name<br>");
		name.set("Excited!");
	%>
	The Name Is: <%= name.get() %>
</html>
