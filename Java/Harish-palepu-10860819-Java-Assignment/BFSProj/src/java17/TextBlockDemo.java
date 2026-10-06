package java17;

/**
 * Class Name : TextBlockDemo
 * Created By : 10860819
 * Created Date : 10/1/2026
 * Created Time : 12:20 AM
 */
public class TextBlockDemo {
    public static void main(String[] args) {
        generateAccountStatement(
                1000001L,
                "Rahul",
                "SAVINGS",
                85000.00,
                "ACTIVE"
        );
        generateAccountStatement(
                1000002L,
                "Priya",
                "CURRENT",
                65000.00,
                "ACTIVE"
        );
        generateAccountStatement(
                1000003L,
                "Amit",
                "SAVINGS",
                120000.00,
                "ACTIVE"
        );
    }

    private static void generateAccountStatement(
            long accountNumber,
            String customerName,
            String accountType,
            double balance,
            String status) {

        String accountStatement = """
                =========================
                Bank Account Statement
                =========================
                Account Number: %d
                Customer: %s
                Account Type: %s
                Balance: %.2f
                Status: %s
                =========================
                """.formatted(
                accountNumber,
                customerName,
                accountType,
                balance,
                status
        );

        System.out.println(accountStatement);
    }
}
