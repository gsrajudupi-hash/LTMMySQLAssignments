package com.vikranth.ems.dao;
import com.vikranth.ems.model.*; import java.math.BigDecimal; import java.sql.Connection; import java.util.*;
public interface EmployeeDao {
 int insert(Employee e); List<Employee> findAll(); Optional<Employee> findById(int id); boolean update(Employee e); boolean delete(int id);
 List<Employee> findByMinimumSalary(BigDecimal min); List<EmployeeDetails> findDetailsWithDepartment(); List<DepartmentCount> departmentCounts();
 List<Employee> callEmployeesByDepartment(int deptId); BigDecimal callAnnualSalary(int employeeId); void transfer(Connection c,int employeeId,int newDeptId); int[] batchInsert(List<Employee> employees);
}