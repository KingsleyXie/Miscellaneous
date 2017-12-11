import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class reader {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config = db.parse(new File("config.xml"));

			System.out.println(
				config.getElementsByTagName("database").item(0).getTextContent()
			);
			System.out.println(
				config.getElementsByTagName("username").item(0).getTextContent()
			);
			System.out.println(
				config.getElementsByTagName("password").item(0).getTextContent()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
