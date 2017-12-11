<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!-- For SQL INSERT parameters -->
<% request.setCharacterEncoding("UTF-8"); %>
<!-- I can't find any elegant solution with JSTL only = =  -->

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

<c:choose>
	<c:when test="${exception != null}">
		<h3>操作出现异常，信息如下：<br>${exception}</h3>
	</c:when>

	<c:otherwise>
		<h1>Data is successfully inserted!</h1>
		<br><a href='./index.jsp'>Return</a>
	</c:otherwise>
</c:choose>
