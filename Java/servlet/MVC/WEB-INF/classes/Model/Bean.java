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

			PreparedStatement s = con.prepareStatement(
				"INSERT INTO " +
					"forum (nickname, message)" +
				"VALUES (?, ?)"
			);
			s.setString(1, nickname); s.setString(2, message);
			s.executeUpdate();

			s.close(); con.close();
		} catch (Exception e) {
			return e.getMessage();
		}
		return "OK";
	}
}
