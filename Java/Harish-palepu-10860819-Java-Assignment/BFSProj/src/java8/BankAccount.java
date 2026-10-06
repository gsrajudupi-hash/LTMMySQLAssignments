package java8;

/**
 * Class Name : BankAccount
 * Created By : 10860819
 * Created Date : 9/29/2026
 * Created Time : 9:53 AM
 */
public class BankAccount {
    private long accountNumber;
    private int customerId;
    private String accountType;
    private double balance;
    private String status;

    //Parameterized constructor
    public BankAccount(long accountNumber, int customerId, String accountType,
                       double balance, String status) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }

    //Getters and Setters
    public long getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    //toString() method
    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", customerId=" + customerId +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BankAccount that = (BankAccount) obj;
        return accountNumber == that.accountNumber;
    }
    @Override
    public int hashCode() {
        return Long.hashCode(accountNumber);
    }

    //Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ", New Balance: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    //Withdraw method
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ", New Balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    //displayAccountDetails method
    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: " + balance);
        System.out.println("Status: " + status);
    }

    // Display account details
    public void displayAccount() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Customer ID : " + customerId);
        System.out.println("Account Type : " + accountType);
        System.out.println("Balance : " + balance);
        System.out.println("Status : " + status);
    }
}