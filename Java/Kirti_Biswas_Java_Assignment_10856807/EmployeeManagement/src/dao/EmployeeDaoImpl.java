package dao;

import config.DatabaseConnection;
import model.Employee;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {

    // 1. Add Employee
    // 1. Add Employee
    @Override
    public boolean addEmployee(Employee employee)
            throws SQLException {

        String sql = """
            INSERT INTO employee
            (employee_name, email, salary, department_id)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setString(
                    1, employee.getEmployeeName()
            );

            preparedStatement.setString(
                    2, employee.getEmail()
            );

            preparedStatement.setDouble(
                    3, employee.getSalary()
            );

            preparedStatement.setInt(
                    4, employee.getDepartmentId()
            );

            return preparedStatement.executeUpdate() > 0;
        }
    }

    // 2. View All Employees
    @Override
    public List<Employee> getAllEmployees()
            throws SQLException {

        String sql = """
                SELECT employee_id,
                       employee_name,
                       email,
                       salary,
                       department_id
                FROM employee
                ORDER BY employee_id
                """;

        List<Employee> employees = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Employee employee = new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("employee_name"),
                        resultSet.getString("email"),
                        resultSet.getDouble("salary"),
                        resultSet.getInt("department_id")
                );

                employees.add(employee);
            }
        }

        return employees;
    }

    // 3. Find Employee
    @Override
    public Employee getEmployeeById(int employeeId)
            throws SQLException {

        String sql = """
                SELECT employee_id,
                       employee_name,
                       email,
                       salary,
                       department_id
                FROM employee
                WHERE employee_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, employeeId);

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    return new Employee(
                            resultSet.getInt("employee_id"),
                            resultSet.getString("employee_name"),
                            resultSet.getString("email"),
                            resultSet.getDouble("salary"),
                            resultSet.getInt("department_id")
                    );
                }
            }
        }

        return null;
    }

    // 4. Update Employee
    @Override
    public boolean updateEmployee(Employee employee)
            throws SQLException {

        String sql = """
                UPDATE employee
                SET employee_name = ?,
                    email = ?,
                    salary = ?,
                    department_id = ?
                WHERE employee_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setString(
                    1, employee.getEmployeeName()
            );
            preparedStatement.setString(
                    2, employee.getEmail()
            );
            preparedStatement.setDouble(
                    3, employee.getSalary()
            );
            preparedStatement.setInt(
                    4, employee.getDepartmentId()
            );
            preparedStatement.setInt(
                    5, employee.getEmployeeId()
            );

            return preparedStatement.executeUpdate() > 0;
        }
    }

    // 5. Delete Employee
    @Override
    public boolean deleteEmployee(int employeeId)
            throws SQLException {

        String sql =
                "DELETE FROM employee WHERE employee_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, employeeId);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    // 6. Employee Details with Department using JOIN
    @Override
    public List<String> getEmployeeDetailsWithDepartment()
            throws SQLException {

        String sql = """
                SELECT e.employee_id,
                       e.employee_name,
                       e.email,
                       e.salary,
                       d.department_name
                FROM employee e
                INNER JOIN department d
                    ON e.department_id = d.department_id
                ORDER BY e.employee_id
                """;

        List<String> employeeDetails = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                String details =
                        "ID: " +
                                resultSet.getInt("employee_id") +
                                ", Name: " +
                                resultSet.getString("employee_name") +
                                ", Email: " +
                                resultSet.getString("email") +
                                ", Salary: " +
                                resultSet.getDouble("salary") +
                                ", Department: " +
                                resultSet.getString("department_name");

                employeeDetails.add(details);
            }
        }

        return employeeDetails;
    }

    // 7. Department-wise Employee Count using GROUP BY
    @Override
    public Map<String, Integer>
    getDepartmentWiseEmployeeCount()
            throws SQLException {

        String sql = """
                SELECT d.department_name,
                       COUNT(e.employee_id) AS employee_count
                FROM department d
                LEFT JOIN employee e
                    ON d.department_id = e.department_id
                GROUP BY d.department_id,
                         d.department_name
                ORDER BY d.department_name
                """;

        Map<String, Integer> departmentCounts =
                new LinkedHashMap<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                departmentCounts.put(
                        resultSet.getString("department_name"),
                        resultSet.getInt("employee_count")
                );
            }
        }

        return departmentCounts;
    }

    // 8A. Stored Procedure: Salary Update
    @Override
    public void executeSalaryUpdateProcedure(
            int departmentId,
            double percentage) throws SQLException {

        String sql =
                "{CALL increase_department_salary(?, ?)}";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             CallableStatement callableStatement =
                     connection.prepareCall(sql)) {

            callableStatement.setInt(1, departmentId);
            callableStatement.setDouble(2, percentage);

            callableStatement.execute();
        }
    }

    // 8B. Stored Procedure: Employee Count
    @Override
    public int executeEmployeeCountProcedure(
            int departmentId) throws SQLException {

        String sql =
                "{CALL get_employee_count(?, ?)}";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             CallableStatement callableStatement =
                     connection.prepareCall(sql)) {

            callableStatement.setInt(1, departmentId);

            callableStatement.registerOutParameter(
                    2, Types.INTEGER
            );

            callableStatement.execute();

            return callableStatement.getInt(2);
        }
    }

    // 9. Transfer Employee
    @Override
    public boolean transferEmployee(
            int employeeId,
            int newDepartmentId) throws SQLException {

        String sql = """
                UPDATE employee
                SET department_id = ?
                WHERE employee_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setInt(
                    1, newDepartmentId
            );
            preparedStatement.setInt(
                    2, employeeId
            );

            return preparedStatement.executeUpdate() > 0;
        }
    }

    // 10. Transaction Demonstration
    @Override
    public boolean transferEmployeeWithTransaction(
            int employeeId,
            int oldDepartmentId,
            int newDepartmentId) throws SQLException {

        String updateEmployeeSql = """
                UPDATE employee
                SET department_id = ?
                WHERE employee_id = ?
                  AND department_id = ?
                """;

        String insertHistorySql = """
                INSERT INTO employee_transfer_history
                (employee_id,
                 old_department_id,
                 new_department_id,
                 transfer_date)
                VALUES (?, ?, ?, CURRENT_TIMESTAMP)
                """;

        Connection connection =
                DatabaseConnection.getConnection();

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement updateStatement =
                         connection.prepareStatement(
                                 updateEmployeeSql
                         );
                 PreparedStatement historyStatement =
                         connection.prepareStatement(
                                 insertHistorySql
                         )) {

                updateStatement.setInt(
                        1, newDepartmentId
                );
                updateStatement.setInt(
                        2, employeeId
                );
                updateStatement.setInt(
                        3, oldDepartmentId
                );

                int updatedRows =
                        updateStatement.executeUpdate();

                if (updatedRows == 0) {
                    throw new SQLException(
                            "Employee not found or old department is incorrect."
                    );
                }

                historyStatement.setInt(
                        1, employeeId
                );
                historyStatement.setInt(
                        2, oldDepartmentId
                );
                historyStatement.setInt(
                        3, newDepartmentId
                );

                int insertedRows =
                        historyStatement.executeUpdate();

                if (insertedRows == 0) {
                    throw new SQLException(
                            "Transfer history could not be inserted."
                    );
                }

                connection.commit();

                return true;
            }

        } catch (SQLException exception) {

            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                exception.addSuppressed(
                        rollbackException
                );
            }

            throw exception;

        } finally {

            try {
                connection.setAutoCommit(true);
            } finally {
                connection.close();
            }
        }
    }
}