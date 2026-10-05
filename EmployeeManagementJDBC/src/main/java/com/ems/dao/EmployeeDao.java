package com.ems.dao;

import com.ems.model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    boolean addEmployee(Employee employee)
            throws SQLException;

    List<Employee> getAllEmployees()
            throws SQLException;

    Optional<Employee> findEmployeeById(
            int employeeId) throws SQLException;

    boolean updateEmployee(Employee employee)
            throws SQLException;

    boolean deleteEmployee(int employeeId)
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

    void callEmployeesByDepartment(
            int departmentId) throws SQLException;

    boolean callIncreaseSalary(
            int employeeId,
            double percentage) throws SQLException;

    boolean executeProjectTransaction(
            int projectId,
            int employeeId,
            double salaryIncrease) throws SQLException;

    int[] insertEmployeeBatch(
            List<Employee> employees)
            throws SQLException;
}