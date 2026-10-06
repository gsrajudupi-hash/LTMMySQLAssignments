package java8;

import java.util.List;

/**
 * Class Name : FilterDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 12:41 AM
 */
public class FilterDemo {

    public static void filterCustomers(List<Customer> customers) {

// 1. Premium Customers
        System.out.println("\n===== PREMIUM CUSTOMERS =====");

        customers.stream()
                .filter(customer ->
                        customer.getCustomerType()
                                .equalsIgnoreCase("PREMIUM"))
                .forEach(System.out::println);


// 2. Bangalore Customers
        System.out.println("\n===== BANGALORE CUSTOMERS =====");

        customers.stream()
                .filter(customer ->
                        customer.getCity()
                                .equalsIgnoreCase("Bangalore"))
                .forEach(System.out::println);


// 3. Customer names starting with A
        System.out.println("\n===== NAMES STARTING WITH A =====");

        customers.stream()
                .filter(customer ->
                        customer.getName()
                                .toUpperCase()
                                .startsWith("A"))
                .forEach(System.out::println);


// 4. Customer ID greater than 105
        System.out.println("\n===== CUSTOMER ID > 105 =====");

        customers.stream()
                .filter(customer ->
                        customer.getCustomerId() > 105)
                .forEach(System.out::println);
    }
}