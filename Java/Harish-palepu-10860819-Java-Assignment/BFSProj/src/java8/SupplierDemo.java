package java8;

import java.util.function.Supplier;

/**
 * Class Name : SupplierDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 12:35 AM
 */
public class SupplierDemo {

    public static void testSupplier() {
        Supplier<Long> accountNumberGenerator = () -> System.currentTimeMillis();
        System.out.println("===== SUPPLIER =====");
        System.out.println("Generated Account Number: " + accountNumberGenerator.get()
        );
    }
}
