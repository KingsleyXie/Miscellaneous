<%@page import="java.sql.*,java.util.*"%>
<%
	request.setCharacterEncoding("UTF8");
	String nickname = request.getParameter("nickname");
	String message = request.getParameter("message");
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/servlet?useUnicode=true&characterEncoding=UTF-8",
			"DATABASE_USERNAME", "DATABASE_PASSWORD"
		);
		Statement st=con.createStatement();
		int i=st.executeUpdate(
			"INSERT INTO forum (nickname,message) VALUES('" +
			nickname + "','" + message + "')"
		);
		out.println("<h1>Data is successfully inserted!</h1>");
		out.println("<a href=\"./index.jsp\">Return</a>");
	} catch(Exception e) {
		e.printStackTrace();
	}
%>
