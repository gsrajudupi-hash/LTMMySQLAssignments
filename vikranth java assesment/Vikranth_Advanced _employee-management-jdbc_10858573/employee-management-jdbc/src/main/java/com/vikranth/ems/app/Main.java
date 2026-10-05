package com.vikranth.ems.app;
import com.vikranth.ems.dao.EmployeeDaoJdbc; import com.vikranth.ems.model.Employee; import com.vikranth.ems.service.EmployeeService;
import java.math.BigDecimal; import java.time.LocalDate; import java.time.format.DateTimeParseException; import java.util.*;
public class Main {
 private static final Scanner IN=new Scanner(System.in); private static final EmployeeService S=new EmployeeService(new EmployeeDaoJdbc());
 public static void main(String[] args){while(true){menu();try{switch(readInt("Choice: ")){case 1->add();case 2->S.all().forEach(System.out::println);case 3->System.out.println(S.get(readInt("Employee ID: ")));case 4->update();case 5->System.out.println(S.delete(readInt("Employee ID: "))?"Deleted":"Record not found");case 6->S.details().forEach(System.out::println);case 7->S.counts().forEach(System.out::println);case 8->procedures();case 9->transfer();case 10->batch();case 11->S.salaryAtLeast(readDecimal("Minimum salary: ")).forEach(System.out::println);case 0->{System.out.println("Goodbye");return;}default->System.out.println("Invalid choice");}}catch(Exception e){System.out.println("ERROR: "+e.getMessage());}}}
 private static void menu(){System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===\n1 Add  2 All  3 Find  4 Update  5 Delete\n6 Join 7 Aggregate 8 Procedures 9 Transfer Transaction 10 Batch 11 Salary Query 0 Exit");}
 private static void add(){Employee e=input(null);System.out.println("Created employee ID: "+S.add(e));}
 private static void update(){int id=readInt("Employee ID: ");System.out.println(S.update(input(id))?"Updated":"Record not found");}
 private static Employee input(Integer id){System.out.print("Name: ");String n=IN.nextLine().trim();System.out.print("Email: ");String mail=IN.nextLine().trim();BigDecimal sal=readDecimal("Salary: ");LocalDate d=readDate("Hire date yyyy-MM-dd: ");int dept=readInt("Department ID: ");return new Employee(id,n,mail,sal,d,dept);}
 private static void procedures(){int d=readInt("Department ID: ");S.procedureByDepartment(d).forEach(System.out::println);int id=readInt("Employee ID for annual salary: ");System.out.println("Annual salary: "+S.annualSalary(id));}
 private static void transfer(){S.transferWithAudit(readInt("Employee ID: "),readInt("New department ID: "));System.out.println("Transfer committed");}
 private static void batch(){int n=readInt("How many employees: ");List<Employee>a=new ArrayList<>();for(int i=1;i<=n;i++){System.out.println("Employee "+i);a.add(input(null));}System.out.println("Batch results: "+Arrays.toString(S.batch(a)));}
 private static int readInt(String p){while(true){try{System.out.print(p);return Integer.parseInt(IN.nextLine().trim());}catch(NumberFormatException e){System.out.println("Enter a valid integer.");}}}
 private static BigDecimal readDecimal(String p){while(true){try{System.out.print(p);return new BigDecimal(IN.nextLine().trim());}catch(NumberFormatException e){System.out.println("Enter a valid number.");}}}
 private static LocalDate readDate(String p){while(true){try{System.out.print(p);return LocalDate.parse(IN.nextLine().trim());}catch(DateTimeParseException e){System.out.println("Use yyyy-MM-dd.");}}}
}