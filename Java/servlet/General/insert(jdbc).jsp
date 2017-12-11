<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="java.io.File"%>
<%@ page import="javax.xml.parsers.*,org.w3c.dom.*"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>JSP DB Insert</title>
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
					<a type="button" class="btn btn-primary center-block" href="/index.jsp">确定</a>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<%
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document configXML = db.parse(new File(
				request.getServletContext().getRealPath("/WEB-INF/config.xml")
			));

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/" +
				configXML.getElementsByTagName("database").item(0).getTextContent() +
				"?useUnicode=true&characterEncoding=UTF-8",
				configXML.getElementsByTagName("username").item(0).getTextContent(),
				configXML.getElementsByTagName("password").item(0).getTextContent()
			);

			request.setCharacterEncoding("UTF-8");
			PreparedStatement s = con.prepareStatement(
				"INSERT INTO " +
					"forum (nickname, message)" +
				"VALUES (?, ?)"
			);
			s.setString(1, request.getParameter("nickname"));
			s.setString(2, request.getParameter("message"));
			s.executeUpdate();

			out.println(
			"<script>" +
				"$(\"#alertModal\").modal();" +
				"$(\"#msg\").html(" +
				"'<h3>留言成功！</h3>'+" +
				"'<h4>'+" +
					"'页面将在 <span id=\"t\">5</span>s 后自动跳回首页'+" +
				"'</h4>'" +
				");" +
				"setInterval(function () {" +
					"t.textContent--;" +
					"if (t.textContent == 0)" +
						"window.location.href = \"/index.jsp\";" +
				"}, 1000);" +
			"</script>"
			);
		} catch(Exception e) {
			out.println(
			"<script>" +
				"$(\"#alertModal\").modal();" +
				"$(\"#msg\").html(" +
				"'<h4>操作出现异常，信息如下：</h4>'+" +
				"'<h5 class=\"text-left\">'+" +
					"'" + e.toString().replaceAll("'", "\\\\'") + "'+" +
				"'</h5>'" +
				");" +
			"</script>"
			);
		}
	%>
</body>
</html>
