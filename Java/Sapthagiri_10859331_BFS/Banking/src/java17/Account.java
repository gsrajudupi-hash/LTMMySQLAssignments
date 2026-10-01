package java17;

public sealed abstract class Account
        permits SavingsAccount, CurrentAccount, LoanAccount {

    private final long accountNumber;
    private final double balance;

    protected Account(long accountNumber, double balance) {

        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public abstract String getAccountType();
}
