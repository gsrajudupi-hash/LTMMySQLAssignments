package com.ems.service;

import com.ems.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee)
            throws SQLException;

    List<Employee> getAllEmployees()
            throws SQLException;

    Employee findEmployee(int employeeId)
            throws SQLException;

    void updateEmployee(Employee employee)
            throws SQLException;

    void deleteEmployee(int employeeId)
            throws SQLException;

    List<Employee> findEmployeesByDepartment(
            int departmentId) throws SQLException;

    void showEmployeeDepartmentDetails()
            throws SQLException;

    void showDepartmentEmployeeCount()
            throws SQLException;

    void showEmployeesWithManagers()
            throws SQLException;

    void showProjectsWithEmployees()
            throws SQLException;

    void executeDepartmentProcedure(
            int departmentId) throws SQLException;

    void executeSalaryProcedure(
            int employeeId,
            double percentage) throws SQLException;

    void executeTransaction(
            int projectId,
            int employeeId,
            double salaryIncrease) throws SQLException;

    void executeBatch(
            List<Employee> employees)
            throws SQLException;
}