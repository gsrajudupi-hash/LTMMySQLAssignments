package com.employee.model;

import java.time.LocalDate;
/**
 * Author   : 10858829
 * Date     : 30 Sept 2026
 * Time     : 9:26:13 am
 * project  : EmployeeManagementJDBC
 */


 
public class Employee {
 
    private int empId;
    private String empName;
    private String email;
    private String phone;
    private double salary;
    private LocalDate hireDate;
    private int deptId;
    private Integer managerId;
 
    public Employee() {
    }
 
    public Employee(int empId, String empName, String email,
                    String phone, double salary,
                    LocalDate hireDate, int deptId,
                    Integer managerId) {
 
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.hireDate = hireDate;
        this.deptId = deptId;
        this.managerId = managerId;
    }
 
    public int getEmpId() {
        return empId;
    }
 
    public void setEmpId(int empId) {
        this.empId = empId;
    }
 
    public String getEmpName() {
        return empName;
    }
 
    public void setEmpName(String empName) {
        this.empName = empName;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPhone() {
        return phone;
    }
 
    public void setPhone(String phone) {
        this.phone = phone;
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
 
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
 
    public int getDeptId() {
        return deptId;
    }
 
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
 
    public Integer getManagerId() {
        return managerId;
    }
 
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
 
    @Override
    public String toString() {
        return "ID: " + empId +
                ", Name: " + empName +
                ", Email: " + email +
                ", Phone: " + phone +
                ", Salary: " + salary +
                ", Hire Date: " + hireDate +
                ", Department ID: " + deptId +
                ", Manager ID: " + managerId;
    }
}
 