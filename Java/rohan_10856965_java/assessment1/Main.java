package assessment1;

import assessment1.model.Employee;
import assessment1.service.EmployeeService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();

        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("""
                    
                    ==========================================
                       EMPLOYEE MANAGEMENT SYSTEM
                    ==========================================
                    1. Add Employee
                    2. View All Employees
                    3. Find Employee
                    4. Update Employee
                    5. Delete Employee
                    6. Employee Details with Department
                    7. Department-wise Employee Count
                    8. Execute Stored Procedure 1
                    9. Transfer Employee
                    10. Execute Stored Procedure 2
                    11. Batch Salary Increment
                    12. Exit
                    """);

                try {
                    System.out.print("Enter choice: ");
                    int choice = Integer.parseInt(sc.nextLine().trim());

                    switch (choice) {
                        case 1 -> {
                            Employee e = readEmployee(sc);
                            service.addEmployee(e);
                            System.out.println("Employee added.");
                        }
                        case 2 -> service.getAllEmployees()
                                .forEach(System.out::println);
                        case 3 -> {
                            int id = readInt(sc, "Employee ID: ");
                            Employee e = service.getEmployeeById(id);
                            System.out.println(
                                    e == null ? "Record not found." : e);
                        }
                        case 4 -> {
                            Employee e = readEmployee(sc);
                            System.out.println(
                                    service.updateEmployee(e)
                                            ? "Employee updated."
                                            : "Record not found.");
                        }
                        case 5 -> {
                            int id = readInt(sc, "Employee ID: ");
                            System.out.println(
                                    service.deleteEmployee(id)
                                            ? "Employee deleted."
                                            : "Record not found.");
                        }
                        case 6 -> service.showEmployeeDepartments();
                        case 7 -> service.departmentWiseCount();
                        case 8 -> {
                            int dept = readInt(sc, "Department ID: ");
                            service.callDepartmentCount(dept);
                        }
                        case 9 -> {
                            int emp = readInt(sc, "Employee ID: ");
                            int dept = readInt(sc, "New department ID: ");
                            service.transferEmployee(emp, dept);
                        }
                        case 10 -> {
                            int id = readInt(sc, "Employee ID: ");
                            BigDecimal salary = readDecimal(
                                    sc, "New salary: ");
                            service.callSalaryUpdate(id, salary);
                        }
                        case 11 -> {
                            BigDecimal increment = readDecimal(
                                    sc, "Salary increment: ");
                            service.batchUpdateSalary(increment);
                            System.out.println("Batch completed.");
                        }
                        case 12 -> running = false;
                        default -> System.out.println(
                                "Invalid menu choice.");
                    }

                } catch (NumberFormatException ex) {
                    System.out.println(
                            "Invalid input. Enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    System.out.println(
                            "Validation error: " + ex.getMessage());
                } catch (SQLException ex) {
                    handleDatabaseError(ex);
                } catch (Exception ex) {
                    System.out.println(
                            "Unexpected error: " + ex.getMessage());
                }
            }
        }

        System.out.println("Application closed.");
    }

    private static Employee readEmployee(Scanner sc) {
        int id = readInt(sc, "Employee ID: ");

        System.out.print("Employee name: ");
        String name = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        BigDecimal salary = readDecimal(sc, "Salary: ");
        int dept = readInt(sc, "Department ID: ");

        return new Employee(id, name, email, salary, dept);
    }

    private static int readInt(Scanner sc, String message) {
        System.out.print(message);
        return Integer.parseInt(sc.nextLine().trim());
    }

    private static BigDecimal readDecimal(
            Scanner sc, String message) {
        System.out.print(message);
        return new BigDecimal(sc.nextLine().trim());
    }

    private static void handleDatabaseError(SQLException ex) {
        String state = ex.getSQLState();
        int code = ex.getErrorCode();

        if (state != null && state.startsWith("23")) {
            if (code == 1062) {
                System.out.println(
                        "Duplicate primary key or unique value.");
            } else {
                System.out.println(
                        "Constraint violation: check primary/foreign keys.");
            }
        } else if (state != null && state.startsWith("08")) {
            System.out.println(
                    "Database connection failed. Check MySQL and credentials.");
        } else if (state != null && state.startsWith("42")) {
            System.out.println(
                    "SQL error. Check table names, columns and query syntax.");
        } else {
            System.out.println(
                    "Database operation failed. SQLState=" + state +
                            ", errorCode=" + code);
        }

        // Log technical details during development; avoid exposing
        // credentials or sensitive SQL data to the end user.
        System.err.println("Database error: " + ex.getMessage());
    }
}