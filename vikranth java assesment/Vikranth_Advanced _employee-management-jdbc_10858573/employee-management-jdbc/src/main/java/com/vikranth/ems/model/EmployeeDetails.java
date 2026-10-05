package com.vikranth.ems.model;
import java.math.BigDecimal;
public record EmployeeDetails(int employeeId, String employeeName, String email, BigDecimal salary, String departmentName) {}