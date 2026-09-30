package model;

public non-sealed class LoanAccount
        extends BankAccount {

    public LoanAccount(long accountNumber,
                       int customerId,
                       double balance,
                       String status) {

        super(
                accountNumber,
                customerId,
                "LOAN",
                balance,
                status
        );
    }
}