package com.Database;
import java.sql.*;

public class Driver {
	
    String ip = "127.0.0.1";

    String db = "bookstore";

    String un = "root";

    String password = "";
    
	public Statement CONN() {

		Connection myConn = null;
		
		Statement stm = null;

		try {
			
			String ConnURL = "jdbc:mysql://" + ip + ":3306" + "/" + db;

			myConn = DriverManager.getConnection(ConnURL, un, password);
			
			stm = myConn.createStatement();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return stm;

	}

}
