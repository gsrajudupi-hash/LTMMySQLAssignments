package java17;


public final class CurrentAccount extends Account {

    public CurrentAccount(
            long accountNumber,
            double balance) {

        super(accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }
}
