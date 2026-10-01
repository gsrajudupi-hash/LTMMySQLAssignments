package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {

    public static Connection createConnection() throws Exception {

        // Optional in JDBC 4+
        // Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecommerce_db",
                "root",
                "root");

        return con;
    }
}
