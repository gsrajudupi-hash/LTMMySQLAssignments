package employee.dao;

import employee.model.Employee;
import employee.util.ConnectionUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // 1. Add employee
    public boolean addEmployee(Employee employee) throws SQLException {

        String sql = """
                INSERT INTO employee
                    (employee_id, employee_name, email, salary, department_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employee.getEmployeeId());
            statement.setString(2, employee.getEmployeeName());
            statement.setString(3, employee.getEmail());
            statement.setDouble(4, employee.getSalary());
            statement.setInt(5, employee.getDepartmentId());

            return statement.executeUpdate() > 0;
        }
    }

    // 2. View all employees
    public List<Employee> getAllEmployees() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String sql = """
                SELECT employee_id,
                       employee_name,
                       email,
                       salary,
                       department_id
                FROM employee
                ORDER BY employee_id
                """;

        try (Connection connection = ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(createEmployee(resultSet));
            }
        }

        return employees;
    }

    // 3. Find employee by ID
    public Employee findEmployee(int employeeId) throws SQLException {

        String sql = """
                SELECT employee_id,
                       employee_name,
                       email,
                       salary,
                       department_id
                FROM employee
                WHERE employee_id = ?
                """;

        try (Connection connection = ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return createEmployee(resultSet);
                }
            }
        }

        return null;
    }

    // 4. Update employee
    public boolean updateEmployee(Employee employee) throws SQLException {

        String sql = """
                UPDATE employee
                SET employee_name = ?,
                    email = ?,
                    salary = ?,
                    department_id = ?
                WHERE employee_id = ?
                """;

        try (Connection connection = ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, employee.getEmployeeName());
            statement.setString(2, employee.getEmail());
            statement.setDouble(3, employee.getSalary());
            statement.setInt(4, employee.getDepartmentId());
            statement.setInt(5, employee.getEmployeeId());

            return statement.executeUpdate() > 0;
        }
    }

    // 5. Delete employee
    public boolean deleteEmployee(int employeeId) throws SQLException {

        String sql = """
                DELETE FROM employee
                WHERE employee_id = ?
                """;

        try (Connection connection = ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            return statement.executeUpdate() > 0;
        }
    }

    // 6. Get employee details with department
    public List<Employee> getEmployeesWithDepartment()
            throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String sql = """
                SELECT e.employee_id,
                       e.employee_name,
                       e.email,
                       e.salary,
                       e.department_id,
                       d.department_name
                FROM employee e
                INNER JOIN department d
                    ON e.department_id = d.department_id
                ORDER BY e.employee_id
                """;

        try (Connection connection = ConnectionUtil.createConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Employee employee = createEmployee(resultSet);

                employee.setDepartmentName(
                        resultSet.getString("department_name")
                );

                employees.add(employee);
            }
        }

        return employees;
    }

    // 8. Execute stored procedure
    public List<Employee> getEmployeesByDepartment(int departmentId)
            throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String procedure = "{CALL GetEmployeesByDepartment(?)}";

        try (Connection connection = ConnectionUtil.createConnection();
             CallableStatement statement =
                     connection.prepareCall(procedure)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Employee employee = new Employee();

                    employee.setEmployeeId(
                            resultSet.getInt("employee_id")
                    );

                    employee.setEmployeeName(
                            resultSet.getString("employee_name")
                    );

                    employee.setEmail(
                            resultSet.getString("email")
                    );

                    employee.setSalary(
                            resultSet.getDouble("salary")
                    );

                    employee.setDepartmentId(departmentId);

                    employee.setDepartmentName(
                            resultSet.getString("department_name")
                    );

                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    // 9. Transfer employee to another department
    public boolean transferEmployee(
            int employeeId,
            int newDepartmentId) throws SQLException {

        String checkDepartmentSql = """
                SELECT department_id
                FROM department
                WHERE department_id = ?
                """;

        String transferSql = """
                UPDATE employee
                SET department_id = ?
                WHERE employee_id = ?
                """;

        try (Connection connection = ConnectionUtil.createConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement checkStatement =
                         connection.prepareStatement(checkDepartmentSql);

                 PreparedStatement transferStatement =
                         connection.prepareStatement(transferSql)) {

                checkStatement.setInt(1, newDepartmentId);

                try (ResultSet resultSet =
                             checkStatement.executeQuery()) {

                    if (!resultSet.next()) {
                        connection.rollback();
                        return false;
                    }
                }

                transferStatement.setInt(1, newDepartmentId);
                transferStatement.setInt(2, employeeId);

                int affectedRows =
                        transferStatement.executeUpdate();

                if (affectedRows > 0) {
                    connection.commit();
                    return true;
                }

                connection.rollback();
                return false;

            } catch (SQLException exception) {
                connection.rollback();
                throw exception;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    // 10. Transaction demonstration
    public boolean transferSalary(
            int fromEmployeeId,
            int toEmployeeId,
            double amount) throws SQLException {

        String deductSql = """
                UPDATE employee
                SET salary = salary - ?
                WHERE employee_id = ?
                  AND salary >= ?
                """;

        String addSql = """
                UPDATE employee
                SET salary = salary + ?
                WHERE employee_id = ?
                """;

        try (Connection connection = ConnectionUtil.createConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement deductStatement =
                         connection.prepareStatement(deductSql);

                 PreparedStatement addStatement =
                         connection.prepareStatement(addSql)) {

                deductStatement.setDouble(1, amount);
                deductStatement.setInt(2, fromEmployeeId);
                deductStatement.setDouble(3, amount);

                int deductedRows =
                        deductStatement.executeUpdate();

                if (deductedRows == 0) {
                    connection.rollback();
                    return false;
                }

                addStatement.setDouble(1, amount);
                addStatement.setInt(2, toEmployeeId);

                int addedRows =
                        addStatement.executeUpdate();

                if (addedRows == 0) {
                    connection.rollback();
                    return false;
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

    // Convert ResultSet row into Employee object
    private Employee createEmployee(ResultSet resultSet)
            throws SQLException {

        return new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("employee_name"),
                resultSet.getString("email"),
                resultSet.getDouble("salary"),
                resultSet.getInt("department_id")
        );
    }
}