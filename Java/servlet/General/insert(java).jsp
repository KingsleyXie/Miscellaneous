<%@page import="java.sql.*,java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="javax.xml.parsers.*,org.w3c.dom.*"%>

<%
	request.setCharacterEncoding("UTF8");
	String nickname = request.getParameter("nickname");
	String message = request.getParameter("message");

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

		Statement st = con.createStatement();
		st.executeUpdate(
			"INSERT INTO forum (nickname,message) VALUES('" +
			nickname + "','" + message + "')"
		);
		out.println("<h1>Data is successfully inserted!</h1>");
	} catch(Exception e) {
		out.println(e.getMessage());
	}
	out.println("<br><a href='./index.jsp'>Return</a>");
%>
