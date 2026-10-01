
package assessment1.service;

import assessment1.dao.EmployeeDAO;
import assessment1.model.Employee;
import assessment1.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO dao = new EmployeeDAO();

    public void addEmployee(Employee employee) throws SQLException {
        validate(employee);
        dao.addEmployee(employee);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return dao.getAllEmployees();
    }

    public Employee getEmployeeById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be positive.");
        }
        return dao.getEmployeeById(id);
    }

    public boolean updateEmployee(Employee employee)
            throws SQLException {
        validate(employee);
        return dao.updateEmployee(employee);
    }

    public boolean deleteEmployee(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be positive.");
        }
        return dao.deleteEmployee(id);
    }

    public void showEmployeeDepartments() throws SQLException {
        dao.showEmployeeDepartments();
    }

    public void departmentWiseCount() throws SQLException {
        dao.departmentWiseCount();
    }

    public void callDepartmentCount(int departmentId)
            throws SQLException {
        dao.callDepartmentCount(departmentId);
    }

    public void callSalaryUpdate(int employeeId, BigDecimal salary)
            throws SQLException {
        if (salary == null || salary.signum() < 0) {
            throw new IllegalArgumentException(
                    "Salary cannot be negative.");
        }
        dao.callSalaryUpdate(employeeId, salary);
    }

    public void batchUpdateSalary(BigDecimal increment)
            throws SQLException {
        if (increment == null || increment.signum() < 0) {
            throw new IllegalArgumentException(
                    "Increment cannot be negative.");
        }
        dao.batchUpdateSalary(increment);
    }

    // TRANSACTION: transfer an employee to another department
    public void transferEmployee(int employeeId, int newDepartmentId)
            throws SQLException {

        if (employeeId <= 0 || newDepartmentId <= 0) {
            throw new IllegalArgumentException(
                    "IDs must be positive.");
        }

        String sql = """
            UPDATE employee
            SET department_id = ?
            WHERE employee_id = ?
            """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                // Check that the employee exists.
                try (PreparedStatement check = con.prepareStatement(
                        "SELECT employee_id FROM employee " +
                                "WHERE employee_id = ?")) {
                    check.setInt(1, employeeId);

                    try (var rs = check.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Employee not found: " + employeeId);
                        }
                    }
                }

                // Update the department.
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, newDepartmentId);
                    ps.setInt(2, employeeId);

                    int updated = ps.executeUpdate();

                    if (updated != 1) {
                        throw new SQLException(
                                "Employee transfer failed.");
                    }
                }

                con.commit();
                System.out.println("Employee transferred successfully.");

            } catch (SQLException | RuntimeException ex) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    ex.addSuppressed(rollbackEx);
                }
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    private void validate(Employee e) {
        if (e == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null.");
        }
        if (e.getEmployeeId() <= 0) {
            throw new IllegalArgumentException(
                    "Employee ID must be positive.");
        }
        if (e.getEmployeeName() == null ||
                e.getEmployeeName().isBlank()) {
            throw new IllegalArgumentException(
                    "Employee name is required.");
        }
        if (e.getEmail() == null ||
                !e.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException(
                    "Enter a valid email.");
        }
        if (e.getSalary() == null ||
                e.getSalary().signum() < 0) {
            throw new IllegalArgumentException(
                    "Salary cannot be negative.");
        }
        if (e.getDepartmentId() <= 0) {
            throw new IllegalArgumentException(
                    "Department ID must be positive.");
        }
    }
}

