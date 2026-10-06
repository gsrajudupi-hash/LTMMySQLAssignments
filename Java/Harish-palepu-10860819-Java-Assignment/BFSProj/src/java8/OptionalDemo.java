package java8;

import java.util.List;
import java.util.Optional;

/**
 * Class Name : OptionalDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 5:40 PM
 */
public class OptionalDemo {
    public static void testOptional(List<Customer> customers) {

        // Find Customer with ID 101
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.getCustomerId() == 101)
                .findFirst();

        // 1. ifPresent()
        System.out.println("\n===== ifPresent() =====");
        customer.ifPresent(c -> System.out.println("Customer Found: " +c));

        // 2. orElse()
        System.out.println("\n===== orElse() =====");
        Customer customerOrElse = customer.orElse(
                new Customer(
                        0,
                        "Default Customer",
                        "default@gmail.com",
                        "Unknown",
                        "0000000000",
                        "REGULAR"
                )
        );
        System.out.println(customerOrElse);

        // 3. orElseGet()
        System.out.println("\n===== orElseGet() =====");
        Customer customerOrElseGet = customer.orElseGet(
                () -> new Customer(
                        0,
                        "Generated Customer",
                        "generated@gmail.com",
                        "Unknown",
                        "0000000000",
                        "REGULAR"
                )
        );
        System.out.println(customerOrElseGet);


        // 4. orElseThrow()
        System.out.println("\n===== orElseThrow() =====");
        Customer customerOrElseThrow = customer.orElseThrow(
                () -> new RuntimeException("Customer not found")
        );
        System.out.println(customerOrElseThrow);

    }
}
