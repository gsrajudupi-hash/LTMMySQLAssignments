package org.example;

import org.example.model.Employee;
import org.example.service.EmployeeService;

import java.util.Scanner;

public class Main {
    public static void welcomeScreen() {
        System.out.println("=================================");
        System.out.println("1.  Add Employee");
        System.out.println("2.  View All Employees");
        System.out.println("3.  Find Employee");
        System.out.println("4.  Update Employee");
        System.out.println("5.  Delete Employee");
        System.out.println("6.  Employee Details with Department");
        System.out.println("7.  Department-wise Employee Count");
        System.out.println("8.  Execute Stored Procedure");
        System.out.println("9.  Transfer Employee");
        System.out.println("10. Transaction Demonstration");
        System.out.println("11. Exit");
        System.out.println("=================================================");
        System.out.print("Enter your choice: ");
    }

    static void main() {
        EmployeeService employeeService=new EmployeeService();
        Scanner sc=new Scanner(System.in);
        String optionSelected;
        System.out.println("=================================================");
        System.out.println("         EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=================================================");
        do{
            welcomeScreen();
            optionSelected=sc.nextLine();
            if(optionSelected.equals("1")){

                Employee employee = new Employee();

                System.out.println("=================================");
                System.out.println("      ENTER EMPLOYEE DETAILS");
                System.out.println("=================================");

                System.out.print("First Name      : ");
                employee.setEmpFirstName(sc.nextLine());
                System.out.print("Last Name       : ");
                employee.setEmpLastName(sc.nextLine());
                System.out.print("Phone Number    : ");
                employee.setEmpPhoneNumber(sc.nextLine());
                System.out.print("Address         : ");
                employee.setEmpAddress(sc.nextLine());
                System.out.print("Blood Group     : ");
                employee.setEmpBloodGroup(sc.nextLine());
                System.out.print("Salary          : ");
                employee.setEmpSalary(sc.nextLine());
                System.out.print("Department ID   : ");
                employee.setEmpDeptId(sc.nextInt());
                sc.nextLine();
                employeeService.addEmployee(employee);
            }
            else if(optionSelected.equals("2")){
                employeeService.viewAllEmployees();
            }
            else if(optionSelected.equals("3")){
                int empId;
                System.out.print("Employee ID   : ");
                empId=sc.nextInt();
                sc.nextLine();
                employeeService.findEmployeeById(empId);
            }
            else if(optionSelected.equals("4")){
                int empId;
                String firstName, lastName;
                System.out.println("You are updating Employee details");
                System.out.print("Employee ID   : ");
                empId=sc.nextInt();
                sc.nextLine();

                System.out.print("Employee First Name   :");
                firstName=sc.nextLine();
                System.out.print("Employee Last Name   :");
                lastName=sc.nextLine();
                //sc.nextLine();
                employeeService.updateEmployee(empId, firstName, lastName);
            }
            else if(optionSelected.equals("5")){
                int empId;
                System.out.println("You are deleting Employee details");
                System.out.print("Employee ID   : ");
                empId=sc.nextInt();
                sc.nextLine();
                employeeService.deleteEmployee(empId);
            }
            else if(optionSelected.equals("6")) {
                employeeService.employeeDetailsWithDepartment();
            }
            else if(optionSelected.equals("7")) {
                employeeService.departmentWiseEmployeeCount();
            }
            else if(optionSelected.equals("8")) {
                System.out.println("===== STORED PROCEDURE MENU =====");
                System.out.println("1. Get Employee Count By Location");
                System.out.println("2. Get Employee Count By Blood Group");
                System.out.print("Enter your choice: ");

                int internalOption = Integer.parseInt(sc.nextLine());

                switch (internalOption) {
                    case 1:
                        System.out.print("Enter Department Location: ");
                        String location = sc.nextLine();
                        employeeService.executeStoredProcedureGetEmployeeCountByLocation(location);
                        break;

                    case 2:
                        employeeService.executeStoredProcedureGetEmployeeCountByBloodGroup();
                        break;

                    default:
                        System.out.println("Invalid Stored Procedure Choice!");
                }
            }
            else if(optionSelected.equals("9")) {
                int empId, deptId;
                System.out.println("You are updating Employee details");
                System.out.print("Employee ID   : ");
                empId=sc.nextInt();
                System.out.print("Department ID   : ");
                deptId=sc.nextInt();
                sc.nextLine();
                employeeService.transferEmployee(empId, deptId);
            }
            else if(optionSelected.equals("10")) {
                int empId, deptId;
                String salary;

                System.out.println("You are updating Employee details");

                System.out.print("Enter Employee ID : ");
                empId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter New Department ID : ");
                deptId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter New Salary : ");
                salary = sc.nextLine();
                employeeService.transactionDemonstration(empId, deptId, salary);
            }
            else if(optionSelected.equalsIgnoreCase("11")) {
                break;
            }
            else {
                System.out.println("Please select correct options");
            }
        }while(!optionSelected.equalsIgnoreCase("11"));
        System.out.println("You have exited the program");
    }

}
