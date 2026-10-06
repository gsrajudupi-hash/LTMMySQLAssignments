package java8;

import java.util.List;
import java.util.function.Predicate;

/**
 * Class Name : PredicateDemo
 * Created By : 10860819
 * Created Date : 9/29/2026
 * Created Time : 11:37 PM
 */
public class PredicateDemo {

    public static void testPredicates(List<Customer> customers) {
        Predicate<Customer> premiumCustomers = customer -> customer.getCustomerType().equalsIgnoreCase("PREMIUM");

        Predicate<Customer> bangaloreCustomers = customer -> customer.getCity().equalsIgnoreCase("Bangalore");

        Predicate<Customer> mangaloreCustomers = customer -> customer.getCity().equalsIgnoreCase("Mangalore");

        Predicate<Customer> cIdGreaterThan105 = customer -> customer.getCustomerId() > 105;

        System.out.println("===== PREMIUM CUSTOMERS =====");

        customers.stream()
                .filter(premiumCustomers)
                .forEach(System.out::println);
        System.out.println("---------------------------------------------------");

        System.out.println("===== BANGALORE CUSTOMERS =====");

        customers.stream()
                .filter(bangaloreCustomers)
                .forEach(System.out::println);
        System.out.println("---------------------------------------------------");

        System.out.println("===== MANGALORE CUSTOMERS =====");

        customers.stream()
                .filter(mangaloreCustomers)
                .forEach(System.out::println);
        System.out.println("---------------------------------------------------");

        System.out.println("===== CUSTOMER ID > 105 =====");

        customers.stream()
                .filter(cIdGreaterThan105)
                .forEach(System.out::println);


    }
}
