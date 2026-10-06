package java17;

/**
 * Class Name : BankAccount
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:24 PM
 */
public sealed abstract class BankAccount permits
                                  SavingAccount, CurrentAccount, LoanAccount{

    public abstract void displayAccountType();
}
