package model;

public sealed abstract class BankAccount
        permits SavingsAccount,
        CurrentAccount,
        LoanAccount {

    private long accountNumber;
    private int customerId;
    private String accountType;
    private double balance;
    private String status;

    public BankAccount(long accountNumber,
                       int customerId,
                       String accountType,
                       double balance,
                       String status) {

        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }

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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}