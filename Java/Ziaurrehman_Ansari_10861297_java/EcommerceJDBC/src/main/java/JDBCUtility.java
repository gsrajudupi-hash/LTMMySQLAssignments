import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Author : 
Date: 
Project : 
*/
public class JDBCUtility {


    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commerce","root","root");
        return con;
    }
}
