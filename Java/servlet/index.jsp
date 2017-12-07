<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>JSP DB</title>
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css">
	<style>
		.panel-default > .panel-heading {
			height: 3em;
			background-color: #52acea;
		}
		.msg {
			font-size: 135%;
		}
		.msg-no {
			padding-right: 0.6em;
		}
		@media (max-width: 500px) {
			.msg-heading {
				height: 4.3em !important;
			}
		}
		@media (max-width: 500px) {
			.msg-time {
				clear: both;
			}
		}
	</style>
</head>

<body>
	<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/servlet?useUnicode=true&characterEncoding=utf-8" user="DATABASE_USERNAME" password="DATABASE_PASSWORD"/>
	<sql:query dataSource="${snapshot}" var="result">SELECT * from forum;</sql:query>

	<c:forEach var="row" items="${result.rows}">
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="panel panel-default">
					<div class="panel-heading msg-heading">
						<div class="panel-title">
							<div class="pull-left msg-no"> #<c:out value="${row.id}"/></div>
							<div class="pull-left"><c:out value="${row.nickname}"/></div>
							<div class="pull-right msg-time">
								<i class="fa fa-calendar"></i> <fmt:formatDate pattern="yyyy-MM-dd" value="${row.postTime}"/> <i class="fa fa-clock-o"></i> <fmt:formatDate pattern="HH:mm:ss" value="${row.postTime}"/>
							</div>
						</div>
					</div>
					<div class="row panel-body">
						<div class="col-md-9 msg"><c:out value="${row.message}"/></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:forEach>
</body>
</html>
