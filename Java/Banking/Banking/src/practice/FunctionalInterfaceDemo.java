package practice;

import data.BankingData;
import functional.BankingOperation;
import model.Customer;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceDemo {

    public static void main(String[] args) {

        List<Customer> customers = BankingData.createCustomers();

        System.out.println("========================================");
        System.out.println("       PART B - JAVA 8 FEATURES");
        System.out.println("========================================");

        functionalInterfaceDemo();

        predicateDemo(customers);

        consumerDemo(customers);

        functionDemo(customers);

        supplierDemo();
    }

    // Task 2 - Custom Functional Interface
    private static void functionalInterfaceDemo() {

        System.out.println("\n--- Task 2: Functional Interface ---");

        BankingOperation deposit =
                (amount, balance) -> balance + amount;

        BankingOperation withdrawal =
                (amount, balance) -> balance - amount;

        BankingOperation interest =
                (amount, balance) ->
                        balance + (balance * amount / 100);

        double balance = 50000;

        System.out.println("Initial Balance: " + balance);

        balance = deposit.execute(10000, balance);

        System.out.println(
                "After Deposit: " + balance
        );

        balance = withdrawal.execute(5000, balance);

        System.out.println(
                "After Withdrawal: " + balance
        );

        balance = interest.execute(5, balance);

        System.out.println(
                "After 5% Interest: " + balance
        );
    }

    // Task 3 - Predicate
    private static void predicateDemo(
            List<Customer> customers) {

        System.out.println("\n--- Task 3: Predicate ---");

        Predicate<Customer> premiumCustomer =
                customer ->
                        customer.getCustomerType()
                                .equals("PREMIUM");

        Predicate<Customer> bangaloreCustomer =
                customer ->
                        customer.getCity()
                                .equals("Bangalore");

        Predicate<Customer> mangaloreCustomer =
                customer ->
                        customer.getCity()
                                .equals("Mangalore");

        Predicate<Customer> idGreaterThan105 =
                customer ->
                        customer.getCustomerId() > 105;

        System.out.println("\nPremium Customers:");

        customers.stream()
                .filter(premiumCustomer)
                .forEach(System.out::println);

        System.out.println("\nBangalore Customers:");

        customers.stream()
                .filter(bangaloreCustomer)
                .forEach(System.out::println);

        System.out.println("\nMangalore Customers:");

        customers.stream()
                .filter(mangaloreCustomer)
                .forEach(System.out::println);

        System.out.println("\nCustomer ID > 105:");

        customers.stream()
                .filter(idGreaterThan105)
                .forEach(System.out::println);

        System.out.println("\nPremium Bangalore Customers:");

        customers.stream()
                .filter(
                        premiumCustomer.and(bangaloreCustomer)
                )
                .forEach(System.out::println);
    }

    // Task 4 - Consumer
    private static void consumerDemo(
            List<Customer> customers) {

        System.out.println("\n--- Task 4: Consumer ---");

        Consumer<Customer> displayCustomer =
                customer ->
                        System.out.println(customer);

        customers.forEach(displayCustomer);
    }

    // Task 5 - Function
    private static void functionDemo(
            List<Customer> customers) {

        System.out.println("\n--- Task 5: Function ---");

        Function<Customer, String> getCustomerName =
                customer -> customer.getName();

        customers.forEach(customer -> {

            String name =
                    getCustomerName.apply(customer);

            System.out.println(name);
        });
    }

    // Task 6 - Supplier
    private static void supplierDemo() {

        System.out.println("\n--- Task 6: Supplier ---");

        Supplier<Long> accountNumberGenerator =
                () -> System.currentTimeMillis();

        System.out.println(
                "Generated Account Number: "
                        + accountNumberGenerator.get()
        );
    }
}
