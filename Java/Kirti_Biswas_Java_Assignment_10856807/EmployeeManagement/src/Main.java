import model.Employee;
import service.EmployeeService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EmployeeService service = new EmployeeService();

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("EMPLOYEE MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Find Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Employee Details with Department");
            System.out.println("7. Department-wise Employee Count");
            System.out.println("8. Execute Stored Procedure");
            System.out.println("9. Transfer Employee");
            System.out.println("10. Transaction Demonstration");
            System.out.println("11. Exit");
            System.out.print("Enter Choice : ");

            choice = scanner.nextInt();

            try {

                switch (choice) {

                    case 1:

                        scanner.nextLine();

                        System.out.print("Employee Name : ");
                        String name = scanner.nextLine();

                        System.out.print("Email : ");
                        String email = scanner.nextLine();

                        System.out.print("Salary : ");
                        double salary = scanner.nextDouble();

                        System.out.print("Department ID : ");
                        int deptId = scanner.nextInt();

                        Employee employee = new Employee(
                                name,
                                email,
                                salary,
                                deptId
                        );

                        service.addEmployee(employee);

                        System.out.println("Employee Added Successfully");
                        break;

                    case 2:

                        List<Employee> employees =
                                service.getAllEmployees();

                        employees.forEach(System.out::println);

                        break;

                    case 3:

                        System.out.print("Enter Employee ID : ");
                        int empId = scanner.nextInt();

                        Employee found =
                                service.getEmployeeById(empId);

                        System.out.println(found);

                        break;

                    case 4:

                        System.out.print("Employee ID : ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Employee Name : ");
                        String updateName = scanner.nextLine();

                        System.out.print("Email : ");
                        String updateEmail = scanner.nextLine();

                        System.out.print("Salary : ");
                        double updateSalary =
                                scanner.nextDouble();

                        System.out.print("Department ID : ");
                        int updateDeptId =
                                scanner.nextInt();

                        Employee updateEmployee =
                                new Employee(
                                        updateId,
                                        updateName,
                                        updateEmail,
                                        updateSalary,
                                        updateDeptId
                                );

                        service.updateEmployee(
                                updateEmployee
                        );

                        System.out.println(
                                "Employee Updated Successfully"
                        );

                        break;

                    case 5:

                        System.out.print(
                                "Enter Employee ID : "
                        );

                        int deleteId =
                                scanner.nextInt();

                        boolean deleted =
                                service.deleteEmployee(deleteId);

                        if(deleted){
                            System.out.println(
                                    "Employee Deleted Successfully"
                            );
                        }
                        else{
                            System.out.println(
                                    "Employee ID Not Found"
                            );
                        }

                        break;

                    case 6:

                        List<String> details =
                                service.getEmployeeDetailsWithDepartment();

                        details.forEach(System.out::println);

                        break;

                    case 7:

                        Map<String, Integer> counts =
                                service.getDepartmentWiseEmployeeCount();

                        counts.forEach(
                                (department, count) ->
                                        System.out.println(
                                                department +
                                                        " : " +
                                                        count
                                        )
                        );

                        break;

                    case 8:

                        System.out.println("1. Salary Update Procedure");
                        System.out.println("2. Employee Count Procedure");

                        int procedureChoice =
                                scanner.nextInt();

                        if (procedureChoice == 1) {

                            System.out.print(
                                    "Department ID : "
                            );

                            int departmentId =
                                    scanner.nextInt();

                            System.out.print(
                                    "Percentage : "
                            );

                            double percentage =
                                    scanner.nextDouble();

                            service.executeStoredProcedure(
                                    departmentId,
                                    percentage
                            );

                            System.out.println(
                                    "Procedure Executed"
                            );
                        }

                        else if (procedureChoice == 2) {

                            System.out.print(
                                    "Department ID : "
                            );

                            int departmentId =
                                    scanner.nextInt();

                            int count =
                                    service.getEmployeeCountByDepartment(
                                            departmentId
                                    );

                            System.out.println(
                                    "Employee Count : " +
                                            count
                            );
                        }

                        break;

                    case 9:

                        System.out.print("Employee ID : ");
                        int employeeId = scanner.nextInt();

                        System.out.print("New Department ID : ");
                        int newDepartmentId = scanner.nextInt();

                        boolean transferred =
                                service.transferEmployee(
                                        employeeId,
                                        newDepartmentId
                                );

                        if (transferred) {

                            System.out.println(
                                    "Employee Transferred Successfully"
                            );

                        } else {

                            System.out.println(
                                    "Employee ID Not Found"
                            );
                        }

                        break;

                    case 10:

                        System.out.print(
                                "Employee ID : "
                        );

                        int transactionEmployeeId =
                                scanner.nextInt();

                        System.out.print(
                                "Old Department ID : "
                        );

                        int oldDepartmentId =
                                scanner.nextInt();

                        System.out.print(
                                "New Department ID : "
                        );

                        int transactionNewDepartmentId =
                                scanner.nextInt();

                        service.transactionDemo(
                                transactionEmployeeId,
                                oldDepartmentId,
                                transactionNewDepartmentId
                        );

                        System.out.println(
                                "Transaction Completed Successfully"
                        );

                        break;

                    case 11:

                        System.out.println(
                                "Application Closed"
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid Choice"
                        );
                }

            } catch (SQLException exception) {

                System.out.println(
                        "Database Error : " +
                                exception.getMessage()
                );

            } catch (Exception exception) {

                System.out.println(
                        "Error : " +
                                exception.getMessage()
                );
            }

        } while (choice != 11);

        scanner.close();
    }
}