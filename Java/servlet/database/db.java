import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class db {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config = db.parse(new File("config.xml"));

			String database = config.getElementsByTagName("database").item(0).getTextContent();
			String username = config.getElementsByTagName("username").item(0).getTextContent();
			String password = config.getElementsByTagName("password").item(0).getTextContent();

			System.out.println(database);
			System.out.println(username);
			System.out.println(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
