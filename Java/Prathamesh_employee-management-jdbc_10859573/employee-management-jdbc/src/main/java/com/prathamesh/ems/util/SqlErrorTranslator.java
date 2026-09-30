package com.prathamesh.ems.util;

import java.sql.SQLException;

public final class SqlErrorTranslator {
    private SqlErrorTranslator() {
    }

    public static String friendly(SQLException e) {
        return switch (e.getErrorCode()) {
            case 1045 -> "Invalid database username/password.";
            case 1049 -> "Database does not exist.";
            case 1062 -> "Duplicate primary key or unique value (for example email).";
            case 1064 -> "SQL syntax error.";
            case 1451, 1452 -> "Foreign-key violation. Verify department/employee IDs.";
            case 0 -> "Database unavailable or connection problem: " + e.getMessage();
            default ->
                    "Database error [code=" + e.getErrorCode() + ", state=" + e.getSQLState() + "]: " + e.getMessage();
        };
    }
}
