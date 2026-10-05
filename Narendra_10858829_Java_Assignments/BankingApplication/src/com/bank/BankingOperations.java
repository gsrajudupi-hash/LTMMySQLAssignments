package com.bank;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.bank.model.BankAccount;
import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.LoanAccount;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;
import com.bank.service.BankingService;

/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 11:26:06 pm
 * project  : BankingApplication
 */

public class BankingOperations {
	
	
	Main main = new Main();

	public static void main(String[] args) {
		
		BankingService service = new BankingService();
	    Scanner scanner = new Scanner(System.in);
	 
	    // Load sample data
	    //loadCustomers(service);
	    //loadAccounts(service);
	 
	    while (true) {
	 
	        System.out.println();
	        System.out.println("==============================================");
	        System.out.println("       BANKING MANAGEMENT SYSTEM");
	        System.out.println("==============================================");
	        System.out.println("1.  Add Customer");
	        System.out.println("2.  Display All Customers");
	        System.out.println("3.  Search Customer");
	        System.out.println("4.  Add Bank Account");
	        System.out.println("5.  Display All Accounts");
	        System.out.println("6.  Deposit Money");
	        System.out.println("7.  Withdraw Money");
	        System.out.println("8.  Transfer Money");
	        System.out.println("9.  Check Account Balance");
	        System.out.println("10. Display Transaction History");
	        System.out.println("11. Banking Analytics");
	        System.out.println("12. Customer Report");
	        System.out.println("13. Account Type Report");
	        System.out.println("14. Transaction Report");
	        System.out.println("15. Generate Account Statement");
	        System.out.println("16. Exit");
	        System.out.println("==============================================");
	 
	        System.out.print("Enter your choice: ");
	 
	        int choice;
	 
	        try {
	            choice = Integer.parseInt(scanner.nextLine());
	        } catch (NumberFormatException e) {
	            System.out.println("Please enter a valid number.");
	            continue;
	        }
	 
	        try {
	 
	            switch (choice) {
	 
	                // 1. Add Customer
	                case 1:
	 
	                    System.out.println("\n===== ADD CUSTOMER =====");
	 
	                    System.out.print("Customer ID: ");
	                    int customerId =
	                            Integer.parseInt(scanner.nextLine());
	 
	                    System.out.print("Name: ");
	                    String name = scanner.nextLine();
	 
	                    System.out.print("Email: ");
	                    String email = scanner.nextLine();
	 
	                    System.out.print("City: ");
	                    String city = scanner.nextLine();
	 
	                    System.out.print("Phone: ");
	                    String phone = scanner.nextLine();
	 
	                    System.out.print(
	                            "Customer Type (PREMIUM/REGULAR): ");
	 
	                    String customerType =
	                            scanner.nextLine().toUpperCase();
	 
	                    Customer customer =
	                            new Customer(
	                                    customerId,
	                                    name,
	                                    email,
	                                    city,
	                                    phone,
	                                    customerType);
	 
	                    service.addCustomer(customer);
	 
	                    System.out.println(
	                            "Customer added successfully.");
	 
	                    break;
	 
	 
	                // 2. Display All Customers
	                case 2:
	 
	                    System.out.println(
	                            "\n===== ALL CUSTOMERS =====");
	 
	                    service.getCustomers()
	                            .forEach(System.out::println);
	 
	                    break;
	 
	 
	                // 3. Search Customer
	                case 3:
	 
	                    System.out.println(
	                            "\n===== SEARCH CUSTOMER =====");
	 
	                    System.out.print("Customer ID: ");
	 
	                    int searchId =
	                            Integer.parseInt(scanner.nextLine());
	 
	                    Customer foundCustomer =
	                            service.findCustomer(searchId);
	 
	                    System.out.println(
	                            "Customer Found:");
	 
	                    System.out.println(foundCustomer);
	 
	                    break;
	                    
	                    // 4. Add Bank Account
	                case 4:
	 
	                    System.out.println(
	                            "\n===== ADD BANK ACCOUNT =====");
	 
	                    System.out.print("Account Number: ");
	 
	                    long accountNumber =
	                            Long.parseLong(scanner.nextLine());
	 
	                    System.out.print("Customer ID: ");
	 
	                    int accountCustomerId =
	                            Integer.parseInt(scanner.nextLine());
	 
	                    System.out.print(
	                            "Account Type (SAVINGS/CURRENT/LOAN): ");
	 
	                    String accountType =
	                            scanner.nextLine().toUpperCase();
	 
	                    System.out.print("Initial Balance: ");
	 
	                    double initialBalance =
	                            Double.parseDouble(scanner.nextLine());
	 
	                    BankAccount account;
	 
	                    switch (accountType) {
	 
	                        case "SAVINGS":
	 
	                            account =
	                                    new SavingsAccount(
	                                            accountNumber,
	                                            accountCustomerId,
	                                            initialBalance,
	                                            "ACTIVE");
	 
	                            break;
	 
	                        case "CURRENT":
	 
	                            account =
	                                    new CurrentAccount(
	                                            accountNumber,
	                                            accountCustomerId,
	                                            initialBalance,
	                                            "ACTIVE");
	 
	                            break;
	 
	                        case "LOAN":
	 
	                            account =
	                                    new LoanAccount(
	                                            accountNumber,
	                                            accountCustomerId,
	                                            initialBalance,
	                                            "ACTIVE");
	 
	                            break;
	 
	                        default:
	 
	                            System.out.println(
	                                    "Invalid account type.");
	 
	                            continue;
	                    }
	 
	                    service.addAccount(account);
	 
	                    System.out.println(
	                            "Account added successfully.");
	 
	                    break;
	 
	 
	                // 5. Display All Accounts
	                case 5:
	 
	                    System.out.println(
	                            "\n===== ALL ACCOUNTS =====");
	 
	                    service.getAccounts()
	                            .forEach(System.out::println);
	 
	                    break;
	 
	 
	                // 6. Deposit Money
	                case 6:
	 
	                    System.out.println(
	                            "\n===== DEPOSIT MONEY =====");
	 
	                    System.out.print("Account Number: ");
	 
	                    long depositAccount =
	                            Long.parseLong(scanner.nextLine());
	 
	                    System.out.print("Amount: ");
	 
	                    double depositAmount =
	                            Double.parseDouble(scanner.nextLine());
	 
	                    service.deposit(
	                            depositAccount,
	                            depositAmount);
	 
	                    System.out.println(
	                            "Money deposited successfully.");
	 
	                    System.out.println(
	                            "Current Balance: ₹" +
	                            service.checkBalance(depositAccount));
	 
	                    break;
	 
	 
	                // 7. Withdraw Money
	                case 7:
	 
	                    System.out.println(
	                            "\n===== WITHDRAW MONEY =====");
	 
	                    System.out.print("Account Number: ");
	 
	                    long withdrawAccount =
	                            Long.parseLong(scanner.nextLine());
	 
	                    System.out.print("Amount: ");
	 
	                    double withdrawAmount =
	                            Double.parseDouble(scanner.nextLine());
	 
	                    service.withdraw(
	                            withdrawAccount,
	                            withdrawAmount);
	 
	                    System.out.println(
	                            "Money withdrawn successfully.");
	 
	                    System.out.println(
	                            "Current Balance: ₹" +
	                            service.checkBalance(
	                                    withdrawAccount));
	 
	                    break;
	 
	 
	                // 8. Transfer Money
	                case 8:
	 
	                    System.out.println(
	                            "\n===== TRANSFER MONEY =====");
	 
	                    System.out.print("Source Account: ");
	 
	                    long sourceAccount =
	                            Long.parseLong(scanner.nextLine());
	 
	                    System.out.print("Target Account: ");
	 
	                    long targetAccount =
	                            Long.parseLong(scanner.nextLine());
	 
	                    System.out.print("Amount: ");
	 
	                    double transferAmount =
	                            Double.parseDouble(scanner.nextLine());
	 
	                    service.transfer(
	                            sourceAccount,
	                            targetAccount,
	                            transferAmount);
	 
	                    System.out.println(
	                            "Money transferred successfully.");
	 
	                    System.out.println(
	                            "Source Balance: ₹" +
	                            service.checkBalance(
	                                    sourceAccount));
	 
	                    System.out.println(
	                            "Target Balance: ₹" +
	                            service.checkBalance(
	                                    targetAccount));
	 
	                    break;
	 
	 
	                // 9. Check Account Balance
	                case 9:
	 
	                    System.out.println(
	                            "\n===== CHECK BALANCE =====");
	 
	                    System.out.print("Account Number: ");
	 
	                    long balanceAccount =
	                            Long.parseLong(scanner.nextLine());
	 
	                    double balance =
	                            service.checkBalance(
	                                    balanceAccount);
	 
	                    System.out.println(
	                            "Account Number: " +
	                            balanceAccount);
	 
	                    System.out.println(
	                            "Balance: ₹" + balance);
	 
	                    break;
	                    
	                    // 10. Display Transaction History
	                case 10:
	 
	                    System.out.println(
	                            "\n===== TRANSACTION HISTORY =====");
	 
	                    if (service.getTransactions().isEmpty()) {
	 
	                        System.out.println(
	                                "No transactions found.");
	 
	                    } else {
	 
	                        service.getTransactions()
	                                .stream()
	                                .sorted(
	                                        Comparator.comparing(
	                                                Transaction::
	                                                        getTransactionDate)
	                                                .reversed())
	                                .forEach(
	                                        System.out::println);
	                    }
	 
	                    break;
	 
	 
	                // 11. Banking Analytics
	                case 11:
	 
	                    System.out.println(
	                            "\n===== BANKING ANALYTICS =====");
	 
	                    System.out.println(
	                            "Total Customers: " +
	                            service.getCustomers().size());
	 
	                    System.out.println(
	                            "Premium Customers: " +
	                            service.countPremiumCustomers());
	 
	                    System.out.println(
	                            "Total Accounts: " +
	                            service.getAccounts().size());
	 
	                    System.out.println(
	                            "Total Balance: ₹" +
	                            service.getTotalBalance());
	 
	                    System.out.println(
	                            "Average Balance: ₹" +
	                            service.getAverageBalance());
	 
	                    System.out.println(
	                            "Highest Balance:");
	 
	                    service.getHighestBalance()
	                            .ifPresent(
	                                    System.out::println);
	 
	                    System.out.println(
	                            "Lowest Balance:");
	 
	                    service.getLowestBalance()
	                            .ifPresent(
	                                    System.out::println);
	 
	                    System.out.println(
	                            "Total Transactions: " +
	                            service.getTransactions().size());
	 
	                    System.out.println(
	                            "Total Deposits: ₹" +
	                            service.totalDeposits());
	 
	                    System.out.println(
	                            "Total Withdrawals: ₹" +
	                            service.totalWithdrawals());
	 
	                    System.out.println(
	                            "Accounts By Type: " +
	                            service.accountsByType());
	 
	                    System.out.println(
	                            "Customers By City: " +
	                            service.customersByCity());
	 
	                    break;
	 
	 
	                // 12. Customer Report
	                case 12:
	 
	                    System.out.println(
	                            "\n===== CUSTOMER REPORT =====");
	 
	                    System.out.println(
	                            "Total Customers: " +
	                            service.getCustomers().size());
	 
	                    System.out.println(
	                            "Premium Customers: " +
	                            service.countPremiumCustomers());
	 
	                    System.out.println(
	                            "\nCustomers By City:");
	 
	                    service.customersByCity()
	                            .forEach(
	                                    (cityName, count) ->
	                                            System.out.println(
	                                                    cityName +
	                                                    " : " +
	                                                    count));
	 
	                    System.out.println(
	                            "\nPremium Customers:");
	 
	                    service.getCustomers()
	                            .stream()
	                            .filter(c ->
	                                    c.getCustomerType()
	                                            .equalsIgnoreCase(
	                                                    "PREMIUM"))
	                            .forEach(
	                                    System.out::println);
	 
	                    break;
	 
	 
	                // 13. Account Type Report
	                case 13:
	 
	                    System.out.println(
	                            "\n===== ACCOUNT TYPE REPORT =====");
	 
	                    System.out.println(
	                            "\nNumber of Accounts:");
	 
	                    service.accountsByType()
	                            .forEach(
	                                    (type, count) ->
	                                            System.out.println(
	                                                    type +
	                                                    " : " +
	                                                    count));
	 
	                    System.out.println(
	                            "\nBalance By Account Type:");
	 
	                    service.balanceByAccountType()
	                            .forEach(
	                                    (type, total) ->
	                                            System.out.println(
	                                                    type +
	                                                    " : ₹" +
	                                                    total));
	 
	                    break;
	 
	 
	                // 14. Transaction Report
	                case 14:
	 
	                    System.out.println(
	                            "\n===== TRANSACTION REPORT =====");
	 
	                    List<Transaction> transactions =
	                            service.getTransactions();
	 
	                    System.out.println(
	                            "Total Transactions: " +
	                            transactions.size());
	 
	                    double deposits =
	                            transactions.stream()
	                                    .filter(t ->
	                                            t.getTransactionType()
	                                                    .equals(
	                                                            "DEPOSIT"))
	                                    .mapToDouble(
	                                            Transaction::
	                                                    getAmount)
	                                    .sum();
	 
	                    double withdrawals =
	                            transactions.stream()
	                                    .filter(t ->
	                                            t.getTransactionType()
	                                                    .equals(
	                                                            "WITHDRAW"))
	                                    .mapToDouble(
	                                            Transaction::
	                                                    getAmount)
	                                    .sum();
	 
	                    double average =
	                            transactions.stream()
	                                    .mapToDouble(
	                                            Transaction::
	                                                    getAmount)
	                                    .average()
	                                    .orElse(0);
	 
	                    System.out.println(
	                            "Total Deposits: ₹" +
	                            deposits);
	 
	                    System.out.println(
	                            "Total Withdrawals: ₹" +
	                            withdrawals);
	 
	                    System.out.println(
	                            "Average Transaction: ₹" +
	                            average);
	 
	                    System.out.println(
	                            "Transactions Above ₹50,000: " +
	                            transactions.stream()
	                                    .filter(t ->
	                                            t.getAmount() > 50000)
	                                    .count());
	 
	                    System.out.println(
	                            "\nTransaction Count By Type:");
	 
	                    transactions.stream()
	                            .collect(
	                                    Collectors.groupingBy(
	                                            Transaction::
	                                                    getTransactionType,
	                                            Collectors.counting()))
	                            .forEach(
	                                    (type, count) ->
	                                            System.out.println(
	                                                    type +
	                                                    " : " +
	                                                    count));
	 
	                    System.out.println(
	                            "\nHighest Transaction:");
	 
	                    transactions.stream()
	                            .max(
	                                    Comparator.comparingDouble(
	                                            Transaction::
	                                                    getAmount))
	                            .ifPresent(
	                                    System.out::println);
	 
	                    System.out.println(
	                            "\nLowest Transaction:");
	 
	                    transactions.stream()
	                            .min(
	                                    Comparator.comparingDouble(
	                                            Transaction::
	                                                    getAmount))
	                            .ifPresent(
	                                    System.out::println);
	 
	                    break;
	                    
	                 // 15. Generate Account Statement
	                case 15:
	 
	                    System.out.println(
	                            "\n===== ACCOUNT STATEMENT =====");
	 
	                    System.out.print(
	                            "Account Number: ");
	 
	                    long statementAccount =
	                            Long.parseLong(
	                                    scanner.nextLine());
	 
	                    BankAccount statementBankAccount =
	                            service.findAccount(
	                                    statementAccount);
	 
	                    Customer statementCustomer =
	                            service.findCustomer(
	                                    statementBankAccount
	                                            .getCustomerId());
	 
	                    String statement = """
	                            =========================================
	                                  BANK ACCOUNT STATEMENT
	                            =========================================
	                            Account Number : %d
	                            Customer ID    : %d
	                            Customer       : %s
	                            Email          : %s
	                            City           : %s
	                            Account Type   : %s
	                            Balance        : ₹%.2f
	                            Status         : %s
	                            =========================================
	                            """.formatted(
	                            statementBankAccount
	                                    .getAccountNumber(),
	                            statementCustomer
	                                    .getCustomerId(),
	                            statementCustomer
	                                    .getName(),
	                            statementCustomer
	                                    .getEmail(),
	                            statementCustomer
	                                    .getCity(),
	                            statementBankAccount
	                                    .getAccountType(),
	                            statementBankAccount
	                                    .getBalance(),
	                            statementBankAccount
	                                    .getStatus());
	 
	                    System.out.println(statement);
	 
	                    System.out.println(
	                            "Transaction History:");
	 
	                    service.getTransactions()
	                            .stream()
	                            .filter(t ->
	                                    t.getAccountNumber()
	                                            == statementAccount)
	                            .sorted(
	                                    Comparator.comparing(
	                                            Transaction::
	                                                    getTransactionDate)
	                                            .reversed())
	                            .forEach(
	                                    System.out::println);
	 
	                    break;
	 
	 
	                // 16. Exit
	                case 16:
	 
	                    System.out.println(
	                            "\nThank you for using " +
	                            "Banking Management System.");
	 
	                    scanner.close();
	 
	                    return;
	 
	 
	                default:
	 
	                    System.out.println(
	                            "Invalid choice. " +
	                            "Please enter 1 to 16.");
	            }
	 
	        } catch (Exception e) {
	 
	            System.out.println(
	                    "\nERROR: " + e.getMessage());
	        }
	 
		// TODO Auto-generated method stub

	}

}
}
