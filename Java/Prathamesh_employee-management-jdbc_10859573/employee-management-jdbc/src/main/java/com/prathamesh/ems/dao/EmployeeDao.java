package com.prathamesh.ems.dao;
import com.prathamesh.ems.model.*; import java.math.BigDecimal; import java.util.List;
public interface EmployeeDao {
 int add(Employee e); List<Employee> findAll(); Employee findById(int id); boolean update(Employee e); boolean delete(int id);
 List<Employee> findByMinimumSalary(BigDecimal minSalary); List<EmployeeDepartmentView> findDetailsWithDepartment();
 List<DepartmentCount> countByDepartment(); List<Employee> callEmployeesByDepartment(int departmentId);
 int callRaiseDepartmentSalary(int departmentId, BigDecimal percentage); void transferEmployee(int employeeId,int newDepartmentId);
 int transactionalDepartmentRaise(int departmentId,BigDecimal percentage); int[] batchInsert(List<Employee> employees);
}
