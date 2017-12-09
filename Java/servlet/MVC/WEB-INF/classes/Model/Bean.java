package Model;
import java.sql.*;

public class Bean {
	public String insert(
		String database, String username, String password,
		String nickname, String message
	) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String conURL =
				"jdbc:mysql://localhost:3306/" + database +
				"?useUnicode=true&characterEncoding=UTF-8";
			Connection con = DriverManager.getConnection(conURL, username, password);

			Statement s = con.createStatement();
			String query =
				"INSERT INTO " +
					"`forum` (`nickname`, `message`)" +
				"VALUES" +
					"('" + nickname + "', '" + message + "')";
			s.executeUpdate(query);

			s.close(); con.close();
		} catch (Exception e) {
			return e.getMessage();
		}
		return "OK";
	}
}
