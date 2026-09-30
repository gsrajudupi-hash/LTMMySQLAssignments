package assignment3.model;
public final class CurrentAccount extends BankAccount { public CurrentAccount(long n,int c,double b,AccountStatus s){super(n,c,b,s);} public String accountType(){return "CURRENT";} public double minimumBalance(){return 5000;} }
