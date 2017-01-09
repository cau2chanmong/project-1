package SQLService;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLServerService {
	Connection conn=null;
	public SQLServerService() {
		try{
 			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl=
					"jdbc:sqlserver://NAMIT:1433;databaseName=dbquanlithuvien;integratedSecurity=true;";
			conn= DriverManager.getConnection(connectionUrl);
			
 		}catch(Exception ex){
 			ex.printStackTrace();
	}
	}
}
