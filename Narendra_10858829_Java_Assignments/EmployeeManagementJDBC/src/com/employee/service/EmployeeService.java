package com.employee.service;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
/**
 * Author   : 10858829
 * Date     : 30 Sept 2026
 * Time     : 9:29:07 am
 * project  : EmployeeManagementJDBC
 */


 
import java.util.List;
 
public class EmployeeService {
 
    private final EmployeeDAO employeeDAO;
 
    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }
 
    // Add
    public boolean addEmployee(Employee employee) {
 
        if (employee.getSalary() <= 0) {
            System.out.println(
                    "Salary must be greater than zero."
            );
            return false;
        }
 
        if (employee.getEmpName() == null ||
                employee.getEmpName().isBlank()) {
 
            System.out.println(
                    "Employee name cannot be empty."
            );
 
            return false;
        }
 
        return employeeDAO.addEmployee(employee);
    }
 
    // Get all
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
 
    // Get by ID
    public Employee findEmployeeById(int empId) {
        return employeeDAO.getEmployeeById(empId);
    }
 
    // Update
    public boolean updateEmployee(Employee employee) {
 
        if (employee.getSalary() <= 0) {
            System.out.println(
                    "Salary must be greater than zero."
            );
            return false;
        }
 
        return employeeDAO.updateEmployee(employee);
    }
 
    // Delete
    public boolean deleteEmployee(int empId) {
        return employeeDAO.deleteEmployee(empId);
    }
 
    // Join
    public void displayEmployeeDetails() {
        employeeDAO.displayEmployeeDetails();
    }
 
    // Aggregate
    public void departmentEmployeeCount() {
        employeeDAO.departmentEmployeeCount();
    }
 
    // Stored procedure 1
    public void getEmployeeUsingProcedure(int empId) {
        employeeDAO.executeGetEmployee(empId);
    }
 
    // Stored procedure 2
    public void getEmployeesByDepartment(int deptId) {
        employeeDAO.executeEmployeesByDepartment(deptId);
    }
 
    // Transaction
    public boolean transferEmployee(
            int empId,
            int oldDeptId,
            int newDeptId) {
 
        return employeeDAO.transferEmployee(
                empId,
                oldDeptId,
                newDeptId
        );
    }
 
    // Transaction demo
    public void transactionDemo() {
        employeeDAO.transactionDemo();
    }
 
    // Batch
    public void salaryIncrease(double percentage) {
 
        if (percentage <= 0) {
            System.out.println(
                    "Percentage must be greater than zero."
            );
            return;
        }
 
        employeeDAO.batchSalaryIncrease(percentage);
    }
}
 