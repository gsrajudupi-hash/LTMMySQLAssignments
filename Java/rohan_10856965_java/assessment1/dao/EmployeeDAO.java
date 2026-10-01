
package assessment1.dao;

import assessment1.model.Employee;
import assessment1.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // CREATE
    public void addEmployee(Employee e) throws SQLException {
        String sql = """
            INSERT INTO employee
            (employee_id, employee_name, email, salary, department_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getEmployeeId());
            ps.setString(2, e.getEmployeeName());
            ps.setString(3, e.getEmail());
            ps.setBigDecimal(4, e.getSalary());
            ps.setInt(5, e.getDepartmentId());

            ps.executeUpdate();
        }
    }

    // READ ALL
    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employee ORDER BY employee_id";
        List<Employee> employees = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        }
        return employees;
    }

    // READ BY PRIMARY KEY
    public Employee getEmployeeById(int id) throws SQLException {
        String sql =
                "SELECT * FROM employee WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
                return null;
            }
        }
    }

    // UPDATE
    public boolean updateEmployee(Employee e) throws SQLException {
        String sql = """
            UPDATE employee
            SET employee_name = ?, email = ?,
                salary = ?, department_id = ?
            WHERE employee_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getEmployeeName());
            ps.setString(2, e.getEmail());
            ps.setBigDecimal(3, e.getSalary());
            ps.setInt(4, e.getDepartmentId());
            ps.setInt(5, e.getEmployeeId());

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteEmployee(int id) throws SQLException {
        String sql =
                "DELETE FROM employee WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // JOIN: employee details with department
    public void showEmployeeDepartments() throws SQLException {
        String sql = """
            SELECT e.employee_id, e.employee_name,
                   e.salary, d.department_name
            FROM employee e
            INNER JOIN department d
                ON e.department_id = d.department_id
            ORDER BY e.employee_id
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "ID: %d | Name: %s | Salary: %s | Department: %s%n",
                        rs.getInt("employee_id"),
                        rs.getString("employee_name"),
                        rs.getBigDecimal("salary"),
                        rs.getString("department_name"));
            }
        }
    }

    // AGGREGATE: department-wise employee count
    public void departmentWiseCount() throws SQLException {
        String sql = """
            SELECT d.department_name,
                   COUNT(e.employee_id) AS employee_count,
                   COALESCE(AVG(e.salary), 0) AS average_salary
            FROM department d
            LEFT JOIN employee e
                ON d.department_id = e.department_id
            GROUP BY d.department_id, d.department_name
            ORDER BY d.department_name
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "%s | Employees: %d | Average salary: %s%n",
                        rs.getString("department_name"),
                        rs.getInt("employee_count"),
                        rs.getBigDecimal("average_salary"));
            }
        }
    }

    // STORED PROCEDURE 1
    public void callDepartmentCount(int departmentId)
            throws SQLException {

        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(
                     "{CALL sp_department_employee_count(?)}")) {

            cs.setInt(1, departmentId);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            "Department: " +
                                    rs.getString("department_name") +
                                    ", Employee count: " +
                                    rs.getLong("employee_count"));
                }
            }
        }
    }

    // STORED PROCEDURE 2
    public void callSalaryUpdate(int employeeId, BigDecimal salary)
            throws SQLException {

        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(
                     "{CALL sp_update_employee_salary(?, ?)}")) {

            cs.setInt(1, employeeId);
            cs.setBigDecimal(2, salary);

            cs.execute();
            System.out.println("Salary procedure executed.");
        }
    }

    // BATCH PROCESSING
    public void batchUpdateSalary(BigDecimal increment)
            throws SQLException {

        String sql = """
            UPDATE employee
            SET salary = salary + ?
            """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setBigDecimal(1, increment);
                ps.addBatch();

                ps.executeBatch();
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("employee_id"),
                rs.getString("employee_name"),
                rs.getString("email"),
                rs.getBigDecimal("salary"),
                rs.getInt("department_id"));
    }
}

