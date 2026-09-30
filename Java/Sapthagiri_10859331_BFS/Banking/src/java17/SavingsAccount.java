package java17;

public final class SavingsAccount extends Account {

    public SavingsAccount(
            long accountNumber,
            double balance) {

        super(accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
}
