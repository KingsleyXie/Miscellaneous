import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class test extends HttpServlet {
	protected void doGet(
		HttpServletRequest req,
		HttpServletResponse res
	) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("<h1>Hello, Servlet!</h1>");
		out.flush();
	}
}
