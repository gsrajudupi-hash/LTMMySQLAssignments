package java17;

/**
 * Class Name : InstanceOfDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:44 PM
 */
public class InstanceOfDemo {
    public static void main(String[] args) {

        BankAccount savingsAccount = new SavingAccount();
        BankAccount currentAccount = new CurrentAccount();
        BankAccount loanAccount = new LoanAccount();

        processAccount(savingsAccount);
        processAccount(currentAccount);
        processAccount(loanAccount);
    }

    public static void processAccount(BankAccount account) {
        if (account instanceof SavingAccount savings) {
            System.out.println("===== SAVINGS ACCOUNT =====");
            savings.displayAccountType();
            savings.displayInterestRate();
        }
        else if (account instanceof CurrentAccount current) {
            System.out.println("\n===== CURRENT ACCOUNT =====");
            current.displayAccountType();
            current.displayOverdraftLimit();
        }
        else if (account instanceof LoanAccount loan) {
            System.out.println("\n===== LOAN ACCOUNT =====");
            loan.displayAccountType();
            loan.displayLoanInterestRate();
        }
    }
}