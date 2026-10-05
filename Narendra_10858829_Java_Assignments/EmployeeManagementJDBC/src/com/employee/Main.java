package com.employee;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
/**
 * Author   : 10858829
 * Date     : 30 Sept 2026
 * Time     : 9:46:32 am
 * project  : EmployeeManagementJDBC
 */



 
public class Main {
 
    private static final Scanner sc =
            new Scanner(System.in);
 
    private static final EmployeeService service =
            new EmployeeService();
 
    public static void main(String[] args) {
 
        int choice;
 
        do {
 
            displayMenu();
 
            choice = readInt("Enter your choice: ");
 
            try {
 
                switch (choice) {
 
                    case 1:
                        addEmployee();
                        break;
 
                    case 2:
                        displayAllEmployees();
                        break;
 
                    case 3:
                        findEmployee();
                        break;
 
                    case 4:
                        updateEmployee();
                        break;
 
                    case 5:
                        deleteEmployee();
                        break;
 
                    case 6:
                        service.displayEmployeeDetails();
                        break;
 
                    case 7:
                        service.departmentEmployeeCount();
                        break;
 
                    case 8:
                        executeStoredProcedure();
                        break;
 
                    case 9:
                        transferEmployee();
                        break;
 
                    case 10:
                        service.transactionDemo();
                        break;
 
                    case 11:
                        salaryIncrease();
                        break;
 
                    case 12:
                        employeesByDepartment();
                        break;
 
                    case 13:
                        System.out.println(
                                "Application exited."
                        );
                        break;
 
                    default:
                        System.out.println(
                                "Invalid choice."
                        );
                }
 
            } catch (Exception e) {
 
                System.out.println(
                        "Invalid input: " +
                        e.getMessage()
                );
            }
 
        } while (choice != 13);
 
        sc.close();
    }
 
    // --------------------------------------------------
    // MENU
    // --------------------------------------------------
 
