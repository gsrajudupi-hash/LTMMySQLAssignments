package java8;

import java.util.List;

/**
 * Class Name : MapDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 12:45 AM
 */
public class MapDemo {

    public static void displayCustomerNames(List<Customer> customers) {

        System.out.println("\n===== CUSTOMER NAMES USING MAP =====");
        customers.stream()
                //.map(customer -> customer.getName())
                .map(Customer::getName)
                .forEach(System.out::println);


    }
}
