package bank.model;

public final class SavingsAccount extends BankAccount {

    private static final double MINIMUM_BALANCE = 1000.00;

    public SavingsAccount(long accountNumber,
                          int customerId,
                          double balance,
                          String status) {

        super(accountNumber, customerId, balance, status);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}