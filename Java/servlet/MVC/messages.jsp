<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:forEach items="${messages}" var="message">
<h1><c:out value="${message.id}"/></h1>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="panel panel-default">
				<div class="panel-heading msg-heading">
					<div class="panel-title">
						<div class="pull-left msg-no">#<c:out value="${message.id}"/></div>
						<div class="pull-left"><c:out value="${message.nickname}"/></div>
						<div class="pull-right msg-time">
							<i class="fa fa-calendar"></i> <fmt:formatDate pattern="yyyy-MM-dd" value="${message.postTime}"/> <i class="fa fa-clock-o"></i> <fmt:formatDate pattern="HH:mm:ss" value="${message.postTime}"/>
						</div>
					</div>
				</div>

				<div class="row panel-body">
					<div class="col-md-9 msg"><c:out value="${message.message}"/></div>
				</div>
			</div>
		</div>
	</div>
</div>
</c:forEach>
