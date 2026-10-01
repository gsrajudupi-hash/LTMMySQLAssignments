package bfs;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BankingService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private int transactionSequence = 1;
    private final Supplier<Long> accountNumberGenerator = () -> System.currentTimeMillis() + accounts.size();

    public List<Customer> getCustomers() { return customers; }
    public List<BankAccount> getAccounts() { return accounts; }
    public List<Transaction> getTransactions() { return transactions; }

    public void addCustomer(Customer customer) {
        if (customers.stream().anyMatch(c -> c.getCustomerId() == customer.getCustomerId())) throw new InvalidTransactionException("Customer ID already exists");
        customers.add(customer);
    }

    public Customer findCustomer(int id) {
        return customers.stream().filter(c -> c.getCustomerId() == id).findFirst().orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    public BankAccount addAccount(int customerId, String type, double openingBalance) {
        findCustomer(customerId);
        long number = accountNumberGenerator.get();
        BankAccount account = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(number, customerId, openingBalance, "ACTIVE");
            case "CURRENT" -> new CurrentAccount(number, customerId, openingBalance, "ACTIVE");
            case "LOAN" -> new LoanAccount(number, customerId, openingBalance, "ACTIVE");
            default -> throw new InvalidAccountException("Unknown account type");
        };
        if (openingBalance < account.minimumBalance()) throw new InsufficientBalanceException("Minimum opening balance is " + account.minimumBalance());
        accounts.add(account);
        return account;
    }

    public BankAccount findAccount(long number) {
        return accounts.stream().filter(a -> a.getAccountNumber() == number).findFirst().orElseThrow(() -> new InvalidAccountException("Account not found: " + number));
    }

    public void deposit(long number, double amount) {
        validateAmount(amount); BankAccount a = findAccount(number);
        BankingOperation operation = (value, balance) -> balance + value;
        a.setBalance(operation.execute(amount, a.getBalance()));
        addTransaction(number, "DEPOSIT", amount, "Cash deposit");
    }

    public void withdraw(long number, double amount) {
        validateAmount(amount); BankAccount a = findAccount(number);
        BankingOperation operation = (value, balance) -> balance - value;
        double newBalance = operation.execute(amount, a.getBalance());
        if (newBalance < a.minimumBalance()) throw new InsufficientBalanceException("Minimum balance violation");
        a.setBalance(newBalance); addTransaction(number, "WITHDRAW", amount, "Cash withdrawal");
    }

    public void transfer(long source, long target, double amount) {
        if (source == target) throw new InvalidTransactionException("Source and target accounts must differ");
        withdraw(source, amount); deposit(target, amount);
        addTransaction(source, "TRANSFER", amount, "Transfer to " + target);
        addTransaction(target, "TRANSFER", amount, "Transfer from " + source);
    }

    public double calculateInterest(long number, double rate) {
        BankAccount a = findAccount(number); double interest = a.getBalance() * rate / 100;
        deposit(number, interest); addTransaction(number, "INTEREST", interest, "Interest at " + rate + "%"); return interest;
    }

    public double checkBalance(long number) { return findAccount(number).getBalance(); }
    private void validateAmount(double amount) { if (amount <= 0) throw new InvalidTransactionException("Amount must be positive"); }
    private void addTransaction(long no, String type, double amount, String description) { transactions.add(new Transaction(transactionSequence++, no, type, amount, LocalDateTime.now(), description)); }

    public void seedData() {
        String[][] data = {{"Rahul","rahul@gmail.com","Bangalore","9876543210","PREMIUM"},{"Priya","priya@gmail.com","Mangalore","9876543211","REGULAR"},{"Arun","arun@gmail.com","Mysore","9876543212","PREMIUM"},{"Sneha","sneha@gmail.com","Udupi","9876543213","REGULAR"},{"Kiran","kiran@gmail.com","Bangalore","9876543214","PREMIUM"},{"Anita","anita@gmail.com","Bangalore","9876543215","REGULAR"},{"Deepak","deepak@gmail.com","Mangalore","9876543216","PREMIUM"},{"Meera","meera@gmail.com","Mysore","9876543217","REGULAR"},{"Vijay","vijay@gmail.com","Udupi","9876543218","PREMIUM"},{"Asha","asha@gmail.com","Bangalore","9876543219","REGULAR"}};
        for (int i=0;i<data.length;i++) addCustomer(new Customer(101+i,data[i][0],data[i][1],data[i][2],data[i][3],data[i][4]));
        String[] types={"SAVINGS","CURRENT","LOAN"};
        for(int i=0;i<15;i++) addAccount(101+(i%10),types[i%3],types[i%3].equals("CURRENT")?10000+i*3000:5000+i*4000);
        for(int i=0;i<30;i++) deposit(accounts.get(i%15).getAccountNumber(),1000+(i*500));
    }

    public void generateDashboard() {
        double total = accounts.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum);
        double avg = accounts.stream().mapToDouble(BankAccount::getBalance).average().orElse(0);
        double highest = accounts.stream().mapToDouble(BankAccount::getBalance).max().orElse(0);
        Map<String,Long> byType = accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
        Map<String,Long> byCity = customers.stream().collect(Collectors.groupingBy(Customer::getCity, Collectors.counting()));
        String report = """
                ========== BANKING ANALYTICS ==========
                Customers       : %d
                Premium         : %d
                Accounts        : %d
                Total balance   : %.2f
                Average balance : %.2f
                Highest balance : %.2f
                Transactions    : %d
                Deposits total  : %.2f
                Withdraw total  : %.2f
                Accounts/type   : %s
                Customers/city  : %s
                =======================================
                """.formatted(customers.size(), customers.stream().filter(c -> "PREMIUM".equals(c.getCustomerType())).count(), accounts.size(), total, avg, highest, transactions.size(), transactionTotal("DEPOSIT"), transactionTotal("WITHDRAW"), byType, byCity);
        System.out.println(report);
    }

    private double transactionTotal(String type) { return transactions.stream().filter(t -> type.equals(t.getTransactionType())).mapToDouble(Transaction::getAmount).sum(); }

    public void streamFeatureDemo() {
        System.out.println("Premium: " + customers.stream().filter(c -> "PREMIUM".equals(c.getCustomerType())).map(Customer::getName).collect(Collectors.joining(", ")));
        System.out.println("Cities distinct: " + customers.stream().map(Customer::getCity).distinct().sorted().toList());
        System.out.println("Skip 2, limit 3: " + customers.stream().skip(2).limit(3).map(Customer::getName).toList());
        System.out.println("Partition premium: " + customers.stream().collect(Collectors.partitioningBy(c -> "PREMIUM".equals(c.getCustomerType()))));
        System.out.println("All account numbers: " + customers.stream().flatMap(c -> accounts.stream().filter(a -> a.getCustomerId() == c.getCustomerId())).map(BankAccount::getAccountNumber).toList());
        System.out.println("Balance sum: " + accounts.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum));
        System.out.println("Balance by type: " + accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance))));
        System.out.println("Average by type: " + accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.averagingDouble(BankAccount::getBalance))));
    }
}
