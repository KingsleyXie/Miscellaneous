<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>JSP DB</title>
</head>

<body>
	<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/servlet?useUnicode=true&characterEncoding=utf-8" user="DATABASE_USERNAME" password="DATABASE_PASSWORD"/>
	<sql:query dataSource="${snapshot}" var="result">SELECT * from forum;</sql:query>

	<h1>JSP DB: DISPLAY ALL THE DATA</h1>
	<table border="1" width="100%">
	<tr>
		<th>ID</th>
		<th>昵称</th>
		<th>留言</th>
		<th>发布时间</th>
	</tr>
	<c:forEach var="row" items="${result.rows}">
	<tr>
		<td><c:out value="${row.id}"/></td>
		<td><c:out value="${row.nickname}"/></td>
		<td><c:out value="${row.message}"/></td>
		<td><c:out value="${row.postTime}"/></td>
	</tr>
	</c:forEach>
	</table>
</body>
</html>
