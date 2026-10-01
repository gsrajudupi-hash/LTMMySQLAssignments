package com.ltim.jdbc.service;

import com.ltim.jdbc.dao.EmployeeDAO;
import com.ltim.jdbc.model.Employee;

public class EmployeeService {

	EmployeeDAO  dao = new EmployeeDAO();

	public void addEmployee(Employee emp) {
		dao.addEmployee(emp);
	}

	public void viewEmployees() {
		dao.viewEmployees();
	}

	public void findEmployee(int empId) {
		dao.findEmployee(empId);
	}

	public void updateEmployee(int empId, double salary) {

		dao.updateEmployee(empId, salary);
	}

	public void deleteEmployee(int empId) {

		dao.deleteEmployee(empId);
	}

	public void employeeWithDepartment() {
		dao.employeeWithDepartment();
	}

	public void employeeCountByDept() {
		dao.employeeCountByDept();
	}

	public void callEmployeeProcedure(int empId) {

		dao.callEmployeeProcedure(empId);
	}

	public void transactionDemo() {
		dao.transactionDemo();
	}
}