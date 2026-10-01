package Model;

/*
Author : 
Date: 
Project : 
*/
public sealed class BankAccount permits SavingsAccount, CurrentAccount, LoanAccount {
    int AccountNumber;
    int customerId;
    String accountType;
    double balance = 5000;
    String status;

    public BankAccount(int accountNumber, int customerId, double balance, String status, String accountType) {
        AccountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.status = status;
        this.accountType = accountType;
    }

    public int getAccountNumber() {
        return AccountNumber;
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

    @Override
    public String toString() {
        return "BankAccount{" +
                "AccountNumber=" + AccountNumber +
                ", customerId=" + customerId +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                '}';
    }
}

