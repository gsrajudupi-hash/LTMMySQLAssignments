package employee.dao;

import employee.model.Department;
import employee.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    // 7. Department-wise employee count
    public List<Department> getDepartmentEmployeeCount()
            throws SQLException {

        List<Department> departments =
                new ArrayList<>();

        String sql = """
                SELECT d.department_id,
                       d.department_name,
                       COUNT(e.employee_id) AS employee_count
                FROM department d
                LEFT JOIN employee e
                    ON d.department_id = e.department_id
                GROUP BY d.department_id,
                         d.department_name
                ORDER BY d.department_id
                """;

        try (Connection connection =
                     ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Department department =
                        new Department(
                                resultSet.getInt(
                                        "department_id"
                                ),
                                resultSet.getString(
                                        "department_name"
                                ),
                                resultSet.getInt(
                                        "employee_count"
                                )
                        );

                departments.add(department);
            }
        }

        return departments;
    }
}