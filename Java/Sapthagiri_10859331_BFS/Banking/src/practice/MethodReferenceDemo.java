package practice;

import data.BankingData;
import model.Customer;

import java.util.List;
import java.util.function.Function;

public class MethodReferenceDemo {

    public static void main(String[] args) {

        List<Customer> customers =
                BankingData.createCustomers();

        System.out.println("========================================");
        System.out.println("       PART E - METHOD REFERENCES");
        System.out.println("========================================");

        instanceMethodReference(customers);

        staticMethodReference(customers);

        constructorMethodReference();
    }

    // Instance method reference
    private static void instanceMethodReference(
            List<Customer> customers) {

        System.out.println(
                "\n--- Instance Method Reference ---"
        );

        Function<Customer, String> customerName =
                Customer::getName;

        customers.stream()
                .map(customerName)
                .forEach(System.out::println);
    }

    // Static method reference
    private static void staticMethodReference(
            List<Customer> customers) {

        System.out.println(
                "\n--- Static Method Reference ---"
        );

        customers.stream()
                .filter(
                        MethodReferenceDemo::isPremium
                )
                .forEach(System.out::println);
    }

    private static boolean isPremium(
            Customer customer) {

        return customer.getCustomerType()
                .equals("PREMIUM");
    }

    // Constructor method reference
    private static void constructorMethodReference() {

        System.out.println(
                "\n--- Constructor Method Reference ---"
        );

        Function<String, StringBuilder> builder =
                StringBuilder::new;

        StringBuilder result =
                builder.apply("BFS Banking");

        System.out.println(result);
    }
}
