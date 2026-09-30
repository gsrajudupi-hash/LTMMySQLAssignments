package com.prathamesh.ems.service;

import com.prathamesh.ems.dao.EmployeeDao;
import com.prathamesh.ems.exception.ValidationException;
import com.prathamesh.ems.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeService {
    private final EmployeeDao dao;
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public EmployeeService(EmployeeDao dao) {
        this.dao = dao;
    }

    private void validate(Employee e) {
        if (e.name() == null || e.name().isBlank()) throw new ValidationException("Name is required");
        if (e.email() == null || !EMAIL.matcher(e.email()).matches())
            throw new ValidationException("Valid email is required");
        if (e.salary() == null || e.salary().signum() < 0) throw new ValidationException("Salary cannot be negative");
        if (e.departmentId() == null || e.departmentId() <= 0)
            throw new ValidationException("Department ID must be positive");
        if (e.hireDate() == null) throw new ValidationException("Hire date is required");
    }

    public int add(Employee e) {
        validate(e);
        return dao.add(e);
    }

    public List<Employee> all() {
        return dao.findAll();
    }

    public Employee byId(int id) {
        positive(id, "Employee ID");
        return dao.findById(id);
    }

    public void update(Employee e) {
        if (e.id() == null) throw new ValidationException("Employee ID required");
        validate(e);
        if (!dao.update(e))
            throw new com.prathamesh.ems.exception.RecordNotFoundException("Employee " + e.id() + " not found");
    }

    public void delete(int id) {
        positive(id, "Employee ID");
        if (!dao.delete(id))
            throw new com.prathamesh.ems.exception.RecordNotFoundException("Employee " + id + " not found");
    }

    private void positive(int n, String field) {
        if (n <= 0) throw new ValidationException(field + " must be positive");
    }

    public List<Employee> minSalary(BigDecimal x) {
        if (x.signum() < 0) throw new ValidationException("Minimum salary cannot be negative");
        return dao.findByMinimumSalary(x);
    }

    public List<EmployeeDepartmentView> details() {
        return dao.findDetailsWithDepartment();
    }

    public List<DepartmentCount> counts() {
        return dao.countByDepartment();
    }

    public List<Employee> procedureEmployees(int d) {
        positive(d, "Department ID");
        return dao.callEmployeesByDepartment(d);
    }

    public int procedureRaise(int d, BigDecimal p) {
        positive(d, "Department ID");
        if (p.signum() <= 0) throw new ValidationException("Percentage must be positive");
        return dao.callRaiseDepartmentSalary(d, p);
    }

    public void transfer(int e, int d) {
        positive(e, "Employee ID");
        positive(d, "Department ID");
        dao.transferEmployee(e, d);
    }

    public int transactionRaise(int d, BigDecimal p) {
        positive(d, "Department ID");
        if (p.signum() <= 0) throw new ValidationException("Percentage must be positive");
        return dao.transactionalDepartmentRaise(d, p);
    }

    public int[] batch(List<Employee> e) {
        e.forEach(this::validate);
        return dao.batchInsert(e);
    }
}
