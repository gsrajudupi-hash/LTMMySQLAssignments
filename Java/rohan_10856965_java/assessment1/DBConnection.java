package assessment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_management_system";

    private static final String USER = "root";

    // Prefer an environment variable instead of hardcoding passwords.
    private static final String PASSWORD =
            System.getenv("root");

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "DB_PASSWORD environment variable is not configured");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
