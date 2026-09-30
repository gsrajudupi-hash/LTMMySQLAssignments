package dao;

import model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    // 1. Add Employee
    boolean addEmployee(Employee employee) throws SQLException;

    // 2. View All Employees
    List<Employee> getAllEmployees() throws SQLException;

    // 3. Find Employee by ID
    Employee getEmployeeById(int employeeId) throws SQLException;

    // 4. Update Employee
    boolean updateEmployee(Employee employee) throws SQLException;

    // 5. Delete Employee
    boolean deleteEmployee(int employeeId) throws SQLException;

    // 6. Employee Details with Department using JOIN
    List<String> getEmployeeDetailsWithDepartment()
            throws SQLException;

    // 7. Department-wise Employee Count using GROUP BY
    Map<String, Integer> getDepartmentWiseEmployeeCount()
            throws SQLException;

    // 8. Execute Stored Procedure
    void executeSalaryUpdateProcedure(
            int departmentId,
            double percentage
    ) throws SQLException;

    // Second stored procedure required by assignment
    int executeEmployeeCountProcedure(int departmentId)
            throws SQLException;

    // 9. Transfer Employee to another department
    boolean transferEmployee(
            int employeeId,
            int newDepartmentId
    ) throws SQLException;

    // 10. Transaction Demonstration
    boolean transferEmployeeWithTransaction(
            int employeeId,
            int oldDepartmentId,
            int newDepartmentId
    ) throws SQLException;
}