package bank.model;

public non-sealed class LoanAccount extends BankAccount {

    public LoanAccount(long accountNumber,
                       int customerId,
                       double balance,
                       String status) {

        super(accountNumber, customerId, balance, status);
    }

    @Override
    public String getAccountType() {
        return "LOAN";
    }

    @Override
    public double getMinimumBalance() {
        return 0.00;
    }
}