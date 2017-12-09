package Model;
import java.sql.*;
import java.util.Vector;

class Msg {
	public int id;
	public Date postTime;
	public String nickname, message;

	public Msg(
		int id, Date postTime,
		String nickname, String message
	) {
		this.id = id; this.postTime = postTime;
		this.nickname = nickname; this.message = message;
	}
}

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
			return e.getMessage();
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
			ResultSet rs = s.executeQuery("SELECT * FROM forum");

			messages = new Vector<Msg>();
			while (rs.next())
				messages.add(new Msg(
					rs.getInt("ID"),
					rs.getDate("postTime"),
					rs.getString("nickname"),
					rs.getString("message")
				));

			s.close(); con.close();
		} catch (Exception e) {
			return e.getMessage();
		}
		return "OK";
	}
}
