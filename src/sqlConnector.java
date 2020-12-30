import java.sql.Connection;
import java.sql.DriverManager;


public class sqlConnector {

	public static Connection connector() {
		Connection con = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost:3306/libraries?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String password = "";
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return con;
	}
}
