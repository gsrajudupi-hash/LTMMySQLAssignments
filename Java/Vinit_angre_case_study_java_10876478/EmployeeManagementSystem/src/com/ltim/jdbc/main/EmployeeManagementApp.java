package com.ltim.jdbc.main;

import java.util.Scanner;

import com.ltim.jdbc.model.Employee;
import com.ltim.jdbc.service.EmployeeService;

public class EmployeeManagementApp{
	

public static void main(String[] args) {

	Scanner sc = new Scanner(System.in);
	EmployeeService service = new EmployeeService();

	while (true) {

		System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
		System.out.println("1. Add Employee");
		System.out.println("2. View Employees");
		System.out.println("3. Find Employee");
		System.out.println("4. Update Employee");
		System.out.println("5. Delete Employee");
		System.out.println("6. Employee Details With Department");
		System.out.println("7. Department Wise Employee Count");
		System.out.println("8. Execute Stored Procedure");
		System.out.println("9. Transaction Demo");
		System.out.println("10. Exit");

		System.out.print("Enter Choice : ");
		int choice = sc.nextInt();

		switch (choice) {

		case 1:

			sc.nextLine();

			System.out.print("First Name: ");
			String firstName = sc.nextLine();

			System.out.print("Last Name: ");
			String lastName = sc.nextLine();

			System.out.print("Salary: ");
			double salary = sc.nextDouble();

			System.out.print("Department Id: ");
			int deptId = sc.nextInt();

			Employee emp = new Employee(firstName, lastName, salary, deptId);

			service.addEmployee(emp);
			break;

		case 2:

			service.viewEmployees();
			break;

		case 3:

			System.out.print("Enter Employee Id: ");
			int empId = sc.nextInt();

			service.findEmployee(empId);
			break;

		case 4:

			System.out.print("Enter Employee Id: ");
			int updateId = sc.nextInt();

			System.out.print("Enter New Salary: ");
			double newSalary = sc.nextDouble();

			service.updateEmployee(updateId, newSalary);
			break;

		case 5:

			System.out.print("Enter Employee Id: ");
			int deleteId = sc.nextInt();

			service.deleteEmployee(deleteId);
			break;

		case 6:

			service.employeeWithDepartment();
			break;

		case 7:

			service.employeeCountByDept();
			break;

		case 8:

			System.out.print("Enter Employee Id: ");
			int procId = sc.nextInt();

			service.callEmployeeProcedure(procId);
			break;

		case 9:

			service.transactionDemo();
			break;

		case 10:

			System.out.println("Application Closed");
			System.exit(0);

		default:

			System.out.println("Invalid Choice");
		}
	}
}
}