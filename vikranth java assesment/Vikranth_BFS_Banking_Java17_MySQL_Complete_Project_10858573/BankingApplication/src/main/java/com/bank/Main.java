package com.bank;
import java.time.*;import java.time.format.*;import java.util.*;import java.util.function.*;import java.util.stream.*;import java.sql.*;
public class Main{
 static final Scanner in=new Scanner(System.in);static final BankingService service=new BankingService();
 public static void main(String[]a){SampleData.load(service);recordDemo();while(true){menu();try{switch(Integer.parseInt(in.nextLine())){case 1->addCustomer();case 2->service.customers.forEach(System.out::println);case 3->System.out.println(service.customer(i("Customer ID: ")));case 4->addAccount();case 5->service.accounts.forEach(Main::showType);case 6->service.deposit(l("Account: "),d("Amount: "));case 7->service.withdraw(l("Account: "),d("Amount: "));case 8->service.transfer(l("Source: "),l("Target: "),d("Amount: "));case 9->System.out.println("Balance: Rs. "+service.account(l("Account: ")).balance);case 10->service.transactions.forEach(System.out::println);case 11->Reports.dashboard(service);case 12->Reports.customerReport(service);case 13->Reports.accountReport(service);case 14->Reports.transactionReport(service);case 15->{var x=service.account(l("Account: "));System.out.println(Reports.statement(x,service.customer(x.customerId)));}case 16->{return;}default->System.out.println("Invalid choice");}}catch(Exception e){System.out.println("ERROR: "+e.getMessage());}}}
 static void menu(){System.out.print("""
1 Add Customer  2 Display Customers  3 Search Customer  4 Add Account
5 Display Accounts 6 Deposit 7 Withdraw 8 Transfer 9 Balance
10 Transaction History 11 Analytics 12 Customer Report 13 Account Report
14 Transaction Report 15 Statement 16 Exit
Choice: """);}
 static void addCustomer(){service.add(new Customer(i("ID: "),s("Name: "),s("Email: "),s("City: "),s("Phone: "),s("PREMIUM/REGULAR: ").toUpperCase()));}
 static void addAccount(){long no=l("Account: ");int cid=i("Customer ID: ");String t=s("SAVINGS/CURRENT/LOAN: ").toUpperCase();double b=d("Opening balance: ");BankAccount x=switch(t){case"SAVINGS"->new SavingsAccount(no,cid,b,"ACTIVE");case"CURRENT"->new CurrentAccount(no,cid,b,"ACTIVE");case"LOAN"->new LoanAccount(no,cid,b,"ACTIVE");default->throw new IllegalArgumentException("Invalid type");};service.add(x);}
 static void showType(BankAccount a){if(a instanceof SavingsAccount x)System.out.println("Savings "+x);else if(a instanceof CurrentAccount x)System.out.println("Current "+x);else if(a instanceof LoanAccount x)System.out.println("Loan "+x);}
 static void recordDemo(){var r=new CustomerRecord(101,"Rahul","Bangalore","PREMIUM");System.out.println("Java 17 record: "+r+", accessor="+r.name());System.out.println("Date/time: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));}
 static String s(String p){System.out.print(p);return in.nextLine();}static int i(String p){return Integer.parseInt(s(p));}static long l(String p){return Long.parseLong(s(p));}static double d(String p){return Double.parseDouble(s(p));}
}