    private static void displayMenu() {
 
        System.out.println();
        System.out.println("======================================");
        System.out.println("     EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("======================================");
 
        System.out.println("1.  Add Employee");
        System.out.println("2.  Display All Employees");
        System.out.println("3.  Search Employee");
        System.out.println("4.  Update Employee");
        System.out.println("5.  Delete Employee");
        System.out.println("6.  Employee Details with Department");
        System.out.println("7.  Department-wise Employee Count");
        System.out.println("8.  Execute Stored Procedure");
        System.out.println("9.  Transfer Employee");
        System.out.println("10. Transaction Demonstration");
        System.out.println("11. Increase All Salaries");
        System.out.println("12. Employees by Department");
        System.out.println("13. Exit");
 
        System.out.println("======================================");
    }
 
    // --------------------------------------------------
    // 1. ADD EMPLOYEE
    // --------------------------------------------------
 
    private static void addEmployee() {
 
        int id =
                readInt("Enter Employee ID: ");
 
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();
 
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
 
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
 
        double salary =
                readDouble("Enter Salary: ");
 
        System.out.print(
                "Enter Hire Date (yyyy-MM-dd): "
        );
 
        LocalDate hireDate =
                LocalDate.parse(sc.nextLine());
 
        int deptId =
                readInt("Enter Department ID: ");
 
        System.out.print(
                "Enter Manager ID (press Enter for none): "
        );
 
        String managerInput =
                sc.nextLine();
 
        Integer managerId = null;
 
        if (!managerInput.isBlank()) {
            managerId =
                    Integer.parseInt(managerInput);
        }
 
        Employee employee =
                new Employee(
                        id,
                        name,
                        email,
                        phone,
                        salary,
                        hireDate,
                        deptId,
                        managerId
                );
 
        boolean result =
                service.addEmployee(employee);
 
        if (result) {
            System.out.println(
                    "Employee added successfully."
            );
        } else {
            System.out.println(
                    "Employee insertion failed."
            );
        }
    }
 
    // --------------------------------------------------
    // 2. DISPLAY ALL
    // --------------------------------------------------
 
    private static void displayAllEmployees() {
 
        List<Employee> employees =
                service.getAllEmployees();
 
        if (employees.isEmpty()) {
 
            System.out.println(
                    "No employees found."
            );
 
            return;
        }
 
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
 
    // --------------------------------------------------
    // 3. SEARCH
    // --------------------------------------------------
 
    private static void findEmployee() {
 
        int id =
                readInt("Enter Employee ID: ");
 
        Employee employee =
                service.findEmployeeById(id);
 
        if (employee == null) {
 
            System.out.println(
                    "Employee not found."
            );
 
        } else {
 
            System.out.println(employee);
        }
    }
 
    // --------------------------------------------------
    // 4. UPDATE
    // --------------------------------------------------
 
    private static void updateEmployee() {
 
        int id =
                readInt("Enter Employee ID: ");
 
        Employee existing =
                service.findEmployeeById(id);
 
        if (existing == null) {
 
            System.out.println(
                    "Employee not found."
            );
 
            return;
        }
 
        System.out.print(
                "Enter New Name: "
        );
 
        String name = sc.nextLine();
 
        System.out.print(
                "Enter New Email: "
        );
 
        String email = sc.nextLine();
 
        System.out.print(
                "Enter New Phone: "
        );
 
        String phone = sc.nextLine();
 
        double salary =
                readDouble("Enter New Salary: ");
 
        int deptId =
                readInt("Enter New Department ID: ");
 
        System.out.print(
                "Enter Manager ID (blank for none): "
        );
 
        String managerInput =
                sc.nextLine();
 
        Integer managerId = null;
 
        if (!managerInput.isBlank()) {
            managerId =
                    Integer.parseInt(managerInput);
        }
 
        existing.setEmpName(name);
        existing.setEmail(email);
        existing.setPhone(phone);
        existing.setSalary(salary);
        existing.setDeptId(deptId);
        existing.setManagerId(managerId);
 
        boolean result =
                service.updateEmployee(existing);
 
        if (result) {
 
            System.out.println(
                    "Employee updated successfully."
            );
 
        } else {
 
            System.out.println(
                    "Employee update failed."
            );
        }
    }
 
    // --------------------------------------------------
    // 5. DELETE
    // --------------------------------------------------
 
    private static void deleteEmployee() {
 
        int id =
                readInt("Enter Employee ID: ");
 
        Employee employee =
                service.findEmployeeById(id);
 
        if (employee == null) {
 
            System.out.println(
                    "Employee not found."
            );
 
            return;
        }
 
        boolean result =
                service.deleteEmployee(id);
 
        if (result) {
 
            System.out.println(
                    "Employee deleted successfully."
            );
 
        } else {
 
            System.out.println(
                    "Employee deletion failed."
            );
        }
    }
 
    // --------------------------------------------------
    // 8. STORED PROCEDURE
    // --------------------------------------------------
 
    private static void executeStoredProcedure() {
 
        System.out.println();
        System.out.println("1. Get Employee");
        System.out.println("2. Get Employees By Department");
 
        int choice =
                readInt("Enter choice: ");
 
        if (choice == 1) {
 
            int empId =
                    readInt("Enter Employee ID: ");
 
            service.getEmployeeUsingProcedure(empId);
 
        } else if (choice == 2) {
 
            int deptId =
                    readInt("Enter Department ID: ");
 
            service.getEmployeesByDepartment(deptId);
 
        } else {
 
            System.out.println(
                    "Invalid choice."
            );
        }
    }
 
    // --------------------------------------------------
    // 9. TRANSFER EMPLOYEE
    // --------------------------------------------------
 
    private static void transferEmployee() {
 
        int empId =
                readInt("Enter Employee ID: ");
 
        int oldDept =
                readInt("Enter Old Department ID: ");
 
        int newDept =
                readInt("Enter New Department ID: ");
 
        service.transferEmployee(
                empId,
                oldDept,
                newDept
        );
    }
 
    // --------------------------------------------------
    // 11. SALARY INCREASE
    // --------------------------------------------------
 
    private static void salaryIncrease() {
 
        double percentage =
                readDouble(
                        "Enter salary increase percentage: "
                );
 
        service.salaryIncrease(percentage);
    }
 
    // --------------------------------------------------
    // 12. EMPLOYEES BY DEPARTMENT
    // --------------------------------------------------
 
    private static void employeesByDepartment() {
 
        int deptId =
                readInt("Enter Department ID: ");
 
        service.getEmployeesByDepartment(deptId);
    }
 
 // --------------------------------------------------
    // INPUT METHODS
    // --------------------------------------------------
 
    private static int readInt(String message) {
 
        System.out.print(message);
 
        while (!sc.hasNextInt()) {
 
            System.out.println(
                    "Please enter a valid integer."
            );
 
            sc.next();
            System.out.print(message);
        }
 
        int value = sc.nextInt();
 
        sc.nextLine();
 
        return value;
    }
 
    private static double readDouble(String message) {
 
        System.out.print(message);
 
        while (!sc.hasNextDouble()) {
 
            System.out.println(
                    "Please enter a valid number."
            );
 
            sc.next();
            System.out.print(message);
        }
 
        double value = sc.nextDouble();
 
        sc.nextLine();
 
        return value;
    }
}
 
 
