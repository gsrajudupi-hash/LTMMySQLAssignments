package com.vikranth.ems.model;
import java.math.BigDecimal;
import java.time.LocalDate;
public record Employee(Integer id, String name, String email, BigDecimal salary, LocalDate hireDate, Integer departmentId) {
    public Employee { if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required"); if (salary == null || salary.signum() < 0) throw new IllegalArgumentException("Salary must be non-negative"); }
}