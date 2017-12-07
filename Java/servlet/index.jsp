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
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu" aria-expanded="false">
					<span class="sr-only"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<span class="navbar-brand">留言板</span>
			</div>

			<div class="collapse navbar-collapse" id="menu">
				<ul class="nav navbar-nav navbar-right">
					<li><a href ="#" data-toggle="modal" data-target="#leave-msg-modal">留下足迹</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="modal fade" id="leave-msg-modal" tabindex="-1" aria-labelledby="leave-msg-label" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 class="modal-title" id="leave-msg-label">发表留言</h3>
				</div>

				<form class="form-horizontal" id="leave-msg">
					<div class="modal-body">
						<div class="col-md-12 form-group">
							<label>昵称</label>
							<input type="text" class="form-control" name="nickname" placeholder="请输入姓名或昵称" required>
						</div>

						<div class="col-md-12 form-group">
							<label>留言内容</label>
							<textarea class="form-control" rows="3" name="message" placeholder="请输入留言内容" required></textarea>
						</div>
					</div>
					<div class="clearfix"></div>

					<div class="modal-footer">
						<input type="submit" class="btn btn-lg btn-primary btn-block" value="确定">
					</div>
				</form>
			</div>
		</div>
	</div>

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

	<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
