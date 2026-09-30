package com.prathamesh.ems.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Employee(Integer id, String name, String email, BigDecimal salary, Integer departmentId,
                       LocalDate hireDate) {
}
