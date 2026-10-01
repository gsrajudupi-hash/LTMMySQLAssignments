package bfs;
public final class SavingsAccount extends BankAccount {
    public SavingsAccount(long no, int customerId, double balance, String status) { super(no, customerId, "SAVINGS", balance, status); }
    @Override public double minimumBalance() { return 1000; }
}
