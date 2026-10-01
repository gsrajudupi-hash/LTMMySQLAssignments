package Model;

/*
Author : 
Date: 
Project : 
*/
public final class LoanAccount extends BankAccount {
    int AccountNumber;
    int customerId;
    String accountType;
    double balance;
    String status;

    public LoanAccount(int accountNumber, int customerId, double balance, String status) {
        super(accountNumber, customerId, balance, status,"LOAN_ACCOUNT");
//        this.accountType = "LOAN_ACCOUNT";
    }

    
}
