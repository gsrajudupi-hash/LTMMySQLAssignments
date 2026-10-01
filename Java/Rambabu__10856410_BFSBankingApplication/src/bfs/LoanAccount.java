package bfs;
public non-sealed class LoanAccount extends BankAccount {
    public LoanAccount(long no, int customerId, double balance, String status) { super(no, customerId, "LOAN", balance, status); }
    @Override public double minimumBalance() { return 0; }
}
