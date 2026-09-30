package java17;

public non-sealed class LoanAccount extends Account {

    public LoanAccount(
            long accountNumber,
            double balance) {

        super(accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "LOAN";
    }
}
