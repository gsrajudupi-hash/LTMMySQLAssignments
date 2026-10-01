package bfs;

import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {
        BankingService service = new BankingService(); service.seedData();
        int choice;
        do {
            menu();
            try { choice = Integer.parseInt(SC.nextLine()); execute(choice, service); }
            catch (NumberFormatException e) { choice=-1; System.out.println("Enter numeric input"); }
            catch (RuntimeException e) { choice=-1; System.out.println("Error: " + e.getMessage()); }
        } while (choice != 16);
    }

    private static void menu() {
        System.out.println("""
                1 Add Customer                 9 Check Account Balance
                2 Display All Customers       10 Display Transaction History
                3 Search Customer             11 Banking Analytics
                4 Add Bank Account            12 Customer Report
                5 Display All Accounts        13 Account Type Report
                6 Deposit Money               14 Transaction Report
                7 Withdraw Money              15 Generate Account Statement
                8 Transfer Money              16 Exit
                Enter choice:
                """);
    }

    private static void execute(int ch, BankingService s) {
        switch(ch) {
            case 1 -> { System.out.print("ID name email city phone type: "); s.addCustomer(new Customer(SC.nextInt(),SC.next(),SC.next(),SC.next(),SC.next(),SC.next().toUpperCase())); SC.nextLine(); }
            case 2 -> s.getCustomers().forEach(System.out::println);
            case 3 -> { System.out.print("Customer ID: "); System.out.println(s.findCustomer(Integer.parseInt(SC.nextLine()))); }
            case 4 -> { System.out.print("Customer ID, type, opening balance: "); BankAccount a=s.addAccount(SC.nextInt(),SC.next(),SC.nextDouble()); SC.nextLine(); System.out.println(a); }
            case 5 -> s.getAccounts().forEach(System.out::println);
            case 6 -> { System.out.print("Account and amount: "); s.deposit(SC.nextLong(),SC.nextDouble()); SC.nextLine(); }
            case 7 -> { System.out.print("Account and amount: "); s.withdraw(SC.nextLong(),SC.nextDouble()); SC.nextLine(); }
            case 8 -> { System.out.print("Source target amount: "); s.transfer(SC.nextLong(),SC.nextLong(),SC.nextDouble()); SC.nextLine(); }
            case 9 -> { System.out.print("Account: "); System.out.println("Balance: " + s.checkBalance(Long.parseLong(SC.nextLine()))); }
            case 10 -> s.getTransactions().forEach(System.out::println);
            case 11 -> s.generateDashboard();
            case 12 -> { s.getCustomers().forEach(System.out::println); BankingReport.printCustomerJson(s.getCustomers().get(0)); }
            case 13 -> s.streamFeatureDemo();
            case 14 -> s.getTransactions().stream().sorted((a,b)->Double.compare(b.getAmount(),a.getAmount())).limit(5).forEach(System.out::println);
            case 15 -> { System.out.print("Account: "); BankingReport.printAccountStatement(s,Long.parseLong(SC.nextLine())); }
            case 16 -> System.out.println("Thank you");
            default -> System.out.println("Invalid choice");
        }
    }
}
