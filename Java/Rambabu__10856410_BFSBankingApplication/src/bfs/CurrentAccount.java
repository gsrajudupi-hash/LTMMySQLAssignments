package bfs;
public final class CurrentAccount extends BankAccount {
    public CurrentAccount(long no, int customerId, double balance, String status) { super(no, customerId, "CURRENT", balance, status); }
    @Override public double minimumBalance() { return 5000; }
}
