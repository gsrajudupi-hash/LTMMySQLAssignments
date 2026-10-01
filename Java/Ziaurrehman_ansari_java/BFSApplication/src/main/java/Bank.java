import Exceptions.InsufficientBalance;
import Service.BankingService;
import Service.CustomerService;
import Service.TransactionService;

import java.io.BufferedReader;
import java.util.Scanner;

/*
Author : 
Date: 
Project : 
*/
public class Bank {
    public void initializeBank(){
    }
    public static void main(String[] args) throws InsufficientBalance {

        BankingService bs = new BankingService();
        CustomerService cs = new CustomerService();
        TransactionService ts = new TransactionService();
        Scanner sc = new Scanner(System.in);
        int option  = 0;
        do {
            System.out.println("1. Add Customer");
            System.out.println("2. Display All Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Add Bank Account");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Deposit Money");
            System.out.println("7. Withdraw Money");
            System.out.println("8. Transfer Money");
            System.out.println("9. Check Account Balance");
            System.out.println("10. Display Transaction History");
            System.out.println("11. Banking Analytics");
            System.out.println("12. Customer Report");
            System.out.println("13. Account Type Report");
            System.out.println("14. Transaction Report");
            System.out.println("15. Generate Account Statement");
            System.out.println("0. Exit");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Add Customer");
                    cs.addCustomer();
                    break;

                case 2:
                    System.out.println("Display All Customers");
                    cs.displayAllCustomers();
                    break;

                case 3:
                    System.out.println("Enter customer email");
                    String email = sc.next();
                    cs.findCustomer(email);
                    break;

                case 4:
                    System.out.println("Add Bank Account");
                    bs.addBankAccount();
                    break;

                case 5:
                    System.out.println("Display All Accounts");
                    bs.displayAllAccounts();
                    break;

                case 6:
                    System.out.println("Deposit Money");
                    System.out.println("Enter Account Number");
                    int acc = sc.nextInt();
                    System.out.println("Enter Amount");
                    double amount = sc.nextDouble();
                    bs.depositToAccount(acc, amount);
                    break;

                case 7:
                    System.out.println("Withdraw Money");
                    System.out.println("Enter Account Number");
                     acc = sc.nextInt();
                    System.out.println("Enter Amount");
                     amount = sc.nextDouble();
                    bs.withdrawFromAccount(acc, amount);
                    break;

                case 8:
                    System.out.println("Transfer Money");
                    System.out.println("Enter from Account Number ");
                    acc = sc.nextInt();
                    System.out.println("Enter to Account Number ");
                    int acc2 = sc.nextInt();
                    System.out.println("Enter Amount");
                    amount = sc.nextDouble();
                    bs.transfer(acc, acc2, amount);
                    break;

                case 9:
                    System.out.println("Check Account Balance");
                    System.out.println("Enter Account Number");
                    acc = sc.nextInt();
                    bs.checkAccountBalance(acc);
                    break;

                case 10:
                    System.out.println("Display Transaction History");
                    ts.history();
                    break;

                case 12:
                    System.out.println("Customer Report");
                    bs.customerReport();
                    break;

                case 13:
                    System.out.println("Account Type Report");
                    bs.BankingReport();
                    break;

                case 14:
                    System.out.println("Transaction Report");
//                    transactionReport();
                    ts.transactionReport();
                    break;

                case 15:
                    System.out.println("Generate Account Statement");
                    System.out.println("Enter Account Number");
                    acc = sc.nextInt();
                    ts.generateBankStatement(acc);
                    break;

                case 0:
                    System.out.println("Exiting application...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while(option != 0);
    }
}
