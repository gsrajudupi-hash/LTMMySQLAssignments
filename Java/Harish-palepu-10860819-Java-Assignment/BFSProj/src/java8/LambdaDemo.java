package java8;

/**
 * Class Name : LambdaDemo
 * Created By : 10860819
 * Created Date : 9/29/2026
 * Created Time : 11:21 PM
 */
public class LambdaDemo {
    public static void main(String[] args) {

        // 1. Deposit
        double balance = 5000;
        BankingOperation deposit = (double amount, double CurrentBalance) -> CurrentBalance + amount;

        // 2. Withdrawal
        BankingOperation withdraw =
                (amount, currentBalance) -> {
                    if (amount <= currentBalance) {
                        return currentBalance - amount;
                    } else {
                        System.out.println("Insufficient Balance");
                        return currentBalance;
                    }
                };

        // 3. Interest
        BankingOperation interest =
                (rate, currentBalance) ->
                        currentBalance +
                                (currentBalance * rate / 100);


        System.out.println("Initial Balance: " + balance);

        balance = deposit.execute(5000, balance);
        System.out.println("After Deposit: " + balance);

        balance = withdraw.execute(2000, balance);
        System.out.println("After Withdrawal: " + balance);

        balance = interest.execute(5, balance);
        System.out.println("After Interest: " + balance);
    }
}
