package com.bank;

import com.bank.model.*;
import com.bank.record.CustomerRecord;
import com.bank.record.TransactionRecord;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;

import java.util.Scanner;

public class Main {
    private static final BankingService service = new BankingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();
        demonstrateFeatures();
        runMenu();
    }

    private static void loadSampleData() {
        service.addCustomer(new Customer(101, "Rahul", "rahul@gmail.com", "Bangalore", "9876543210", "PREMIUM"));
        service.addCustomer(new Customer(102, "Priya", "priya@gmail.com", "Mangalore", "9876543211", "REGULAR"));
        service.addCustomer(new Customer(103, "Arun", "arun@gmail.com", "Mysore", "9876543212", "PREMIUM"));
        service.addCustomer(new Customer(104, "Sneha", "sneha@gmail.com", "Udupi", "9876543213", "REGULAR"));
        service.addCustomer(new Customer(105, "Kiran", "kiran@gmail.com", "Bangalore", "9876543214", "PREMIUM"));
        service.addCustomer(new Customer(106, "Anil", "anil@gmail.com", "Pune", "9876543215", "REGULAR"));
        service.addCustomer(new Customer(107, "Neha", "neha@gmail.com", "Hyderabad", "9876543216", "PREMIUM"));
        service.addCustomer(new Customer(108, "Vijay", "vijay@gmail.com", "Chennai", "9876543217", "REGULAR"));
        service.addCustomer(new Customer(109, "Asha", "asha@gmail.com", "Bangalore", "9876543218", "PREMIUM"));
        service.addCustomer(new Customer(110, "Rohit", "rohit@gmail.com", "Mangalore", "9876543219", "REGULAR"));

        service.addAccount(new SavingsAccount(100001, 101, 85000, "ACTIVE"));
        service.addAccount(new CurrentAccount(100002, 101, 60000, "ACTIVE"));
        service.addAccount(new SavingsAccount(100003, 102, 45000, "ACTIVE"));
        service.addAccount(new LoanAccount(100004, 103, 125000, "ACTIVE"));
        service.addAccount(new SavingsAccount(100005, 104, 25000, "ACTIVE"));
        service.addAccount(new CurrentAccount(100006, 105, 95000, "ACTIVE"));
        service.addAccount(new SavingsAccount(100007, 106, 30000, "ACTIVE"));
        service.addAccount(new CurrentAccount(100008, 107, 110000, "ACTIVE"));
        service.addAccount(new LoanAccount(100009, 108, 150000, "ACTIVE"));
        service.addAccount(new SavingsAccount(100010, 109, 72000, "ACTIVE"));
        service.addAccount(new CurrentAccount(100011, 110, 52000, "ACTIVE"));
        service.addAccount(new SavingsAccount(100012, 102, 18000, "ACTIVE"));
        service.addAccount(new CurrentAccount(100013, 103, 80000, "ACTIVE"));
        service.addAccount(new SavingsAccount(100014, 104, 67000, "ACTIVE"));
        service.addAccount(new LoanAccount(100015, 105, 200000, "ACTIVE"));

        long[] accountNumbers = {100001,100002,100003,100004,100005,100006,100007,100008,100009,100010,100011,100012,100013,100014,100015};
        for (int i = 0; i < 30; i++) {
            long number = accountNumbers[i % accountNumbers.length];
            if (i % 3 == 0) service.deposit(number, 1000 + i * 250);
            else if (i % 3 == 1) {
                try { service.withdraw(number, 500 + i * 50); } catch (RuntimeException ignored) { }
            } else service.calculateInterest(number, 0.10);
        }
    }

    private static void demonstrateFeatures() {
        BankingOperation deposit = (amount, balance) -> balance + amount;
        BankingOperation withdrawal = (amount, balance) -> balance - amount;
        BankingOperation interest = (rate, balance) -> balance + balance * rate / 100;
        System.out.println("Lambda deposit result: " + deposit.execute(5000, 10000));
        System.out.println("Lambda withdrawal result: " + withdrawal.execute(2000, 10000));
        System.out.println("Lambda interest result: " + interest.execute(5, 10000));

        CustomerRecord record1 = new CustomerRecord(101, "Rahul", "Bangalore", "PREMIUM");
        CustomerRecord record2 = new CustomerRecord(101, "Rahul", "Bangalore", "PREMIUM");
        TransactionRecord transactionRecord = new TransactionRecord(1, 100001, "DEPOSIT", 5000);
        System.out.println("Customer record accessor: " + record1.name());
        System.out.println("Customer records equal: " + record1.equals(record2));
        System.out.println("Record hash code: " + record1.hashCode());
        System.out.println("Transaction record: " + transactionRecord);
        BankingReport.displayAccountType(service.getAccounts().get(0));
        System.out.println("Switch classification: " + BankingReport.classify("DEPOSIT"));
        BankingReport.demonstrateJava8(service);
    }

    private static void runMenu() {
        while (true) {
            showMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addCustomer();
                    case 2 -> service.getCustomers().forEach(System.out::println);
                    case 3 -> searchCustomer();
                    case 4 -> addAccount();
                    case 5 -> service.getAccounts().forEach(System.out::println);
                    case 6 -> deposit();
                    case 7 -> withdraw();
                    case 8 -> transfer();
                    case 9 -> checkBalance();
                    case 10 -> transactionHistory();
                    case 11 -> BankingReport.dashboard(service);
                    case 12 -> customerReport();
                    case 13 -> System.out.println(service.accountCountByType());
                    case 14 -> BankingReport.transactionAnalysis(service);
                    case 15 -> accountStatement();
                    case 16 -> { System.out.println("Thank you"); return; }
                    default -> System.out.println("Invalid choice");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void showMenu() {
        System.out.println("""
                \n========== BANKING MENU ==========
                1. Add Customer
                2. Display All Customers
                3. Search Customer
                4. Add Bank Account
                5. Display All Accounts
                6. Deposit Money
                7. Withdraw Money
                8. Transfer Money
                9. Check Account Balance
                10. Display Transaction History
                11. Banking Analytics
                12. Customer Report
                13. Account Type Report
                14. Transaction Report
                15. Generate Account Statement
                16. Exit
                Enter choice:
                """);
    }

    private static void addCustomer() {
        System.out.print("ID: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("City: "); String city = scanner.nextLine();
        System.out.print("Phone: "); String phone = scanner.nextLine();
        System.out.print("Type (PREMIUM/REGULAR): "); String type = scanner.nextLine().toUpperCase();
        service.addCustomer(new Customer(id, name, email, city, phone, type));
        System.out.println("Customer added");
    }

    private static void searchCustomer() {
        System.out.print("Customer ID: ");
        System.out.println(service.findCustomer(Integer.parseInt(scanner.nextLine())));
    }

    private static void addAccount() {
        System.out.print("Account number: "); long number = Long.parseLong(scanner.nextLine());
        System.out.print("Customer ID: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Type (SAVINGS/CURRENT/LOAN): "); String type = scanner.nextLine().toUpperCase();
        System.out.print("Opening balance: "); double balance = Double.parseDouble(scanner.nextLine());
        BankAccount account = switch (type) {
            case "SAVINGS" -> new SavingsAccount(number, id, balance, "ACTIVE");
            case "CURRENT" -> new CurrentAccount(number, id, balance, "ACTIVE");
            case "LOAN" -> new LoanAccount(number, id, balance, "ACTIVE");
            default -> throw new IllegalArgumentException("Invalid account type");
        };
        service.addAccount(account);
        System.out.println("Account added");
    }

    private static void deposit() {
        System.out.print("Account number: "); long number = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: "); double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("New balance: " + service.deposit(number, amount));
    }
    private static void withdraw() {
        System.out.print("Account number: "); long number = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: "); double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("New balance: " + service.withdraw(number, amount));
    }
    private static void transfer() {
        System.out.print("Source account: "); long source = Long.parseLong(scanner.nextLine());
        System.out.print("Target account: "); long target = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: "); double amount = Double.parseDouble(scanner.nextLine());
        service.transfer(source, target, amount);
        System.out.println("Transfer successful");
    }
    private static void checkBalance() {
        System.out.print("Account number: "); long number = Long.parseLong(scanner.nextLine());
        System.out.println("Balance: " + service.checkBalance(number));
    }
    private static void transactionHistory() {
        System.out.print("Account number: "); long number = Long.parseLong(scanner.nextLine());
        service.history(number).forEach(System.out::println);
        System.out.println("Latest five overall: " + service.latestFiveTransactions());
    }
    private static void customerReport() {
        service.getCustomers().forEach(c -> System.out.println(BankingReport.customerJson(c)));
    }
    private static void accountStatement() {
        System.out.print("Account number: "); long number = Long.parseLong(scanner.nextLine());
        BankAccount account = service.findAccount(number);
        Customer customer = service.findCustomer(account.getCustomerId());
        System.out.println(BankingReport.accountStatement(customer, account));
    }
}
