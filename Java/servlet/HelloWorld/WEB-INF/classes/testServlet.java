import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class testServlet extends HttpServlet {
	protected void doGet(
		HttpServletRequest req,
		HttpServletResponse res
	) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("<title>Servlet Test</title>");
		out.println("<h1>Hello, Servlet!</h1>");
		out.flush();
	}
}
