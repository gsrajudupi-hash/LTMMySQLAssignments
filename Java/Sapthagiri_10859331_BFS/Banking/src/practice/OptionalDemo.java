package practice;

import data.BankingData;
import model.Customer;

import java.util.List;
import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {

        List<Customer> customers =
                BankingData.createCustomers();

        System.out.println("========================================");
        System.out.println("          PART D - OPTIONAL");
        System.out.println("========================================");

        optionalDemo(customers);
    }

    private static void optionalDemo(
            List<Customer> customers) {

        System.out.println(
                "\n--- Task 20: Optional ---"
        );

        Optional<Customer> customer =
                customers.stream()
                        .filter(c ->
                                c.getCustomerId() == 101)
                        .findFirst();

        // ifPresent()
        System.out.println("\nifPresent():");

        customer.ifPresent(
                c ->
                        System.out.println(
                                "Found Customer: " + c
                        )
        );

        // orElse()
        Customer customerOrElse =
                customer.orElse(
                        new Customer(
                                0,
                                "Unknown",
                                "unknown@gmail.com",
                                "Unknown",
                                "0000000000",
                                "REGULAR"
                        )
                );

        System.out.println(
                "\norElse(): "
                        + customerOrElse
        );

        // orElseGet()
        Customer customerOrElseGet =
                customer.orElseGet(
                        () ->
                                new Customer(
                                        0,
                                        "Unknown",
                                        "unknown@gmail.com",
                                        "Unknown",
                                        "0000000000",
                                        "REGULAR"
                                )
                );

        System.out.println(
                "\norElseGet(): "
                        + customerOrElseGet
        );

        // orElseThrow()
        Customer customerOrElseThrow =
                customer.orElseThrow(
                        () ->
                                new RuntimeException(
                                        "Customer not found"
                                )
                );

        System.out.println(
                "\norElseThrow(): "
                        + customerOrElseThrow
        );
    }
}