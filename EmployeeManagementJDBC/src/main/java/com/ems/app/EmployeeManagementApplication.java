package com.ems.app;

import com.ems.model.Employee;
import com.ems.service.EmployeeService;
import com.ems.service.EmployeeServiceImpl;
import com.ems.util.SqlExceptionUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementApplication {

    private final Scanner scanner =
            new Scanner(System.in);

    private final EmployeeService service =
            new EmployeeServiceImpl();

    public static void main(String[] args) {
        new EmployeeManagementApplication().start();
    }

    private void start() {

        boolean running = true;

        while (running) {

            printMenu();

            int choice =
                    readInt("Enter your choice: ");

            try {
                switch (choice) {

                    case 1 -> addEmployee();
                    case 2 -> viewAllEmployees();
                    case 3 -> findEmployee();
                    case 4 -> updateEmployee();
                    case 5 -> deleteEmployee();
                    case 6 -> findByDepartment();

                    case 7 ->
                            service
                                    .showEmployeeDepartmentDetails();

                    case 8 ->
                            service
                                    .showDepartmentEmployeeCount();

                    case 9 ->
                            service
                                    .showEmployeesWithManagers();

                    case 10 ->
                            service
                                    .showProjectsWithEmployees();

                    case 11 ->
                            executeDepartmentProcedure();

                    case 12 ->
                            executeSalaryProcedure();

                    case 13 ->
                            executeTransaction();

                    case 14 ->
                            executeBatch();

                    case 15 -> {
                        running = false;
                        System.out.println(
                                "Application closed."
                        );
                    }

                    default ->
                            System.out.println(
                                    "Enter a choice from 1 to 15."
                            );
                }

            } catch (SQLException exception) {

                SqlExceptionUtil.display(exception);

            } catch (RuntimeException exception) {

                System.out.println(
                        "Error: " + exception.getMessage()
                );
            }
        }

        scanner.close();
    }

    private void printMenu() {

        System.out.println("""
                
                ============================================
                EMPLOYEE MANAGEMENT SYSTEM
                ============================================
                1. Add Employee
                2. View All Employees
                3. Find Employee by ID
                4. Update Employee
                5. Delete Employee
                6. Find Employees by Department
                7. Employee and Department Details
                8. Department-wise Employee Count
                9. Employees with Managers
                10. Projects with Employees
                11. Procedure: Employees by Department
                12. Procedure: Increase Salary
                13. Transaction: Assign Project and Salary
                14. Batch Insert Employees
                15. Exit
                ============================================
                """);
    }

    private void addEmployee()
            throws SQLException {

        service.addEmployee(readEmployee());

        System.out.println(
                "Employee added successfully."
        );
    }

    private void viewAllEmployees()
            throws SQLException {

        List<Employee> employees =
                service.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println(
                    "No employees found."
            );
            return;
        }

        employees.forEach(System.out::println);
    }

    private void findEmployee()
            throws SQLException {

        int id = readInt("Employee ID: ");

        System.out.println(
                service.findEmployee(id)
        );
    }

    private void updateEmployee()
            throws SQLException {

        System.out.println(
                "Enter complete updated details."
        );

        service.updateEmployee(readEmployee());

        System.out.println(
                "Employee updated successfully."
        );
    }

    private void deleteEmployee()
            throws SQLException {

        int id = readInt("Employee ID: ");

        service.deleteEmployee(id);

        System.out.println(
                "Employee deleted successfully."
        );
    }

    private void findByDepartment()
            throws SQLException {

        int id = readInt("Department ID: ");

        List<Employee> employees =
                service.findEmployeesByDepartment(id);

        if (employees.isEmpty()) {
            System.out.println(
                    "No employees found."
            );
            return;
        }

        employees.forEach(System.out::println);
    }

    private void executeDepartmentProcedure()
            throws SQLException {

        int departmentId =
                readInt("Department ID: ");

        service.executeDepartmentProcedure(
                departmentId
        );
    }

    private void executeSalaryProcedure()
            throws SQLException {

        int employeeId =
                readInt("Employee ID: ");

        double percentage =
                readDouble("Increase percentage: ");

        service.executeSalaryProcedure(
                employeeId,
                percentage
        );

        System.out.println(
                "Salary increased successfully."
        );
    }

    private void executeTransaction()
            throws SQLException {

        int projectId =
                readInt("Project ID: ");

        int employeeId =
                readInt("Employee ID: ");

        double salaryIncrease =
                readDouble("Salary increase amount: ");

        service.executeTransaction(
                projectId,
                employeeId,
                salaryIncrease
        );

        System.out.println(
                "Transaction committed successfully."
        );
    }

    private void executeBatch()
            throws SQLException {

        int count =
                readInt("Number of employees: ");

        if (count <= 0) {
            throw new IllegalArgumentException(
                    "Count must be greater than zero."
            );
        }

        List<Employee> employees =
                new ArrayList<>();

        for (int index = 1;
             index <= count;
             index++) {

            System.out.println(
                    "\nEmployee " + index
            );

            employees.add(readEmployee());
        }

        service.executeBatch(employees);

        System.out.println(
                "Batch inserted successfully."
        );
    }

    private Employee readEmployee() {

        int employeeId =
                readInt("Employee ID: ");

        String employeeName =
                readText("Employee name: ");

        String jobTitle =
                readText("Job title: ");

        double salary =
                readDouble("Salary: ");

        LocalDate hireDate =
                readDate(
                        "Hire date (YYYY-MM-DD): "
                );

        int departmentId =
                readInt("Department ID: ");

        Integer managerId =
                readOptionalInteger(
                        "Manager ID "
                                + "(press Enter for none): "
                );

        return new Employee(
                employeeId,
                employeeName,
                jobTitle,
                salary,
                hireDate,
                departmentId,
                managerId
        );
    }

    private String readText(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    private int readInt(String message) {

        while (true) {
            try {
                return Integer.parseInt(
                        readText(message)
                );
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Enter a valid integer."
                );
            }
        }
    }

    private double readDouble(String message) {

        while (true) {
            try {
                return Double.parseDouble(
                        readText(message)
                );
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Enter a valid number."
                );
            }
        }
    }

    private LocalDate readDate(String message) {

        while (true) {
            try {
                return LocalDate.parse(
                        readText(message)
                );
            } catch (DateTimeParseException exception) {
                System.out.println(
                        "Use YYYY-MM-DD format."
                );
            }
        }
    }

    private Integer readOptionalInteger(
            String message) {

        while (true) {

            String input = readText(message);

            if (input.isBlank()) {
                return null;
            }

            try {
                return Integer.valueOf(input);
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Enter an integer or press Enter."
                );
            }
        }
    }
}