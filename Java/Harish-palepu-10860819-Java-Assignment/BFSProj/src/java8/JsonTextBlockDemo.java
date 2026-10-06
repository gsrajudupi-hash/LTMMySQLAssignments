package java8;

/**
 * Class Name : JsonTextBlockDemo
 * Created By : 10860819
 * Created Date : 10/1/2026
 * Created Time : 10:00 AM
 */
public class JsonTextBlockDemo {
    public static void main(String[] args) {
        // Customer JSON
        String customerJson = """
                {
                "customerId": 101,
                "name": "Rahul",
                "city": "Bangalore",
                "customerType": "PREMIUM"
                }
                """;

// Account JSON
        String accountJson = """
                {
                "accountNumber": 1000001,
                "customerId": 101,
                "accountType": "SAVINGS",
                "balance": 85000.00,
                "status": "ACTIVE"
                }
                """;

// Transaction JSON
        String transactionJson = """
                {
                "transactionId": 5001,
                "accountNumber": 1000001,
                "transactionType": "DEPOSIT",
                "amount": 10000.00,
                "status": "SUCCESS"
                }
                """;

        System.out.println("Customer JSON:");
        System.out.println(customerJson);

        System.out.println("Account JSON:");
        System.out.println(accountJson);

        System.out.println("Transaction JSON:");
        System.out.println(transactionJson);
    }
}
