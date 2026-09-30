package com.prathamesh.ems.app;

import com.prathamesh.ems.dao.*;
import com.prathamesh.ems.exception.*;
import com.prathamesh.ems.model.*;
import com.prathamesh.ems.service.EmployeeService;
import com.prathamesh.ems.util.SqlErrorTranslator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.SQLException;
import java.util.*;

public class EmployeeManagementApp {
    private final Scanner in = new Scanner(System.in);
    private final EmployeeService service = new EmployeeService(new EmployeeDaoImpl());

    public static void main(String[] args) {
        new EmployeeManagementApp().run();
    }

    private void run() {
        while (true) {
            menu();
            try {
                int c = intVal("Enter choice: ");
                switch (c) {
                    case 1 -> add();
                    case 2 -> service.all().forEach(System.out::println);
                    case 3 -> System.out.println(service.byId(intVal("Employee ID: ")));
                    case 4 -> update();
                    case 5 -> service.delete(intVal("Employee ID: "));
                    case 6 -> service.details().forEach(System.out::println);
                    case 7 -> service.counts().forEach(System.out::println);
                    case 8 -> storedProcedures();
                    case 9 -> transfer();
                    case 10 -> transactionDemo();
                    case 11 -> parameterized();
                    case 12 -> batch();
                    case 0 -> {
                        System.out.println("Application closed.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (DatabaseOperationException e) {
                Throwable x = e.getCause();
                System.out.println(x instanceof SQLException s ? SqlErrorTranslator.friendly(s) : e.getMessage());
            } catch (ValidationException | RecordNotFoundException | NumberFormatException | DateTimeParseException e) {
                System.out.println("Input/validation error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void menu() {
        System.out.println("""
                =================================================
                EMPLOYEE MANAGEMENT SYSTEM
                =================================================
                1. Add Employee
                2. View All Employees
                3. Find Employee by ID
                4. Update Employee
                5. Delete Employee
                6. Employee Details with Department (JOIN)
                7. Department-wise Employee Count (AGGREGATE)
                8. Execute Stored Procedures
                9. Transfer Employee (TRANSACTION)
                10. Transaction Demonstration (raise + audit)
                11. Employees by Minimum Salary (PARAMETERIZED)
                12. Batch Insert Employees
                0. Exit
                """);
    }

    private Employee read(Integer id) {
        String n = text("Name: "), e = text("Email: ");
        BigDecimal s = decimal("Salary: ");
        int d = intVal("Department ID: ");
        LocalDate h = LocalDate.parse(text("Hire date (yyyy-MM-dd): "));
        return new Employee(id, n, e, s, d, h);
    }

    private void add() {
        int id = service.add(read(null));
        System.out.println("Employee added. Generated ID: " + id);
    }

    private void update() {
        int id = intVal("Employee ID to update: ");
        service.update(read(id));
        System.out.println("Employee updated.");
    }

    private void storedProcedures() {
        System.out.println("1. Employees by department  2. Raise department salary");
        int c = intVal("Select: ");
        if (c == 1) service.procedureEmployees(intVal("Department ID: ")).forEach(System.out::println);
        else if (c == 2)
            System.out.println("Affected rows: " + service.procedureRaise(intVal("Department ID: "), decimal("Percentage: ")));
        else System.out.println("Invalid selection.");
    }

    private void transfer() {
        service.transfer(intVal("Employee ID: "), intVal("New department ID: "));
        System.out.println("Employee transferred and audit log committed.");
    }

    private void transactionDemo() {
        int n = service.transactionRaise(intVal("Department ID: "), decimal("Percentage: "));
        System.out.println("Transaction committed. Employees updated: " + n);
    }

    private void parameterized() {
        service.minSalary(decimal("Minimum salary: ")).forEach(System.out::println);
    }

    private void batch() {
        int n = intVal("How many employees? ");
        List<Employee> a = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.println("Employee " + i);
            a.add(read(null));
        }
        System.out.println("Batch result: " + Arrays.toString(service.batch(a)));
    }

    private String text(String p) {
        System.out.print(p);
        return in.nextLine().trim();
    }

    private int intVal(String p) {
        return Integer.parseInt(text(p));
    }

    private BigDecimal decimal(String p) {
        return new BigDecimal(text(p));
    }
}
