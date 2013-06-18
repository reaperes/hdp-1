import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;


public class DB {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://reaperes";
	public static final String DB_TABLE = "elec";
	
	private static final String USER = "";
	private static final String PASSWORD = "";
	
	private static DB _instance = null;
	
	private Connection conn = null;
	private Statement stmt = null;
	
	private DB() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("Database has connected.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DB getInstance() {
		if (_instance == null)
			_instance = new DB();
		return _instance;
	}
	
	public void insertPower(Timestamp t, double watt) {
		try {
			stmt = conn.createStatement();
			String query = "INSERT INTO " + DB_TABLE + " VALUES ('" + t.toString() + "', " + String.valueOf(watt) + ");";
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
				System.out.println("Database closed successfully.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
