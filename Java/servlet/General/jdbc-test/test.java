import java.sql.*;

public class test {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/" +
				"DATABASE_NAME" +
				"?useUnicode=true&characterEncoding=UTF-8",
				"DATABASE_USERNAME",
				"DATABASE_PASSWORD"
			);

			Statement s = con.createStatement();
			s.executeUpdate(
			"CREATE TABLE test (" +
				"ID INTEGER NOT NULL AUTO_INCREMENT," +
				"PRIMARY KEY(ID)" +
			") ENGINE = InnoDB DEFAULT CHARSET = utf8;"
			);
			s.close(); con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
