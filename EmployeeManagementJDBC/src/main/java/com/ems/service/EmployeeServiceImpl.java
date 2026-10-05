package com.ems.service;

import com.ems.dao.EmployeeDao;
import com.ems.dao.EmployeeDaoImpl;
import com.ems.exception.RecordNotFoundException;
import com.ems.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeDao employeeDao =
            new EmployeeDaoImpl();

    @Override
    public void addEmployee(Employee employee)
            throws SQLException {

        validateEmployee(employee);

        employeeDao.addEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees()
            throws SQLException {

        return employeeDao.getAllEmployees();
    }

    @Override
    public Employee findEmployee(int employeeId)
            throws SQLException {

        validateId(employeeId, "Employee ID");

        return employeeDao
                .findEmployeeById(employeeId)
                .orElseThrow(() ->
                        new RecordNotFoundException(
                                "Employee not found."
                        )
                );
    }

    @Override
    public void updateEmployee(Employee employee)
            throws SQLException {

        validateEmployee(employee);

        if (!employeeDao.updateEmployee(employee)) {
            throw new RecordNotFoundException(
                    "Employee not found."
            );
        }
    }

    @Override
    public void deleteEmployee(int employeeId)
            throws SQLException {

        validateId(employeeId, "Employee ID");

        if (!employeeDao.deleteEmployee(employeeId)) {
            throw new RecordNotFoundException(
                    "Employee not found."
            );
        }
    }

    @Override
    public List<Employee> findEmployeesByDepartment(
            int departmentId) throws SQLException {

        validateId(departmentId, "Department ID");

        return employeeDao.findEmployeesByDepartment(
                departmentId
        );
    }

    @Override
    public void showEmployeeDepartmentDetails()
            throws SQLException {

        employeeDao.showEmployeeDepartmentDetails();
    }

    @Override
    public void showDepartmentEmployeeCount()
            throws SQLException {

        employeeDao.showDepartmentEmployeeCount();
    }

    @Override
    public void showEmployeesWithManagers()
            throws SQLException {

        employeeDao.showEmployeesWithManagers();
    }

    @Override
    public void showProjectsWithEmployees()
            throws SQLException {

        employeeDao.showProjectsWithEmployees();
    }

    @Override
    public void executeDepartmentProcedure(
            int departmentId) throws SQLException {

        validateId(departmentId, "Department ID");

        employeeDao.callEmployeesByDepartment(
                departmentId
        );
    }

    @Override
    public void executeSalaryProcedure(
            int employeeId,
            double percentage) throws SQLException {

        validateId(employeeId, "Employee ID");

        if (percentage <= 0) {
            throw new IllegalArgumentException(
                    "Percentage must be greater than zero."
            );
        }

        if (!employeeDao.callIncreaseSalary(
                employeeId,
                percentage
        )) {
            throw new RecordNotFoundException(
                    "Employee not found."
            );
        }
    }

    @Override
    public void executeTransaction(
            int projectId,
            int employeeId,
            double salaryIncrease)
            throws SQLException {

        validateId(projectId, "Project ID");
        validateId(employeeId, "Employee ID");

        if (salaryIncrease < 0) {
            throw new IllegalArgumentException(
                    "Salary increase cannot be negative."
            );
        }

        employeeDao.executeProjectTransaction(
                projectId,
                employeeId,
                salaryIncrease
        );
    }

    @Override
    public void executeBatch(
            List<Employee> employees)
            throws SQLException {

        if (employees == null || employees.isEmpty()) {
            throw new IllegalArgumentException(
                    "Employee list cannot be empty."
            );
        }

        employees.forEach(this::validateEmployee);

        employeeDao.insertEmployeeBatch(employees);
    }

    private void validateEmployee(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException(
                    "Employee cannot be null."
            );
        }

        validateId(
                employee.getEmployeeId(),
                "Employee ID"
        );

        validateId(
                employee.getDepartmentId(),
                "Department ID"
        );

        if (employee.getEmployeeName() == null
                || employee.getEmployeeName().isBlank()) {

            throw new IllegalArgumentException(
                    "Employee name is required."
            );
        }

        if (employee.getJobTitle() == null
                || employee.getJobTitle().isBlank()) {

            throw new IllegalArgumentException(
                    "Job title is required."
            );
        }

        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException(
                    "Salary cannot be negative."
            );
        }

        if (employee.getHireDate() == null) {
            throw new IllegalArgumentException(
                    "Hire date is required."
            );
        }

        if (employee.getManagerId() != null
                && employee.getManagerId()
                .equals(employee.getEmployeeId())) {

            throw new IllegalArgumentException(
                    "Employee cannot be their own manager."
            );
        }
    }

    private void validateId(
            int value,
            String name) {

        if (value <= 0) {
            throw new IllegalArgumentException(
                    name + " must be greater than zero."
            );
        }
    }
}