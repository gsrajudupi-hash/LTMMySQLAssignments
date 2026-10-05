package employee;

import employee.dao.DepartmentDAO;
import employee.dao.EmployeeDAO;
import employee.model.Department;
import employee.model.Employee;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementApplication {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final EmployeeDAO employeeDAO =
            new EmployeeDAO();

    private static final DepartmentDAO departmentDAO =
            new DepartmentDAO();

    public static void main(String[] args) {

        int choice;

        do {
            displayMenu();
            choice = readInteger("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> viewAllEmployees();
                    case 3 -> findEmployee();
                    case 4 -> updateEmployee();
                    case 5 -> deleteEmployee();
                    case 6 -> employeeDetailsWithDepartment();
                    case 7 -> departmentWiseEmployeeCount();
                    case 8 -> executeStoredProcedure();
                    case 9 -> transferEmployee();
                    case 10 -> transactionDemonstration();
                    case 11 ->
                            System.out.println(
                                    "Application closed."
                            );
                    default ->
                            System.out.println(
                                    "Select between 1 and 11."
                            );
                }

            } catch (SQLException exception) {
                handleDatabaseException(exception);
            }

        } while (choice != 11);

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println("""
                
                =================================================
                       EMPLOYEE MANAGEMENT SYSTEM
                =================================================
                1. Add Employee
                2. View All Employees
                3. Find Employee
                4. Update Employee
                5. Delete Employee
                6. Employee Details with Department
                7. Department-wise Employee Count
                8. Execute Stored Procedure
                9. Transfer Employee
                10. Transaction Demonstration
                11. Exit
                =================================================
                """);
    }

    // 1. Add employee
    private static void addEmployee()
            throws SQLException {

        int employeeId =
                readInteger("Enter employee ID: ");

        String employeeName =
                readText("Enter employee name: ");

        String email =
                readText("Enter employee email: ");

        double salary =
                readDouble("Enter employee salary: ");

        int departmentId =
                readInteger("Enter department ID: ");

        Employee employee = new Employee(
                employeeId,
                employeeName,
                email,
                salary,
                departmentId
        );

        boolean added =
                employeeDAO.addEmployee(employee);

        System.out.println(
                added
                        ? "Employee added successfully."
                        : "Employee was not added."
        );
    }

    // 2. View all employees
    private static void viewAllEmployees()
            throws SQLException {

        List<Employee> employees =
                employeeDAO.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        printEmployeeHeading();

        for (Employee employee : employees) {
            printEmployee(employee);
        }
    }

    // 3. Find employee
    private static void findEmployee()
            throws SQLException {

        int employeeId =
                readInteger("Enter employee ID: ");

        Employee employee =
                employeeDAO.findEmployee(employeeId);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        printEmployeeHeading();
        printEmployee(employee);
    }

    // 4. Update employee
    private static void updateEmployee()
            throws SQLException {

        int employeeId =
                readInteger("Employee ID to update: ");

        Employee existingEmployee =
                employeeDAO.findEmployee(employeeId);

        if (existingEmployee == null) {
            System.out.println("Employee not found.");
            return;
        }

        String employeeName =
                readText("Enter new employee name: ");

        String email =
                readText("Enter new employee email: ");

        double salary =
                readDouble("Enter new salary: ");

        int departmentId =
                readInteger("Enter new department ID: ");

        Employee employee = new Employee(
                employeeId,
                employeeName,
                email,
                salary,
                departmentId
        );

        boolean updated =
                employeeDAO.updateEmployee(employee);

        System.out.println(
                updated
                        ? "Employee updated successfully."
                        : "Employee was not updated."
        );
    }

    // 5. Delete employee
    private static void deleteEmployee()
            throws SQLException {

        int employeeId =
                readInteger("Employee ID to delete: ");

        boolean deleted =
                employeeDAO.deleteEmployee(employeeId);

        System.out.println(
                deleted
                        ? "Employee deleted successfully."
                        : "Employee not found."
        );
    }

    // 6. Employee details with department
    private static void employeeDetailsWithDepartment()
            throws SQLException {

        List<Employee> employees =
                employeeDAO.getEmployeesWithDepartment();

        if (employees.isEmpty()) {
            System.out.println(
                    "No employee details found."
            );
            return;
        }

        System.out.printf(
                "%-8s %-20s %-25s %-12s %-20s%n",
                "ID",
                "Name",
                "Email",
                "Salary",
                "Department"
        );

        System.out.println(
                "------------------------------------------------"
                        + "--------------------------------"
        );

        for (Employee employee : employees) {

            System.out.printf(
                    "%-8d %-20s %-25s %-12.2f %-20s%n",
                    employee.getEmployeeId(),
                    employee.getEmployeeName(),
                    employee.getEmail(),
                    employee.getSalary(),
                    employee.getDepartmentName()
            );
        }
    }

    // 7. Department-wise employee count
    private static void departmentWiseEmployeeCount()
            throws SQLException {

        List<Department> departments =
                departmentDAO.getDepartmentEmployeeCount();

        if (departments.isEmpty()) {
            System.out.println(
                    "No departments found."
            );
            return;
        }

        System.out.printf(
                "%-15s %-25s %-15s%n",
                "Department ID",
                "Department Name",
                "Employee Count"
        );

        System.out.println(
                "------------------------------------------------------"
        );

        for (Department department : departments) {

            System.out.printf(
                    "%-15d %-25s %-15d%n",
                    department.getDepartmentId(),
                    department.getDepartmentName(),
                    department.getEmployeeCount()
            );
        }
    }

    // 8. Execute stored procedure
    private static void executeStoredProcedure()
            throws SQLException {

        int departmentId =
                readInteger("Enter department ID: ");

        List<Employee> employees =
                employeeDAO.getEmployeesByDepartment(
                        departmentId
                );

        if (employees.isEmpty()) {
            System.out.println(
                    "No employees found in this department."
            );
            return;
        }

        System.out.printf(
                "%-8s %-20s %-25s %-12s %-20s%n",
                "ID",
                "Name",
                "Email",
                "Salary",
                "Department"
        );

        for (Employee employee : employees) {

            System.out.printf(
                    "%-8d %-20s %-25s %-12.2f %-20s%n",
                    employee.getEmployeeId(),
                    employee.getEmployeeName(),
                    employee.getEmail(),
                    employee.getSalary(),
                    employee.getDepartmentName()
            );
        }
    }

    // 9. Transfer employee
    private static void transferEmployee()
            throws SQLException {

        int employeeId =
                readInteger("Enter employee ID: ");

        int departmentId =
                readInteger("Enter new department ID: ");

        boolean transferred =
                employeeDAO.transferEmployee(
                        employeeId,
                        departmentId
                );

        System.out.println(
                transferred
                        ? "Employee transferred successfully."
                        : "Employee or department not found."
        );
    }

    // 10. Transaction demonstration
    private static void transactionDemonstration()
            throws SQLException {

        int fromEmployeeId =
                readInteger(
                        "Enter employee ID to deduct from: "
                );

        int toEmployeeId =
                readInteger(
                        "Enter employee ID to add to: "
                );

        double amount =
                readDouble("Enter amount: ");

        if (amount <= 0) {
            System.out.println(
                    "Amount must be greater than zero."
            );
            return;
        }

        boolean completed =
                employeeDAO.transferSalary(
                        fromEmployeeId,
                        toEmployeeId,
                        amount
                );

        System.out.println(
                completed
                        ? "Transaction completed successfully."
                        : "Transaction failed and was rolled back."
        );
    }

    private static void printEmployeeHeading() {

        System.out.printf(
                "%-8s %-20s %-25s %-12s %-15s%n",
                "ID",
                "Name",
                "Email",
                "Salary",
                "Department ID"
        );

        System.out.println(
                "------------------------------------------------"
                        + "--------------------------------"
        );
    }

    private static void printEmployee(
            Employee employee) {

        System.out.printf(
                "%-8d %-20s %-25s %-12.2f %-15d%n",
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getDepartmentId()
        );
    }

    private static int readInteger(String message) {

        while (true) {
            try {
                System.out.print(message);

                int value = scanner.nextInt();
                scanner.nextLine();

                return value;

            } catch (InputMismatchException exception) {
                System.out.println(
                        "Enter a valid integer."
                );
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {
            try {
                System.out.print(message);

                double value = scanner.nextDouble();
                scanner.nextLine();

                return value;

            } catch (InputMismatchException exception) {
                System.out.println(
                        "Enter a valid number."
                );
                scanner.nextLine();
            }
        }
    }

    private static String readText(String message) {

        while (true) {
            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "Value cannot be empty."
            );
        }
    }

    private static void handleDatabaseException(
            SQLException exception) {

        String sqlState = exception.getSQLState();

        if ("23000".equals(sqlState)) {
            System.out.println(
                    "Duplicate value or invalid department ID."
            );
        } else if ("28000".equals(sqlState)) {
            System.out.println(
                    "Invalid database username or password."
            );
        } else {
            System.out.println(
                    "Database error: "
                            + exception.getMessage()
            );
        }
    }
}