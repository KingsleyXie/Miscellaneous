import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import Model.Bean;

public class Controller extends HttpServlet {
	protected void doPost(
		HttpServletRequest req,
		HttpServletResponse res
	) throws ServletException, IOException {
		req.setCharacterEncoding("UTF8");
		res.setCharacterEncoding("UTF8");
		PrintWriter out = res.getWriter();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config = db.parse(new File(
				req.getServletContext().getRealPath("/config.xml")
			));

			Bean bean = new Bean();
			String result = bean.insert(
				config.getElementsByTagName("database").item(0).getTextContent(),
				config.getElementsByTagName("username").item(0).getTextContent(),
				config.getElementsByTagName("password").item(0).getTextContent(),

				req.getParameter("nickname"),
				req.getParameter("message")
			);

			if (result.equals("OK")) {
				res.sendRedirect("./success.jsp");
			} else out.println(result);
		} catch (Exception e) {
			out.println(e.getMessage());
		}
	}

	protected void doGet(
		HttpServletRequest req,
		HttpServletResponse res
	) throws ServletException, IOException {
		req.setCharacterEncoding("UTF8");
		res.setCharacterEncoding("UTF8");
		PrintWriter out = res.getWriter();

		if (req.getParameter("operation").equals("list")) {
			out.println("Get Request!");
		}
	}
}
