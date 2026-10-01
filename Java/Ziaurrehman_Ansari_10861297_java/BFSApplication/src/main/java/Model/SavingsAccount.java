package Model;

/*
Author : 
Date: 
Project : 
*/
public final class SavingsAccount extends BankAccount {
    int AccountNumber;
    int customerId;
    String accountType;
    double balance;
    String status;
        public SavingsAccount(int accountNumber, int customerId, double balance, String status) {
            super(accountNumber, customerId, balance, status,"SAVINGS_ACCOUNT");
//            this.accountType = "SAVINGS_ACCOUNT";
        }
}
