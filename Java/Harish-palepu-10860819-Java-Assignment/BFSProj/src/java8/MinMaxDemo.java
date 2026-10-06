package java8;

import java.util.Comparator;
import java.util.List;

/**
 * Class Name : MinMaxDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 1:02 AM
 */
public class MinMaxDemo {

    public static void findMinMaxBalance(List<BankAccount> accounts) {

        // Task 10: Maximum Balance
        System.out.println("\n===== HIGHEST BALANCE ACCOUNT =====");

        accounts.stream()
                .max(Comparator.comparingDouble(BankAccount::getBalance))
                .ifPresent(System.out::println);

        // Task 11: Minimum Balance
        System.out.println("\n===== LOWEST BALANCE ACCOUNT =====");

        accounts.stream()
                .min(Comparator.comparingDouble(BankAccount::getBalance))
                .ifPresent(System.out::println);


    }
}