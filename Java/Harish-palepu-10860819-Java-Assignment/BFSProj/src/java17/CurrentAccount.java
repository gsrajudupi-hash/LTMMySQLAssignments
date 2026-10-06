package java17;

/**
 * Class Name : CurrentAccount
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:28 PM
 */
public final class CurrentAccount extends BankAccount{

    @Override
    public void displayAccountType() {
        System.out.println("Current Account");
    }

    public void displayOverdraftLimit() {
        System.out.println("Overdraft Limit: 50000");
    }

}
