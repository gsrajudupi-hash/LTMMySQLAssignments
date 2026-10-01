package org.example.service;

import org.example.dbconnection.DBConnection;
import org.example.model.Employee;

import java.sql.*;

public class EmployeeService {

    //1. Functionality of adding employee using prepared statement
    public void addEmployee(Employee employee) {
        String sql = """
                        INSERT INTO employee(empFirstName,empLastName,empPhoneNumber, empAddress,empBloodGroup,empSalary, empDeptId)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """;

        try{
            // Establishing connection using DBConnection class
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, employee.getEmpFirstName());
            ps.setString(2, employee.getEmpLastName());
            ps.setString(3, employee.getEmpPhoneNumber());
            ps.setString(4, employee.getEmpAddress());
            ps.setString(5, employee.getEmpBloodGroup());
            ps.setString(6, employee.getEmpSalary());
            ps.setInt(7, employee.getEmpDeptId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee added successfully");
            } else {
                System.out.println("Employee can't be added");
            }
            //closing the connection
            con.close();

        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }

    }

    //2. Viewing all employees
    public void viewAllEmployees() {
        String sql = "SELECT * FROM employee";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("========================================");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("empId") + " | " +
                                rs.getString("empFirstName") + " | " +
                                rs.getString("empLastName") + " | " +
                                rs.getString("empPhoneNumber") + " | " +
                                rs.getString("empAddress") + " | " +
                                rs.getString("empBloodGroup") + " | " +
                                rs.getString("empSalary") + " | " +
                                rs.getInt("empDeptId")
                );
            }
            // closing all statements and connections
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }
    }
    //3. Find employee by id
    public void findEmployeeById(int empId) {
        String sql = "SELECT * FROM employee WHERE empId = ?";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("========== EMPLOYEE DETAILS ==========");

                System.out.println("Employee ID    : " + rs.getInt("empId"));
                System.out.println("First Name     : " + rs.getString("empFirstName"));
                System.out.println("Last Name      : " + rs.getString("empLastName"));
                System.out.println("Phone Number   : " + rs.getString("empPhoneNumber"));
                System.out.println("Address        : " + rs.getString("empAddress"));
                System.out.println("Blood Group    : " + rs.getString("empBloodGroup"));
                System.out.println("Salary         : " + rs.getDouble("empSalary"));
                System.out.println("Department ID  : " + rs.getInt("empDeptId"));

                // closing all statements and connections
                rs.close();
                ps.close();
                con.close();
            } else {
                System.out.println("Employee not found with ID : " + empId);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }
    }

    //4. Update employee name by employee id
    public void updateEmployee(int empId, String firstName, String lastName) {
        String sql = "UPDATE employee SET empFirstName = ?, empLastName=? WHERE empId = ?";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setInt(3, empId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully");
            }
            else {
                System.out.println("Employee not found with ID: " + empId);
            }
            // closing all statements and connection
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error occurred with DB: " + e.getMessage());
        }
    }

    //5. Deleting employee by empId
    public void deleteEmployee(int empId) {
        String sql = "DELETE FROM employee WHERE empId = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully");
            } else {
                System.out.println("Employee not found with ID: " + empId);
            }

            // closing all statements and connection
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error occurred with DB: " + e.getMessage());
        }
    }

    //6. getting employee details with their department
    public void employeeDetailsWithDepartment() {
        String sql = """
            SELECT
                e.empId,
                e.empFirstName,
                e.empLastName,
                e.empPhoneNumber,
                e.empSalary,
                d.deptName,
                d.deptLocation
            FROM employee e
            INNER JOIN department d
            ON e.empDeptId = d.deptId
            """;
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("=========================================================================================================");
            System.out.printf("%-5s %-15s %-15s %-15s %-10s %-20s %-15s%n",
                    "ID", "FIRST NAME", "LAST NAME", "PHONE",
                    "SALARY", "DEPARTMENT", "LOCATION");
            System.out.println("=========================================================================================================");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-15s %-15s %-10.2f %-20s %-15s%n",
                        rs.getInt("empId"),
                        rs.getString("empFirstName"),
                        rs.getString("empLastName"),
                        rs.getString("empPhoneNumber"),
                        rs.getDouble("empSalary"),
                        rs.getString("deptName"),
                        rs.getString("deptLocation"));
            }
        } catch (SQLException e) {
            System.out.println(" : " + e.getMessage());
        }
    }

    //7. department wise employee count
    public void departmentWiseEmployeeCount() {
        String sql = """
            SELECT
                d.deptName,
                COUNT(e.empId) as 'Employee Count'
            FROM employee e
            INNER JOIN department d
            ON e.empDeptId = d.deptId
            GROUP BY d.deptName;
            """;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println();
            System.out.println("=========================================");
            System.out.printf("%-25s %-15s%n",
                    "DEPARTMENT",
                    "EMPLOYEE COUNT");
            System.out.println("=========================================");
            while (rs.next()) {
                System.out.printf("%-25s %-15d%n",
                        rs.getString("deptName"),
                        rs.getInt("Employee Count"));
            }
            System.out.println("=========================================");

            // closing all statements and connection
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }
    }
    //8. executing stored procedure
    public void executeStoredProcedureGetEmployeeCountByLocation(String location) {
        String sql = "{" +
                "CALL getEmployeeCountByLocation(?, ?)" +
                "}";
        try{
            Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql);

            // Input parameter
            cs.setString(1, location);
            // Output parameter
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();

            int totalEmployees = cs.getInt(2);

            System.out.println("=================================");
            System.out.println("LOCATION       : " + location);
            System.out.println("TOTAL EMPLOYEE : " + totalEmployees);
            System.out.println("=================================");

            con.close();
        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }
    }

    //stored procedure to get first person with particular blood group
    public void executeStoredProcedureGetEmployeeCountByBloodGroup() {
        String sql = "{" +
                    "CALL getEmployeeCountByBloodGroup()" +
                "}";

        try {
            Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            System.out.println();
            System.out.println("=========================================");
            System.out.printf("%-15s %-20s%n",
                    "BLOOD GROUP",
                    "EMPLOYEE COUNT");
            System.out.println("=========================================");

            while (rs.next()) {
                System.out.printf("%-15s %-20d%n",
                        rs.getString("Blood Group"),
                        rs.getInt("Count of Employees"));
            }
            System.out.println("=========================================");

            rs.close();
            cs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }
    }
    //9. transferring employee department based on emp id
    public void transferEmployee(int empId, int newDeptId) {
        String sql = """
                UPDATE employee SET empDeptId = ? WHERE empId = ?
            """;

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newDeptId);
            ps.setInt(2, empId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Employee transferred successfully");
            } else {
                System.out.println("Employee not found with ID: " + empId);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred with DB : " + e.getMessage());
        }
    }
    //11. transaction demo(executing multiple queries)
    public void transactionDemonstration(int empId, int newDeptId, String newSalary) {
        String transferSql = "UPDATE employee SET empDeptId = ? WHERE empId = ?";
        String salarySql = "UPDATE employee SET empSalary = ? WHERE empId = ?";

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            // Start Transaction
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(transferSql);

            ps1.setInt(1, newDeptId);
            ps1.setInt(2, empId);

            int transferResult = ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(salarySql);

            ps2.setString(1, newSalary);
            ps2.setInt(2, empId);

            int salaryResult = ps2.executeUpdate();

            if (transferResult > 0 && salaryResult > 0) {
                con.commit();
                System.out.println("=================================");
                System.out.println("Transaction Successful");
                System.out.println("Employee transferred");
                System.out.println("Salary updated");
                System.out.println("Changes committed");
                System.out.println("=================================");

            } else {
                con.rollback();
                System.out.println("Transaction Failed");
                System.out.println("Changes rolled back");
            }

            ps1.close();
            ps2.close();

        } catch (SQLException e) {

            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("Transaction rolled back");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Error occurred with DB : " + e.getMessage());

        } finally {

            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
