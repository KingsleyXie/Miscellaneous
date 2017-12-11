package Model;
import java.sql.*;
import java.util.Vector;

public class Bean {
	public Vector<Msg> messages;

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
			return e.toString();
		}
		return "OK";
	}

	public String getAllMsg(
		String database, String username, String password
	) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String conURL =
				"jdbc:mysql://localhost:3306/" + database +
				"?useUnicode=true&characterEncoding=UTF-8";
			Connection con = DriverManager.getConnection(conURL, username, password);

			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM forum ORDER BY ID DESC");

			messages = new Vector<Msg>();
			while (rs.next())
				messages.add(new Msg(
					rs.getInt("ID"),
					rs.getString("nickname"),
					rs.getString("message"),
					rs.getTimestamp("postTime")
				));

			s.close(); con.close();
		} catch (Exception e) {
			return e.toString();
		}
		return "OK";
	}
}
