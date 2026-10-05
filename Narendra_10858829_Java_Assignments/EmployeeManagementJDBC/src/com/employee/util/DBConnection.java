package com.employee.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Author   : 10858829
 * Date     : 30 Sept 2026
 * Time     : 9:27:53 am
 * project  : EmployeeManagementJDBC
 */


 
public class DBConnection {
 
    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_management";
 
    private static final String USER = "root";
 
    private static final String PASSWORD = "root";
 
    public static Connection getConnection() throws SQLException {
 
        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
