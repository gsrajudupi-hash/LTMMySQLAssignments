package com.ems.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static final Properties PROPERTIES =
            new Properties();

    static {
        try (InputStream inputStream =
                     DatabaseConnection.class
                             .getClassLoader()
                             .getResourceAsStream("db.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "db.properties was not found."
                );
            }

            PROPERTIES.load(inputStream);

        } catch (IOException exception) {
            throw new ExceptionInInitializerError(
                    "Unable to read db.properties: "
                            + exception.getMessage()
            );
        }
    }

    private DatabaseConnection() {
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.username"),
                PROPERTIES.getProperty("db.password")
        );
    }
}