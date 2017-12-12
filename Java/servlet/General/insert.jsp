<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!-- For SQL INSERT parameters -->
<% request.setCharacterEncoding("UTF-8"); %>
<!-- I can't find any elegant solution with JSTL only = =  -->

<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>JSP Operation Result</title>
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body>
	<div class="modal fade" id="alertModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h5 class="modal-title text-center">系统提示</h5>
				</div>

				<div class="modal-body text-center" id="msg"></div>

				<div class="modal-footer">
					<a type="button" class="btn btn-primary center-block" href="/">确定</a>
				</div>
			</div>
		</div>
	</div>

	<c:catch var="exception">
		<c:import var="XMLfile" url="/WEB-INF/config.xml"/>
		<x:parse xml="${XMLfile}" var="configXML"/>

		<c:set var="url">
			jdbc:mysql://localhost:3306/<x:out select="$configXML/config/database"/>?useUnicode=true&characterEncoding=utf-8
		</c:set>
		<c:set var="username">
			<x:out select="$configXML/config/username"/>
		</c:set>
		<c:set var="password">
			<x:out select="$configXML/config/password"/>
		</c:set>

		<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver" url="${url}" user="${username}" password="${password}"/>

		<sql:update dataSource="${snapshot}">
			INSERT INTO forum (nickname,message) VALUES(?, ?)
			<sql:param value="${param.nickname}"/>
			<sql:param value="${param.message}"/>
		</sql:update>
	</c:catch>

	<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<c:choose>
		<c:when test="${exception != null}">
			<exception msg="${exception}"></exception>
			<script>
				$("#alertModal").modal();
				$("#msg").html(
				'<h4>操作出现异常，信息如下：</h4>' +
				'<h5 class="text-left">' +
					$("exception").attr("msg") +
				'</h5>'
				);
			</script>
		</c:when>

		<c:otherwise>
			<script>
				$("#alertModal").modal();
				$("#msg").html(
				'<h3>留言成功！</h3>' +
				'<h4>' +
					'页面将在 <span id="t">5</span>s 后自动跳回首页' +
				'</h4>'
				);
				setInterval(function () {
					t.textContent--;
					if (t.textContent == 0)
						window.location.href = "/";
				}, 1000);
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>
