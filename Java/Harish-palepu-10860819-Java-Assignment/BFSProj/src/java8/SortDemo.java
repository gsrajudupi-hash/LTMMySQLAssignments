package java8;

import java.util.Comparator;
import java.util.List;

/**
 * Class Name : SortDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 12:49 AM
 */
public class SortDemo {
    public static void sortCustomers(List<Customer> customers) {

// 1. Sort by Name - Ascending
        System.out.println("\n===== NAME ASCENDING =====");

        customers.stream()
                .sorted(Comparator.comparing(Customer::getName))
                .forEach(System.out::println);

        // 2. Sort by Name - Descending
        System.out.println("\n===== NAME DESCENDING =====");
        customers.stream()
                .sorted(Comparator.comparing(Customer::getName).reversed())
                .forEach(System.out::println);

        // 3. Sort by Customer ID
        System.out.println("\n===== SORT BY CUSTOMER ID =====");
        customers.stream()
                .sorted(Comparator.comparingInt(Customer::getCustomerId))
                .forEach(System.out::println);

        // 4. Sort by Customer Type, then Name
        System.out.println("\n===== CUSTOMER TYPE THEN NAME =====");
        customers.stream()
                .sorted(Comparator.comparing(Customer::getCustomerType).
                        thenComparing(Customer::getName))
                        .forEach(System.out::println);

    }
}