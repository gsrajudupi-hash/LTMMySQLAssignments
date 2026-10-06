package java8;

import java.util.List;
import java.util.function.Function;

/**
 * Class Name : FunctionDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 12:33 AM
 */
public class FunctionDemo {

    public static void testFunction(List<Customer> customers) {
        Function<Customer, String> getCustomerName = customer -> customer.getName();
        System.out.println("===== FUNCTION =====");
        customers.forEach(customer ->
                System.out.println(
                        getCustomerName.apply(customer)
                )
        );
    }
}
