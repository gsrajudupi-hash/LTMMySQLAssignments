package Model;

/*
Author : 
Date: 
Project : 
*/
public final class CurrentAccount extends BankAccount {
    int AccountNumber;
    int customerId;
    String accountType;
    double balance;
    String status;
    public CurrentAccount(int accountNumber, int customerId, double balance, String status) {
        super(accountNumber, customerId, balance, status,"CURRENT_ACCOUNT");
    }
}
