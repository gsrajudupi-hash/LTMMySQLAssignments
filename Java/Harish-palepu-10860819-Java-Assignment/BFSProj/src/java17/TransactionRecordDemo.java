package java17;

/**
 * Class Name : TransactionRecordDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 11:06 PM
 */
public class TransactionRecordDemo {
    public static void main(String[] args) {

        // Valid transaction
        TransactionRecord transaction1 =
                new TransactionRecord(
                        1,
                        100001L,
                        "DEPOSIT",
                        5000
                );

        System.out.println(transaction1);

        //Accessors
        System.out.println("Transaction ID : " + transaction1.transactionId());
        System.out.println("Account Number : " + transaction1.accountNumber());
        System.out.println("Transaction Type : " + transaction1.transactionType());
        System.out.println("Amount : " + transaction1.amount());


        // Invalid Amount
        System.out.println("\n===== INVALID AMOUNT =====");

        try {
            TransactionRecord transaction2 =
                    new TransactionRecord(
                            2,
                            100002L,
                            "WITHDRAWAL",
                            -2000
                    );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Null Transaction Type
        System.out.println("\n===== NULL TRANSACTION TYPE =====");

        try {
            TransactionRecord transaction3 =
                    new TransactionRecord(
                            3,
                            100003L,
                            null,
                            3000
                    );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
