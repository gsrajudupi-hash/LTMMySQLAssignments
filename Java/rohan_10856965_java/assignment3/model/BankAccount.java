package assignment3.model;
public sealed abstract class BankAccount permits SavingsAccount,CurrentAccount,LoanAccount {
 private final long accountNumber; private final int customerId; private double balance; private AccountStatus status;
 protected BankAccount(long n,int c,double b,AccountStatus s){if(n<=0||c<=0||b<0) throw new IllegalArgumentException("Invalid account data"); accountNumber=n; customerId=c; balance=b; status=s;}
 public long accountNumber(){return accountNumber;} public int customerId(){return customerId;} public double balance(){return balance;} public AccountStatus status(){return status;}
 public void status(AccountStatus s){status=s;} public void credit(double a){balance+=a;} public void debit(double a){balance-=a;}
 public abstract String accountType(); public abstract double minimumBalance();
 @Override public String toString(){return accountType()+"{"+accountNumber+", customer="+customerId+", balance="+String.format("%.2f",balance)+", "+status+"}";}
}
