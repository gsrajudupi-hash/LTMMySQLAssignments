package com.bank;

import com.bank.data.SampleData;
import com.bank.exception.*;
import com.bank.functional.BankingOperation;
import com.bank.model.*;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;
import java.time.*;
//import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;

public class Main {
    private final Scanner sc = new Scanner(System.in);
    private final BankingService service = new BankingService(SampleData.customers(), SampleData.accounts(),
            SampleData.transactions());
    private final Supplier<Long> accountNumberGenerator = System::currentTimeMillis;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
//        demoFeatures();
        while (true) {
            menu();
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 16) {
                    System.out.println("Thank you for using BFS Banking.");
                    break;
                }
                handle(choice);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void menu() {
        System.out.print("""
                =========== Banking Management System ===========
                1. Add Customer                 9. Check Account Balance
                2. Display All Customers       10. Display Transaction History
                3. Search Customer             11. Banking Analytics
                4. Add Bank Account            12. Customer Report
                5. Display All Accounts        13. Account Type Report
                6. Deposit Money               14. Transaction Report
                7. Withdraw Money              15. Generate Account Statement
                8. Transfer Money              16. Exit
                Choose an option: """);
    }

    private void handle(int c) {
        switch (c) {
            case 1 -> addCustomer();
            case 2 -> service.customers().forEach(System.out::println);
            case 3 -> {
                System.out.print("Customer ID: ");
                System.out.println(service.findCustomer(Integer.parseInt(sc.nextLine())));
            }
            case 4 -> addAccount();
            case 5 -> service.accounts().forEach(a -> {
                System.out.println(a);
                BankingReport.describeAccount(a);
            });
            case 6 -> money("DEPOSIT");
            case 7 -> money("WITHDRAW");
            case 8 -> transfer();
            case 9 -> {
                System.out.print("Account number: ");
                System.out.printf("Balance: Rs %,.2f%n", service.checkBalance(Long.parseLong(sc.nextLine())));
            }
            case 10 -> history();
            case 11 -> System.out
                    .println(BankingReport.dashboard(service.customers(), service.accounts(), service.transactions()));
            case 12 -> BankingReport.customerReport(service.customers(), service.accounts());
            case 13 -> BankingReport.accountReport(service.accounts());
            case 14 -> BankingReport.transactionReport(service.transactions());
            case 15 -> statement();
            default -> System.out.println("Choose 1 to 16.");
        }
    }

    private void addCustomer() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: ");
        String n = sc.nextLine();
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("City: ");
        String city = sc.nextLine();
        System.out.print("Phone: ");
        String p = sc.nextLine();
        System.out.print("Type PREMIUM/REGULAR: ");
        String t = sc.nextLine().toUpperCase();
        service.addCustomer(new Customer(id, n, e, city, p, t));
        System.out.println("Customer added.");
    }

    private void addAccount() {
        System.out.print("Customer ID: ");
        int customerId = Integer.parseInt(sc.nextLine());
        System.out.print("Type SAVINGS/CURRENT/LOAN: ");
        String type = sc.nextLine().toUpperCase();
        System.out.print("Opening balance: ");
        double balance = Double.parseDouble(sc.nextLine());
        long number = accountNumberGenerator.get();
        BankAccount a = switch (type) {
            case "SAVINGS" -> new SavingsAccount(number, customerId, balance, "ACTIVE");
            case "CURRENT" -> new CurrentAccount(number, customerId, balance, "ACTIVE");
            case "LOAN" -> new LoanAccount(number, customerId, balance, "ACTIVE");
            default -> throw new InvalidAccountException("Unknown account type");
        };
        service.addAccount(a);
        System.out.println("Created account " + number);
    }

    private void money(String type) {
        System.out.print("Account number: ");
        long n = Long.parseLong(sc.nextLine());
        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (type.equals("DEPOSIT"))
            service.deposit(n, amount);
        else
            service.withdraw(n, amount);
        System.out.println(type + " successful.");
    }

    private void transfer() {
        System.out.print("Source: ");
        long s = Long.parseLong(sc.nextLine());
        System.out.print("Target: ");
        long t = Long.parseLong(sc.nextLine());
        System.out.print("Amount: ");
        double a = Double.parseDouble(sc.nextLine());
        service.transfer(s, t, a);
        System.out.println("Transfer successful.");
    }

    private void history() {
        System.out.println("1 All 2 Deposits 3 Withdrawals 4 Transfers 5 Above 50000 6 Sorted 7 Latest five");
        int c = Integer.parseInt(sc.nextLine());
        var stream = service.transactions().stream();
        switch (c) {
            case 2 -> stream.filter(t -> t.getTransactionType().equals("DEPOSIT")).forEach(System.out::println);
            case 3 -> stream.filter(t -> t.getTransactionType().equals("WITHDRAW")).forEach(System.out::println);
            case 4 -> stream.filter(t -> t.getTransactionType().equals("TRANSFER")).forEach(System.out::println);
            case 5 -> stream.filter(t -> t.getAmount() > 50000).forEach(System.out::println);
            case 6 -> stream.sorted(Comparator.comparing(Transaction::getTransactionDate)).forEach(System.out::println);
            case 7 -> stream.sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).limit(5)
                    .forEach(System.out::println);
            default -> stream.forEach(System.out::println);
        }
    }

    private void statement() {
        System.out.print("Account number: ");
        BankAccount a = service.getAccount(Long.parseLong(sc.nextLine()));
        Customer c = service.findCustomer(a.getCustomerId());
        System.out.println(BankingReport.statement(a, c));
        System.out.println(BankingReport.customerJson(c));
        System.out.println(BankingReport.accountJson(a));
        service.transactions().stream().filter(t -> t.getAccountNumber() == a.getAccountNumber()).findFirst()
                .ifPresent(t -> System.out.println(BankingReport.transactionJson(t)));
    }

//    private void demoFeatures() {
//        BankingOperation deposit = (amount, balance) -> balance + amount,
//                withdraw = (amount, balance) -> balance - amount,
//                interest = (rate, balance) -> balance + (balance * rate / 100);
//        //System.out.println("Lambda demo: " + deposit.execute(1000, 5000) + ", " + withdraw.execute(500, 5000) + ", "
//                //+ interest.execute(5, 5000));
//        LocalDate date = LocalDate.now();
//        LocalTime time = LocalTime.now();
//        LocalDateTime now = LocalDateTime.of(date, time);
//        System.out.println("Started: " + now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
//    }
}
