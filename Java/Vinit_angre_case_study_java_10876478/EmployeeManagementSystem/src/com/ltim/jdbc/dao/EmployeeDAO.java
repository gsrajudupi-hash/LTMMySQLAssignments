package com.ltim.jdbc.dao;



import java.sql.*;

import com.ltim.jdbc.model.Employee;
import com.ltim.jdbc.util.DBConnection;

public class EmployeeDAO {

    public void addEmployee(Employee emp) {

        String sql =
                "INSERT INTO Employees " +
                "(FirstName,LastName,Salary,DepartmentID,HireDate) " +
                "VALUES(?,?,?,?,CURDATE())";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql))
        {

            pst.setString(1, emp.getFirstName());
            pst.setString(2, emp.getLastName());
            pst.setDouble(3, emp.getSalary());
            pst.setInt(4, emp.getDepartmentId());

            pst.executeUpdate();

            System.out.println(
                    "Employee Added Successfully");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void viewEmployees() {

        String sql =
                "SELECT * FROM Employees";

        try(Connection con =
                    DBConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            ResultSet rs =
                    stmt.executeQuery(sql))
        {

            while(rs.next()) {

                System.out.println(
                        rs.getInt("EmployeeID")
                        + " "
                        + rs.getString("FirstName")
                        + " "
                        + rs.getString("LastName")
                        + " "
                        + rs.getDouble("Salary"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void findEmployee(int empId) {

        String sql =
                "SELECT * FROM Employees " +
                "WHERE EmployeeID=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql))
        {

            pst.setInt(1, empId);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                System.out.println(
                        rs.getString("FirstName")
                        + " "
                        + rs.getDouble("Salary"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(
            int empId,
            double salary) {

        String sql =
                "UPDATE Employees " +
                "SET Salary=? " +
                "WHERE EmployeeID=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql))
        {

            pst.setDouble(1,salary);
            pst.setInt(2,empId);

            pst.executeUpdate();

            System.out.println(
                    "Employee Updated");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(
            int empId) {

        String sql =
                "DELETE FROM Employees " +
                "WHERE EmployeeID=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql))
        {

            pst.setInt(1,empId);

            pst.executeUpdate();

            System.out.println(
                    "Employee Deleted");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void employeeWithDepartment() {

        String sql =
                "SELECT e.EmployeeID," +
                "e.FirstName," +
                "d.DepartmentName " +
                "FROM Employees e " +
                "INNER JOIN Departments d " +
                "ON e.DepartmentID=d.DepartmentID";

        try(Connection con =
                    DBConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            ResultSet rs =
                    stmt.executeQuery(sql))
        {

            while(rs.next()) {

                System.out.println(
                        rs.getInt(1)
                        + " "
                        + rs.getString(2)
                        + " "
                        + rs.getString(3));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void employeeCountByDept() {

        String sql =
                "SELECT d.DepartmentName," +
                "COUNT(*) " +
                "FROM Employees e " +
                "INNER JOIN Departments d " +
                "ON e.DepartmentID=d.DepartmentID " +
                "GROUP BY d.DepartmentName";

        try(Connection con =
                    DBConnection.getConnection();

            Statement stmt =
                    con.createStatement();

            ResultSet rs =
                    stmt.executeQuery(sql))
        {

            while(rs.next()) {

                System.out.println(
                        rs.getString(1)
                        + " "
                        + rs.getInt(2));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void callEmployeeProcedure(
            int empId) {

        try(Connection con =
                    DBConnection.getConnection();

            CallableStatement cs =
                    con.prepareCall(
                            "{CALL GetEmployeeById(?)}"))
        {

            cs.setInt(1,empId);

            ResultSet rs =
                    cs.executeQuery();

            while(rs.next()) {

                System.out.println(
                        rs.getInt("EmployeeID")
                        + " "
                        + rs.getString("FirstName"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void transactionDemo() {

        try {

            Connection con =
                    DBConnection.getConnection();

            con.setAutoCommit(false);

            PreparedStatement pst1 =
                    con.prepareStatement(
                            "UPDATE Employees " +
                            "SET Salary=Salary+1000 " +
                            "WHERE EmployeeID=1");

            pst1.executeUpdate();

            PreparedStatement pst2 =
                    con.prepareStatement(
                            "UPDATE Employees " +
                            "SET Salary=Salary-1000 " +
                            "WHERE EmployeeID=2");

            pst2.executeUpdate();

            con.commit();

            System.out.println(
                    "Transaction Success");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}