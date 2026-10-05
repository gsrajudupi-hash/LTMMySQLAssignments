package com.ems.app;

import com.ems.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            System.out.println(
                    "MySQL connection successful."
            );

            System.out.println(
                    "Connected database: "
                            + connection.getCatalog()
            );

        } catch (SQLException exception) {

            System.out.println(
                    "MySQL connection failed."
            );

            System.out.println(
                    "Reason: " + exception.getMessage()
            );

            System.out.println(
                    "Error code: "
                            + exception.getErrorCode()
            );

            System.out.println(
                    "SQL state: "
                            + exception.getSQLState()
            );
        }
    }
}