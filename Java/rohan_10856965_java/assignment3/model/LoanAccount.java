package assignment3.model;
public non-sealed class LoanAccount extends BankAccount { public LoanAccount(long n,int c,double b,AccountStatus s){super(n,c,b,s);} public String accountType(){return "LOAN";} public double minimumBalance(){return 0;} }
