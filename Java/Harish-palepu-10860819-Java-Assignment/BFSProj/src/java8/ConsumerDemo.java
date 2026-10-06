package java8;

import java.util.List;
import java.util.function.Consumer;

/**
 * Class Name : ConsumerDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 12:28 AM
 */
public class ConsumerDemo {

    public static void testConsumer(List<Customer> customers) {
        Consumer<Customer> displayCustomer = customer -> System.out.println(customer);
        System.out.println("===== CONSUMER =====");
        customers.forEach(displayCustomer);
    }
}