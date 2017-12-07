import java.sql.*;
import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class init {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config = db.parse(new File("config.xml"));

			String database = config.getElementsByTagName("database").item(0).getTextContent();
			String username = config.getElementsByTagName("username").item(0).getTextContent();
			String password = config.getElementsByTagName("password").item(0).getTextContent();

			Class.forName("com.mysql.jdbc.Driver");
			String conURL = "jdbc:mysql://localhost:3306/" + database;
			Connection con = DriverManager.getConnection(conURL, username, password);

			Statement s = con.createStatement();
			String query = null;
			s.executeUpdate(query);

			s.close(); con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
