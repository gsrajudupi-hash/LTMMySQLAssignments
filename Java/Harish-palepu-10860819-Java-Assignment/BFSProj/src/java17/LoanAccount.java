package java17;

/**
 * Class Name : LoanAccount
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:28 PM
 */
public final class LoanAccount extends BankAccount{

    @Override
    public void displayAccountType() {
        System.out.println("Loan Account");
    }

    public void displayLoanInterestRate() {
        System.out.println("Loan Interest Rate: 8%");
    }

}
