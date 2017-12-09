<%@ page import = "tb.testBean" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Java Bean Test</title>
</head>

<body>
	<% testBean name = new testBean("Naive"); %>
	The Name Is: <%= name.get() %>

	<%
		out.println("<br>Setting Another Name<br>");
		name.set("Excited!");
	%>
	The Name Is: <%= name.get() %>
</html>
