package SQLService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
		public static Connection connect() throws SQLException {
			Connection conn = null;
			try{
	 			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String connectionUrl=
						"jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;";
				conn= DriverManager.getConnection(connectionUrl);
				System.out.println("Kết nối thành công");
				
	 		}catch(Exception ex){
	 			System.out.println(ex.getMessage());
		}
			return conn;
		}

}
