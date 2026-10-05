package com.ems.dao;

import com.ems.config.DatabaseConnection;
import com.ems.model.Employee;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl
        implements EmployeeDao {

    @Override
    public boolean addEmployee(Employee employee)
            throws SQLException {

        String sql = """
                INSERT INTO employees
                (EmployeeID, EmployeeName, JobTitle,
                 Salary, HireDate, DepartmentID, ManagerID)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            setEmployeeParameters(statement, employee);

            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public List<Employee> getAllEmployees()
            throws SQLException {

        String sql = """
                SELECT EmployeeID, EmployeeName, JobTitle,
                       Salary, HireDate, DepartmentID, ManagerID
                FROM employees
                ORDER BY EmployeeID
                """;

        List<Employee> employees = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(mapEmployee(resultSet));
            }
        }

        return employees;
    }

    @Override
    public Optional<Employee> findEmployeeById(
            int employeeId) throws SQLException {

        String sql = """
                SELECT EmployeeID, EmployeeName, JobTitle,
                       Salary, HireDate, DepartmentID, ManagerID
                FROM employees
                WHERE EmployeeID = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(
                            mapEmployee(resultSet)
                    );
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean updateEmployee(Employee employee)
            throws SQLException {

        String sql = """
                UPDATE employees
                SET EmployeeName = ?,
                    JobTitle = ?,
                    Salary = ?,
                    HireDate = ?,
                    DepartmentID = ?,
                    ManagerID = ?
                WHERE EmployeeID = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1, employee.getEmployeeName()
            );
            statement.setString(
                    2, employee.getJobTitle()
            );
            statement.setDouble(
                    3, employee.getSalary()
            );
            statement.setObject(
                    4, employee.getHireDate()
            );
            statement.setInt(
                    5, employee.getDepartmentId()
            );

            setNullableInteger(
                    statement,
                    6,
                    employee.getManagerId()
            );

            statement.setInt(
                    7, employee.getEmployeeId()
            );

            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public boolean deleteEmployee(int employeeId)
            throws SQLException {

        String sql = """
                DELETE FROM employees
                WHERE EmployeeID = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public List<Employee> findEmployeesByDepartment(
            int departmentId) throws SQLException {

        String sql = """
                SELECT EmployeeID, EmployeeName, JobTitle,
                       Salary, HireDate, DepartmentID, ManagerID
                FROM employees
                WHERE DepartmentID = ?
                ORDER BY EmployeeID
                """;

        List<Employee> employees = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    employees.add(
                            mapEmployee(resultSet)
                    );
                }
            }
        }

        return employees;
    }

    @Override
    public void showEmployeeDepartmentDetails()
            throws SQLException {

        String sql = """
                SELECT e.EmployeeID,
                       e.EmployeeName,
                       e.JobTitle,
                       e.Salary,
                       e.HireDate,
                       d.DepartmentName,
                       d.location
                FROM employees e
                JOIN departments d
                  ON e.DepartmentID = d.DepartmentID
                ORDER BY e.EmployeeID
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.printf(
                        "%d | %s | %s | %.2f | %s | %s | %s%n",
                        resultSet.getInt("EmployeeID"),
                        resultSet.getString("EmployeeName"),
                        resultSet.getString("JobTitle"),
                        resultSet.getDouble("Salary"),
                        resultSet.getObject(
                                "HireDate",
                                LocalDate.class
                        ),
                        resultSet.getString(
                                "DepartmentName"
                        ),
                        resultSet.getString("location")
                );
            }
        }
    }

    @Override
    public void showDepartmentEmployeeCount()
            throws SQLException {

        String sql = """
                SELECT d.DepartmentID,
                       d.DepartmentName,
                       COUNT(e.EmployeeID) AS EmployeeCount
                FROM departments d
                LEFT JOIN employees e
                  ON e.DepartmentID = d.DepartmentID
                GROUP BY d.DepartmentID,
                         d.DepartmentName
                ORDER BY d.DepartmentID
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.printf(
                        "%d | %s | Employees: %d%n",
                        resultSet.getInt("DepartmentID"),
                        resultSet.getString(
                                "DepartmentName"
                        ),
                        resultSet.getInt("EmployeeCount")
                );
            }
        }
    }

    @Override
    public void showEmployeesWithManagers()
            throws SQLException {

        String sql = """
                SELECT e.EmployeeID,
                       e.EmployeeName,
                       e.JobTitle,
                       m.EmployeeName AS ManagerName
                FROM employees e
                LEFT JOIN employees m
                  ON e.ManagerID = m.EmployeeID
                ORDER BY e.EmployeeID
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                String managerName =
                        resultSet.getString("ManagerName");

                if (managerName == null) {
                    managerName = "No Manager";
                }

                System.out.printf(
                        "%d | %s | %s | Manager: %s%n",
                        resultSet.getInt("EmployeeID"),
                        resultSet.getString("EmployeeName"),
                        resultSet.getString("JobTitle"),
                        managerName
                );
            }
        }
    }

    @Override
    public void showProjectsWithEmployees()
            throws SQLException {

        String sql = """
                SELECT p.ProjectID,
                       p.ProjectName,
                       p.StartDate,
                       e.EmployeeName
                FROM projects p
                LEFT JOIN employees e
                  ON p.EmployeeID = e.EmployeeID
                ORDER BY p.ProjectID
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                String employeeName =
                        resultSet.getString("EmployeeName");

                if (employeeName == null) {
                    employeeName = "Unassigned";
                }

                System.out.printf(
                        "%d | %s | %s | %s%n",
                        resultSet.getInt("ProjectID"),
                        resultSet.getString("ProjectName"),
                        resultSet.getObject(
                                "StartDate",
                                LocalDate.class
                        ),
                        employeeName
                );
            }
        }
    }

    @Override
    public void callEmployeesByDepartment(
            int departmentId) throws SQLException {

        String sql =
                "{CALL getEmployeesByDepartment(?)}";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             CallableStatement statement =
                     connection.prepareCall(sql)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                boolean found = false;

                while (resultSet.next()) {
                    found = true;
                    System.out.println(
                            mapEmployee(resultSet)
                    );
                }

                if (!found) {
                    System.out.println(
                            "No employees found."
                    );
                }
            }
        }
    }

    @Override
    public boolean callIncreaseSalary(
            int employeeId,
            double percentage) throws SQLException {

        String sql =
                "{CALL increaseEmployeeSalary(?, ?)}";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             CallableStatement statement =
                     connection.prepareCall(sql)) {

            statement.setInt(1, employeeId);
            statement.setDouble(2, percentage);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next()
                        && resultSet.getInt(
                        "affectedRows"
                ) == 1;
            }
        }
    }

    @Override
    public boolean executeProjectTransaction(
            int projectId,
            int employeeId,
            double salaryIncrease) throws SQLException {

        String updateProjectSql = """
                UPDATE projects
                SET EmployeeID = ?
                WHERE ProjectID = ?
                """;

        String updateEmployeeSql = """
                UPDATE employees
                SET Salary = Salary + ?
                WHERE EmployeeID = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement projectStatement =
                         connection.prepareStatement(
                                 updateProjectSql
                         );
                 PreparedStatement employeeStatement =
                         connection.prepareStatement(
                                 updateEmployeeSql
                         )) {

                projectStatement.setInt(1, employeeId);
                projectStatement.setInt(2, projectId);

                if (projectStatement.executeUpdate() != 1) {
                    throw new SQLException(
                            "Project not found."
                    );
                }

                employeeStatement.setDouble(
                        1, salaryIncrease
                );
                employeeStatement.setInt(
                        2, employeeId
                );

                if (employeeStatement.executeUpdate() != 1) {
                    throw new SQLException(
                            "Employee not found."
                    );
                }

                connection.commit();
                return true;

            } catch (SQLException exception) {

                connection.rollback();
                throw exception;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    @Override
    public int[] insertEmployeeBatch(
            List<Employee> employees)
            throws SQLException {

        String sql = """
                INSERT INTO employees
                (EmployeeID, EmployeeName, JobTitle,
                 Salary, HireDate, DepartmentID, ManagerID)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            try {
                for (Employee employee : employees) {

                    setEmployeeParameters(
                            statement,
                            employee
                    );

                    statement.addBatch();
                }

                int[] results = statement.executeBatch();

                connection.commit();
                return results;

            } catch (SQLException exception) {

                connection.rollback();
                throw exception;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private Employee mapEmployee(
            ResultSet resultSet)
            throws SQLException {

        int managerValue =
                resultSet.getInt("ManagerID");

        Integer managerId =
                resultSet.wasNull()
                        ? null
                        : managerValue;

        return new Employee(
                resultSet.getInt("EmployeeID"),
                resultSet.getString("EmployeeName"),
                resultSet.getString("JobTitle"),
                resultSet.getDouble("Salary"),
                resultSet.getObject(
                        "HireDate",
                        LocalDate.class
                ),
                resultSet.getInt("DepartmentID"),
                managerId
        );
    }

    private void setEmployeeParameters(
            PreparedStatement statement,
            Employee employee)
            throws SQLException {

        statement.setInt(
                1, employee.getEmployeeId()
        );
        statement.setString(
                2, employee.getEmployeeName()
        );
        statement.setString(
                3, employee.getJobTitle()
        );
        statement.setDouble(
                4, employee.getSalary()
        );
        statement.setObject(
                5, employee.getHireDate()
        );
        statement.setInt(
                6, employee.getDepartmentId()
        );

        setNullableInteger(
                statement,
                7,
                employee.getManagerId()
        );
    }

    private void setNullableInteger(
            PreparedStatement statement,
            int parameterIndex,
            Integer value)
            throws SQLException {

        if (value == null) {
            statement.setNull(
                    parameterIndex,
                    Types.INTEGER
            );
        } else {
            statement.setInt(
                    parameterIndex,
                    value
            );
        }
    }
}