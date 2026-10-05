package com.employee.dao;

import com.employee.model.Employee;
import com.employee.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Author   : 10858829
 * Date     : 30 Sept 2026
 * Time     : 9:28:43 am
 * project  : EmployeeManagementJDBC
 */

public class EmployeeDAO {
	
	// --------------------------------------------------
    // 1. ADD EMPLOYEE
    // --------------------------------------------------
 
    public boolean addEmployee(Employee employee) {
 
        String sql = """
                INSERT INTO employee
                (emp_id, emp_name, email, phone, salary,
                 hire_date, dept_id, manager_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, employee.getEmpId());
            ps.setString(2, employee.getEmpName());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getPhone());
            ps.setDouble(5, employee.getSalary());
            ps.setDate(6, Date.valueOf(employee.getHireDate()));
            ps.setInt(7, employee.getDeptId());
 
            if (employee.getManagerId() == null) {
                ps.setNull(8, Types.INTEGER);
            } else {
                ps.setInt(8, employee.getManagerId());
            }
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }
    
    // --------------------------------------------------
    // 2. DISPLAY ALL EMPLOYEES
    // --------------------------------------------------
 
    public List<Employee> getAllEmployees() {
 
        List<Employee> employees = new ArrayList<>();
 
        String sql = "SELECT * FROM employee";
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
 
        } catch (SQLException e) {
            System.out.println("Error retrieving employees: "
                    + e.getMessage());
        }
 
        return employees;
    }
    
    // --------------------------------------------------
    // 3. FIND EMPLOYEE BY ID
    // --------------------------------------------------
 
    public Employee getEmployeeById(int empId) {
 
        String sql =
                "SELECT * FROM employee WHERE emp_id = ?";
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, empId);
 
            try (ResultSet rs = ps.executeQuery()) {
 
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error finding employee: "
                    + e.getMessage());
        }
 
        return null;
    }
    
 // --------------------------------------------------
    // 4. UPDATE EMPLOYEE
    // --------------------------------------------------
 
    public boolean updateEmployee(Employee employee) {
 
        String sql = """
                UPDATE employee
                SET emp_name = ?,
                    email = ?,
                    phone = ?,
                    salary = ?,
                    dept_id = ?,
                    manager_id = ?
                WHERE emp_id = ?
                """;
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, employee.getEmpName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPhone());
            ps.setDouble(4, employee.getSalary());
            ps.setInt(5, employee.getDeptId());
 
            if (employee.getManagerId() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, employee.getManagerId());
            }
 
            ps.setInt(7, employee.getEmpId());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error updating employee: "
                    + e.getMessage());
            return false;
        }
    }
 
 // --------------------------------------------------
    // 5. DELETE EMPLOYEE
    // --------------------------------------------------
 
    public boolean deleteEmployee(int empId) {
 
        String sql =
                "DELETE FROM employee WHERE emp_id = ?";
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, empId);
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error deleting employee: "
                    + e.getMessage());
            return false;
        }
    }
    
 // --------------------------------------------------
    // 6. JOIN - EMPLOYEE + DEPARTMENT
    // --------------------------------------------------
 
    public void displayEmployeeDetails() {
 
        String sql = """
                SELECT e.emp_id,
                       e.emp_name,
                       e.email,
                       e.salary,
                       d.dept_name,
                       d.location
                FROM employee e
                INNER JOIN department d
                ON e.dept_id = d.dept_id
                ORDER BY e.emp_id
                """;
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
 
                System.out.println(
                        rs.getInt("emp_id") + " | " +
                        rs.getString("emp_name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getDouble("salary") + " | " +
                        rs.getString("dept_name") + " | " +
                        rs.getString("location")
                );
            }
 
        } catch (SQLException e) {
            System.out.println("Join query error: "
                    + e.getMessage());
        }
    }
    
 // --------------------------------------------------
    // 7. AGGREGATE QUERY
    // --------------------------------------------------
 
    public void departmentEmployeeCount() {
 
        String sql = """
                SELECT d.dept_id,
                       d.dept_name,
                       COUNT(e.emp_id) AS employee_count
                FROM department d
                LEFT JOIN employee e
                ON d.dept_id = e.dept_id
                GROUP BY d.dept_id, d.dept_name
                ORDER BY d.dept_id
                """;
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
 
                System.out.println(
                        "Department ID: " +
                        rs.getInt("dept_id") +
 
                        ", Department: " +
                        rs.getString("dept_name") +
 
                        ", Employees: " +
                        rs.getInt("employee_count")
                );
            }
 
        } catch (SQLException e) {
            System.out.println("Aggregate query error: "
                    + e.getMessage());
        }
    }
 
    
    // --------------------------------------------------
    // 8. STORED PROCEDURE - GET EMPLOYEE
    // --------------------------------------------------
 
    public void executeGetEmployee(int empId) {
 
        String sql = "{CALL GetEmployee(?)}";
 
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
 
            cs.setInt(1, empId);
 
            try (ResultSet rs = cs.executeQuery()) {
 
                if (rs.next()) {
 
                    System.out.println(
                            "ID: " +
                            rs.getInt("emp_id")
                    );
 
                    System.out.println(
                            "Name: " +
                            rs.getString("emp_name")
                    );
 
                    System.out.println(
                            "Email: " +
                            rs.getString("email")
                    );
 
                    System.out.println(
                            "Salary: " +
                            rs.getDouble("salary")
                    );
                } else {
                    System.out.println("Employee not found.");
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Stored procedure error: "
                    + e.getMessage());
        }
    }
 
    // --------------------------------------------------
    // 9. STORED PROCEDURE - EMPLOYEES BY DEPARTMENT
    // --------------------------------------------------
 
    public void executeEmployeesByDepartment(int deptId) {
 
        String sql =
                "{CALL GetEmployeesByDepartment(?)}";
 
        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
 
            cs.setInt(1, deptId);
 
            try (ResultSet rs = cs.executeQuery()) {
 
                boolean found = false;
 
                while (rs.next()) {
 
                    found = true;
 
                    System.out.println(
                            rs.getInt("emp_id") + " | " +
                            rs.getString("emp_name") + " | " +
                            rs.getString("email") + " | " +
                            rs.getDouble("salary")
                    );
                }
 
                if (!found) {
                    System.out.println(
                            "No employees found."
                    );
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Stored procedure error: "
                    + e.getMessage());
        }
    }
 
    // --------------------------------------------------
    // 10. BATCH UPDATE
    // --------------------------------------------------
 
    public void batchSalaryIncrease(double percentage) {
 
        String sql = """
                UPDATE employee
                SET salary = salary + (salary * ? / 100)
                """;
 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setDouble(1, percentage);
 
            int count = ps.executeUpdate();
 
            System.out.println(
                    "Salary updated for " + count + " employees."
            );
 
        } catch (SQLException e) {
            System.out.println("Batch update error: "
                    + e.getMessage());
        }
    }
    
 // --------------------------------------------------
    // 11. TRANSACTION - TRANSFER EMPLOYEE
    // --------------------------------------------------
 
    public boolean transferEmployee(
            int empId,
            int oldDeptId,
            int newDeptId) {
 
        Connection con = null;
 
        try {
 
            con = DBConnection.getConnection();
 
            con.setAutoCommit(false);
 
            String checkSql =
                    "SELECT emp_id FROM employee " +
                    "WHERE emp_id = ? AND dept_id = ?";
 
            try (PreparedStatement check =
                         con.prepareStatement(checkSql)) {
 
                check.setInt(1, empId);
                check.setInt(2, oldDeptId);
 
                try (ResultSet rs = check.executeQuery()) {
 
                    if (!rs.next()) {
                        System.out.println(
                                "Employee does not belong to old department."
                        );
 
                        con.rollback();
                        return false;
                    }
                }
            }
 
            String updateSql =
                    "UPDATE employee SET dept_id = ? " +
                    "WHERE emp_id = ?";
 
            try (PreparedStatement ps =
                         con.prepareStatement(updateSql)) {
 
                ps.setInt(1, newDeptId);
                ps.setInt(2, empId);
 
                int rows = ps.executeUpdate();
 
                if (rows == 0) {
                    con.rollback();
                    return false;
                }
            }
 
            con.commit();
 
            System.out.println("Employee transferred successfully.");
 
            return true;
 
        } catch (SQLException e) {
 
            System.out.println(
                    "Transaction failed: " + e.getMessage()
            );
 
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackError) {
                System.out.println(
                        "Rollback failed: " +
                        rollbackError.getMessage()
                );
            }
 
            return false;
 
        } finally {
 
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(
                        "Connection closing error: " +
                        e.getMessage()
                );
            }
        }
    }
 
    // --------------------------------------------------
    // 12. TRANSACTION DEMONSTRATION
    // --------------------------------------------------
 
    public void transactionDemo() {
 
        Connection con = null;
 
        try {
 
            con = DBConnection.getConnection();
 
            con.setAutoCommit(false);
 
            String sql =
                    "UPDATE employee " +
                    "SET salary = salary + 1000 " +
                    "WHERE emp_id = ?";
 
            try (PreparedStatement ps =
                         con.prepareStatement(sql)) {
 
                ps.setInt(1, 101);
 
                int rows = ps.executeUpdate();
 
                System.out.println(
                        "Rows updated: " + rows
                );
            }
 
            con.commit();
 
            System.out.println(
                    "Transaction committed successfully."
            );
 
        } catch (SQLException e) {
 
            System.out.println(
                    "Transaction error: " + e.getMessage()
            );
 
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                }
            } catch (SQLException ex) {
                System.out.println(
                        "Rollback error: " +
                        ex.getMessage()
                );
            }
 
        } finally {
 
            try {
 
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
 
            } catch (SQLException e) {
                System.out.println(
                        "Connection closing error: " +
                        e.getMessage()
                );
            }
        }
    }
 
    // --------------------------------------------------
    // HELPER METHOD
    // --------------------------------------------------
 
    private Employee mapEmployee(ResultSet rs)
            throws SQLException {
 
        Employee employee = new Employee();
 
        employee.setEmpId(
                rs.getInt("emp_id")
        );
 
        employee.setEmpName(
                rs.getString("emp_name")
        );
 
        employee.setEmail(
                rs.getString("email")
        );
 
        employee.setPhone(
                rs.getString("phone")
        );
 
        employee.setSalary(
                rs.getDouble("salary")
        );
 
        Date date = rs.getDate("hire_date");
 
        if (date != null) {
            employee.setHireDate(
                    date.toLocalDate()
            );
        }
 
        employee.setDeptId(
                rs.getInt("dept_id")
        );
 
        int managerId =
                rs.getInt("manager_id");
 
        if (rs.wasNull()) {
            employee.setManagerId(null);
        } else {
            employee.setManagerId(managerId);
        }
 
        return employee;
    }

}
