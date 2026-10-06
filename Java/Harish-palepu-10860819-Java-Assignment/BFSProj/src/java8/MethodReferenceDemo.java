package java8;

import java.util.List;
import java.util.function.Supplier;

/**
 * Class Name : MethodReferenceDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 9:39 PM
 */
public class MethodReferenceDemo {

    public static void demonstrate(List<Customer> customers) {

        // =====================================================
// 1. STATIC METHOD REFERENCE
// =====================================================

// Lambda:
// customers.forEach(c -> printCustomer(c));

// Method Reference:
        System.out.println("===== STATIC METHOD REFERENCE =====");

        customers.forEach(MethodReferenceDemo::printCustomer);


// =====================================================
// 2. INSTANCE METHOD REFERENCE
// =====================================================

        System.out.println("\n===== INSTANCE METHOD REFERENCE =====");

        MethodReferenceDemo demo = new MethodReferenceDemo();

// Lambda:
// customers.forEach(c -> demo.displayCustomer(c));

// Method Reference:
        customers.forEach(demo::displayCustomer);


// =====================================================
// 3. CONSTRUCTOR REFERENCE
// =====================================================

        System.out.println("\n===== CONSTRUCTOR REFERENCE =====");

// Lambda:
// Supplier<StringBuilder> supplier =
// () -> new StringBuilder();

    // Constructor Reference:
        Supplier<StringBuilder> supplier = StringBuilder::new;

        StringBuilder builder = supplier.get();
        builder.append("Banking Application");
        System.out.println(builder);
    }


    // Static Method
    public static void printCustomer(Customer customer) {
        System.out.println(customer);
    }


    // Instance Method
    public void displayCustomer(Customer customer) {
        System.out.println(customer);
    }
}