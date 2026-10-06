package java8;

import java.util.List;

/**
 * Class Name : StreamCalculationDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 1:12 AM
 */
public class StreamCalculationDemo {

    public static void calculate(List<BankAccount> accounts, List<Customer> customers) {

        // Total Balance using map() and reduce()
        double totalBalance = accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(0.0, Double::sum);

        System.out.println("\n===== TOTAL BALANCE =====");
        System.out.println("Total Balance: " + totalBalance);

        // Average Balance
        double averageBalance = accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .average().orElse(0.0);

        System.out.println("\n===== AVERAGE BALANCE =====");
        System.out.println("Average Balance: " + averageBalance);

        // Count Premium Customers

        long premiumCustomerCount = customers.stream()
                .filter(customer -> customer.getCustomerType().equalsIgnoreCase("PREMIUM"))
                .count();

        System.out.println("\n===== PREMIUM CUSTOMER COUNT =====");
        System.out.println(
                "Premium Customers: " + premiumCustomerCount
        );


}
}
