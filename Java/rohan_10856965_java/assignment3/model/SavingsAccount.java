package assignment3.model;
public final class SavingsAccount extends BankAccount { public SavingsAccount(long n,int c,double b,AccountStatus s){super(n,c,b,s);} public String accountType(){return "SAVINGS";} public double minimumBalance(){return 1000;} }
