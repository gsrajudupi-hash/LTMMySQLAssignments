package bank.model;

public final class CurrentAccount extends BankAccount {

    private static final double MINIMUM_BALANCE = 5000.00;

    public CurrentAccount(long accountNumber,
                          int customerId,
                          double balance,
                          String status) {

        super(accountNumber, customerId, balance, status);
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }

    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}