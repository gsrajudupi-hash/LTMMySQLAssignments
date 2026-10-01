package Service;

import Exceptions.InsufficientBalance;
import Exceptions.InvalidAccountException;
import Model.*;

import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

/*
Author : 
Date: 
Project : 
*/
public class BankingService {

    Map<Integer, BankAccount> accounts ;
    TransactionService transactionService;
    CustomerService cs;
    public BankingService(){
        transactionService = new TransactionService();
        cs = new CustomerService();
        accounts = new HashMap<>();

        accounts.put(1001,
                new SavingsAccount(1001, 1, 25000.0, "ACTIVE"));

        accounts.put(1002,
                new CurrentAccount(1002, 2, 50000.0, "ACTIVE"));

        accounts.put(1003,
                new LoanAccount(1003, 3, -150000.0, "ACTIVE"));

        accounts.put(1004,
                new SavingsAccount(1004, 4, 12000.0, "ACTIVE"));

        accounts.put(1005,
                new CurrentAccount(1005, 5, 75000.0, "ACTIVE"));

        accounts.put(1006,
                new LoanAccount(1006, 6, -300000.0, "ACTIVE"));

        accounts.put(1007,
                new SavingsAccount(1007, 7, 4500.0, "INACTIVE"));

        accounts.put(1008,
                new CurrentAccount(1008, 8, 92000.0, "ACTIVE"));

        accounts.put(1009,
                new LoanAccount(1009, 9, -50000.0, "ACTIVE"));

        accounts.put(1010,
                new SavingsAccount(1010, 10, 18000.0, "ACTIVE"));

        accounts.put(1011,
                new CurrentAccount(1011, 1, 30000.0, "ACTIVE"));

        accounts.put(1012,
                new SavingsAccount(1012, 2, 8500.0, "ACTIVE"));
    }
    BankingOperation withdraw = (double amount , double balance) -> {
        if(balance < amount) throw new InsufficientBalance();
        return balance-amount;
    };

    public Map<Integer, BankAccount> getAccounts(){return accounts;}

    BankingOperation deposit = (double amount , double balance) -> {
        if(amount < 0) throw new RuntimeException("Cannot deposit less than 0");
        return balance+amount;
    };


    public void withdrawFromAccount(int accountNumber, double amount) throws InsufficientBalance {
        if(!accounts.containsKey(accountNumber)) throw new InvalidAccountException();
        BankAccount account = accounts.get(accountNumber);
        account.setBalance(withdraw.execute(amount,account.getBalance()));
        transactionService.registerTransaction(accountNumber,amount,"DEBIT");
        System.out.println(account.getBalance());

    }

    public void depositToAccount(int accountNumber, double amount) throws InsufficientBalance {
        if(!accounts.containsKey(accountNumber)) throw new InvalidAccountException();
        BankAccount account = accounts.get(accountNumber);
        account.setBalance(deposit.execute(amount,account.getBalance()));
        transactionService.registerTransaction(accountNumber,amount,"CREDIT");
        System.out.println(account.getBalance());
    }


    public void transfer(int account1, int account2, double amount) throws InsufficientBalance {
        withdrawFromAccount(account1, amount);
        depositToAccount(account2, amount);


    }
    public void displayAllAccounts(){
        this.accounts.values().forEach(a-> System.out.println(a.toString()));
    }

    public void checkAccountBalance(int accountNumber){
        if(!accounts.containsKey(accountNumber)) throw new InvalidAccountException();
        System.out.println("Balance for Account is : " + accounts.get(accountNumber).getBalance());
    }

    public void addBankAccount(){
        BankAccount account;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter customer Id");
        int customerId = sc.nextInt();
        System.out.println("Enter bank account type");
        String accountType = sc.next();
        int accountNumber = Random.from(RandomGenerator.getDefault()).nextInt(10000, 99999);
        account = switch (accountType){
            case "Loan" -> new LoanAccount(accountNumber,customerId,0,"ACTIVE");
            case "Savings" -> new SavingsAccount(accountNumber,customerId,0,"ACTIVE");
            case "Current" -> new CurrentAccount(accountNumber,customerId,0,"ACTIVE");
            default -> {
                throw new RuntimeException("Account Type Invalid");
            }
        };
        System.out.println("Account Created");
        System.out.println(account.toString());

    }

    public void BankingReport(){
        System.out.println(this.accounts.values().stream()
                .collect(Collectors.groupingBy(
                        BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance))));
    }

    public void customerReport(){
        System.out.println("Customer with highest balance is : " +
                this.accounts.values().stream().min(Comparator.comparingDouble(BankAccount::getBalance))
                );
        List<Integer> highValuingCustomersId = this.accounts.values().stream().filter(a -> a.getBalance()>1000)
                .map(a->a.getCustomerId())
                .toList();
        System.out.println("High valuing Customers");
        highValuingCustomersId.forEach(id-> System.out.println(cs.getCustomer(id).name()));


    }

}
