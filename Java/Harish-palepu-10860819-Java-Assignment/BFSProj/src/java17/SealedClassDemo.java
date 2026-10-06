package java17;

/**
 * Class Name : SealedClassDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:36 PM
 */
public class SealedClassDemo {
    public static void main(String[] args) {

        BankAccount savings = new SavingAccount();
        BankAccount current = new CurrentAccount();
        BankAccount loan = new LoanAccount();

        savings.displayAccountType();
        current.displayAccountType();
        loan.displayAccountType();

    }
}
