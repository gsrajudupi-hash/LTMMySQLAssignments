package bank.model;

public sealed abstract class BankAccount
        permits SavingsAccount, CurrentAccount, LoanAccount {

    private final long accountNumber;
    private final int customerId;
    private double balance;
    private String status;

    protected BankAccount(long accountNumber,
                          int customerId,
                          double balance,
                          String status) {

        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.status = status;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
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

    public abstract String getAccountType();

    public abstract double getMinimumBalance();

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", customerId=" + customerId +
                ", accountType='" + getAccountType() + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                '}';
    }
}