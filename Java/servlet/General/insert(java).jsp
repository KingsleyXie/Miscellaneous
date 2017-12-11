<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="java.io.File"%>
<%@ page import="javax.xml.parsers.*,org.w3c.dom.*"%>

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
			"<h1>Data is successfully inserted!</h1>" +
			"<br><a href='./index.jsp'>Return</a>"
		);
	} catch(Exception e) {
		out.println(
			"<h3>操作出现异常，信息如下：<br>" +
			e.getMessage() + "</h3>"
		);
	}
%>
