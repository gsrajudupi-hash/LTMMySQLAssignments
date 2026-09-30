package java17;


public class Java17FeaturesDemo {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("       PART G - JAVA 17 RECORDS");
        System.out.println("========================================");

        recordDemo();

        System.out.println("\n========================================");
        System.out.println("       PART H - SEALED CLASSES");
        System.out.println("========================================");

        sealedClassDemo();

        System.out.println("\n========================================");
        System.out.println("       PART I - PATTERN MATCHING");
        System.out.println("========================================");

        patternMatchingDemo();

        System.out.println("\n========================================");
        System.out.println("       PART J - SWITCH EXPRESSIONS");
        System.out.println("========================================");

        switchExpressionDemo();

        System.out.println("\n========================================");
        System.out.println("       PART K - TEXT BLOCKS");
        System.out.println("========================================");

        textBlockDemo();
        jsonTextBlockDemo();
    }


    // =========================================================
    // PART G - RECORDS
    // =========================================================

    private static void recordDemo() {

        System.out.println("\n--- Task 23: Customer Record ---");

        CustomerRecord customer =
                new CustomerRecord(
                        101,
                        "Rahul",
                        "Bangalore",
                        "PREMIUM"
                );

        System.out.println(
                "Customer Record: " + customer
        );

        // Accessors
        System.out.println(
                "Customer ID: "
                        + customer.customerId()
        );

        System.out.println(
                "Name: "
                        + customer.name()
        );

        System.out.println(
                "City: "
                        + customer.city()
        );

        System.out.println(
                "Customer Type: "
                        + customer.customerType()
        );

        // equals()
        CustomerRecord anotherCustomer =
                new CustomerRecord(
                        101,
                        "Rahul",
                        "Bangalore",
                        "PREMIUM"
                );

        System.out.println(
                "Equals: "
                        + customer.equals(anotherCustomer)
        );

        // hashCode()
        System.out.println(
                "HashCode: "
                        + customer.hashCode()
        );

        // Immutability
        System.out.println(
                "Records are immutable."
        );


        System.out.println(
                "\n--- Task 24: Transaction Record ---"
        );

        TransactionRecord transaction =
                new TransactionRecord(
                        1,
                        100001L,
                        "DEPOSIT",
                        50000
                );

        System.out.println(
                "Transaction: " + transaction
        );

        // Demonstrate validation
        try {

            TransactionRecord invalidTransaction =
                    new TransactionRecord(
                            2,
                            100001L,
                            "WITHDRAW",
                            -500
                    );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Validation Error: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // PART H - SEALED CLASSES
    // =========================================================

    private static void sealedClassDemo() {

        Account savings =
                new SavingsAccount(
                        100001L,
                        85000
                );

        Account current =
                new CurrentAccount(
                        100002L,
                        150000
                );

        Account loan =
                new LoanAccount(
                        100003L,
                        500000
                );

        System.out.println(
                "Account Type: "
                        + savings.getAccountType()
        );

        System.out.println(
                "Account Number: "
                        + savings.getAccountNumber()
        );

        System.out.println(
                "Balance: "
                        + savings.getBalance()
        );

        System.out.println();

        System.out.println(
                "Account Type: "
                        + current.getAccountType()
        );

        System.out.println(
                "Account Type: "
                        + loan.getAccountType()
        );

        System.out.println(
                "\nAccount is a sealed class."
        );

        System.out.println(
                "Only permitted classes can extend Account."
        );
    }


    // =========================================================
    // PART I - PATTERN MATCHING
    // =========================================================

    private static void patternMatchingDemo() {

        Account savings =
                new SavingsAccount(
                        100001L,
                        85000
                );

        Account current =
                new CurrentAccount(
                        100002L,
                        150000
                );

        Account loan =
                new LoanAccount(
                        100003L,
                        500000
                );

        processAccount(savings);

        processAccount(current);

        processAccount(loan);
    }


    private static void processAccount(Account account) {

        if (account instanceof SavingsAccount savings) {

            System.out.println(
                    "Savings Account"
            );

            System.out.println(
                    "Account Number: "
                            + savings.getAccountNumber()
            );

            System.out.println(
                    "Balance: "
                            + savings.getBalance()
            );

        } else if (account instanceof CurrentAccount current) {

            System.out.println(
                    "Current Account"
            );

            System.out.println(
                    "Account Number: "
                            + current.getAccountNumber()
            );

            System.out.println(
                    "Balance: "
                            + current.getBalance()
            );

        } else if (account instanceof LoanAccount loan) {

            System.out.println(
                    "Loan Account"
            );

            System.out.println(
                    "Account Number: "
                            + loan.getAccountNumber()
            );

            System.out.println(
                    "Loan Amount: "
                            + loan.getBalance()
            );
        }
    }


    // =========================================================
    // PART J - SWITCH EXPRESSIONS
    // =========================================================

    private static void switchExpressionDemo() {

        String[] transactionTypes = {
                "DEPOSIT",
                "INTEREST",
                "WITHDRAW",
                "LOAN_PAYMENT",
                "TRANSFER",
                "UNKNOWN"
        };

        for (String transactionType : transactionTypes) {

            String category =
                    switch (transactionType) {

                        case "DEPOSIT", "INTEREST" ->
                                "CREDIT";

                        case "WITHDRAW", "LOAN_PAYMENT" ->
                                "DEBIT";

                        case "TRANSFER" ->
                                "TRANSFER";

                        default ->
                                "UNKNOWN";
                    };

            System.out.println(
                    transactionType
                            + " -> "
                            + category
            );
        }
    }


    // =========================================================
    // PART K - TEXT BLOCKS
    // =========================================================

    private static void textBlockDemo() {

        String report = """
                ================================
                BANK ACCOUNT STATEMENT
                ================================
                Account Number : 100001
                Customer       : Rahul
                Account Type   : SAVINGS
                Balance        : ₹85,000
                Status         : ACTIVE
                ================================
                """;

        System.out.println(report);


        String secondReport = """
                ================================
                BANK ACCOUNT STATEMENT
                ================================
                Account Number : 100002
                Customer       : Rahul
                Account Type   : CURRENT
                Balance        : ₹1,50,000
                Status         : ACTIVE
                ================================
                """;

        System.out.println(secondReport);
    }


    // =========================================================
    // TASK 29 - JSON USING TEXT BLOCK
    // =========================================================

    private static void jsonTextBlockDemo() {

        String customerJson = """
                {
                  "customerId": 101,
                  "name": "Rahul",
                  "city": "Bangalore",
                  "customerType": "PREMIUM"
                }
                """;

        String accountJson = """
                {
                  "accountNumber": 100001,
                  "customerId": 101,
                  "accountType": "SAVINGS",
                  "balance": 85000,
                  "status": "ACTIVE"
                }
                """;

        String transactionJson = """
                {
                  "transactionId": 1,
                  "accountNumber": 100001,
                  "transactionType": "DEPOSIT",
                  "amount": 50000
                }
                """;

        System.out.println(
                "\nCustomer JSON:"
        );

        System.out.println(customerJson);

        System.out.println(
                "Account JSON:"
        );

        System.out.println(accountJson);

        System.out.println(
                "Transaction JSON:"
        );

        System.out.println(transactionJson);
    }
}
