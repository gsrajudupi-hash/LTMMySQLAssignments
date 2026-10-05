package com.ems.util;

import java.sql.SQLException;

public final class SqlExceptionUtil {

    private SqlExceptionUtil() {
    }

    public static void display(
            SQLException exception) {

        int errorCode = exception.getErrorCode();

        String message =
                switch (errorCode) {

                    case 1045 ->
                            "Invalid database username or password.";

                    case 1049 ->
                            "Database does not exist.";

                    case 1054 ->
                            "Unknown column. Check column names.";

                    case 1062 ->
                            "Duplicate primary key value.";

                    case 1064 ->
                            "SQL syntax error.";

                    case 1146 ->
                            "Database table does not exist.";

                    case 1451 ->
                            "Cannot delete this employee because "
                                    + "another record references it.";

                    case 1452 ->
                            "Foreign-key violation. Check "
                                    + "DepartmentID, ManagerID "
                                    + "or EmployeeID.";

                    default -> {
                        if (exception.getSQLState() != null
                                && exception.getSQLState()
                                .startsWith("08")) {

                            yield "Database unavailable. "
                                    + "Check whether MySQL "
                                    + "is running.";
                        }

                        yield exception.getMessage();
                    }
                };

        System.out.println(
                "\nDatabase error: " + message
        );

        System.out.println(
                "Error code: " + errorCode
        );

        System.out.println(
                "SQL state: "
                        + exception.getSQLState()
        );
    }
}