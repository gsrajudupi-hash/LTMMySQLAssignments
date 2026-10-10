package model;


public final class SavingsAccount
extends BankAccount {

public SavingsAccount(long accountNumber,
                  int customerId,
                  double balance,
                  String status) {

super(
        accountNumber,
        customerId,
        "SAVINGS",
        balance,
        status
);
}
}