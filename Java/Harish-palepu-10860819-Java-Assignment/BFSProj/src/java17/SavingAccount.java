package java17;

/**
 * Class Name : SavingAccount
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:27 PM
 */
public final class SavingAccount extends BankAccount {

    @Override
    public void displayAccountType() {
        System.out.println("Savings Account");
    }

    public void displayInterestRate() {
        System.out.println("Savings Interest Rate: 4%");
    }
}
