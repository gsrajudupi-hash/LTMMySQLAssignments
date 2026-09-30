package practice;

import data.BankingData;
import model.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateTimeDemo {

    public static void main(String[] args) {

        List<Transaction> transactions =
                BankingData.createTransactions();

        System.out.println("========================================");
        System.out.println("       PART F - DATE & TIME API");
        System.out.println("========================================");

        dateTimeDemo(transactions);
    }

    private static void dateTimeDemo(
            List<Transaction> transactions) {

        System.out.println(
                "\n--- Task 22: Date & Time API ---"
        );

        // LocalDate
        LocalDate currentDate =
                LocalDate.now();

        System.out.println(
                "Current Date: "
                        + currentDate
        );

        // LocalTime
        LocalTime currentTime =
                LocalTime.now();

        System.out.println(
                "Current Time: "
                        + currentTime
        );

        // LocalDateTime
        LocalDateTime currentDateTime =
                LocalDateTime.now();

        System.out.println(
                "Current DateTime: "
                        + currentDateTime
        );

        // Creating specific date
        LocalDate bankOpeningDate =
                LocalDate.of(
                        2020,
                        1,
                        1
                );

        System.out.println(
                "Bank Opening Date: "
                        + bankOpeningDate
        );

        // Date operations
        System.out.println(
                "Tomorrow: "
                        + currentDate.plusDays(1)
        );

        System.out.println(
                "Previous Week: "
                        + currentDate.minusDays(7)
        );

        System.out.println(
                "Next Month: "
                        + currentDate.plusMonths(1)
        );

        // Formatter
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        System.out.println(
                "\nFormatted Current DateTime:"
        );

        System.out.println(
                currentDateTime.format(formatter)
        );

        // Format transaction dates
        System.out.println(
                "\nTransaction Dates:"
        );

        transactions.forEach(transaction -> {

            String formattedDate =
                    transaction
                            .getTransactionDate()
                            .format(formatter);

            System.out.println(
                    "Transaction ID: "
                            + transaction.getTransactionId()
                            + " | Type: "
                            + transaction.getTransactionType()
                            + " | Amount: "
                            + transaction.getAmount()
                            + " | Date: "
                            + formattedDate
            );
        });

        // Transactions from last 7 days
        LocalDateTime sevenDaysAgo =
                LocalDateTime.now()
                        .minusDays(7);

        System.out.println(
                "\nTransactions From Last 7 Days:"
        );

        transactions.stream()
                .filter(transaction ->
                        transaction
                                .getTransactionDate()
                                .isAfter(sevenDaysAgo)
                )
                .forEach(System.out::println);
    }
}
