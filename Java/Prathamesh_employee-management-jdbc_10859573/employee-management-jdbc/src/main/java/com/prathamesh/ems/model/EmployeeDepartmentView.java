package com.prathamesh.ems.model;

import java.math.BigDecimal;

public record EmployeeDepartmentView(int employeeId, String employeeName, String email, BigDecimal salary,
                                     String departmentName, String location) {
}
