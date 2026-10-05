package com.ems.model;

import java.time.LocalDate;

public class Employee {

    private int employeeId;
    private String employeeName;
    private String jobTitle;
    private double salary;
    private LocalDate hireDate;
    private int departmentId;
    private Integer managerId;

    public Employee() {
    }

    public Employee(
            int employeeId,
            String employeeName,
            String jobTitle,
            double salary,
            LocalDate hireDate,
            int departmentId,
            Integer managerId) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.hireDate = hireDate;
        this.departmentId = departmentId;
        this.managerId = managerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(
            String employeeName) {
        this.employeeName = employeeName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(
            LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
            int departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(
            Integer managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {

        String manager =
                managerId == null
                        ? "No Manager"
                        : managerId.toString();

        return String.format(
                "%d | %s | %s | %.2f | %s | Department: %d | Manager: %s",
                employeeId,
                employeeName,
                jobTitle,
                salary,
                hireDate,
                departmentId,
                managerId
        );
    }
}