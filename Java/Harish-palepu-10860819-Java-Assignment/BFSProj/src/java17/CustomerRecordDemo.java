package java17;

/**
 * Class Name : CustomerRecordDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 10:20 PM
 */
public class CustomerRecordDemo {
    public static void main(String[] args) {
        // Constructor
        CustomerRecord customer1 = new CustomerRecord(
                101,
                "Rahul",
                "Bangalore",
                "PREMIUM"
        );

        CustomerRecord customer2 = new CustomerRecord(
                101,
                "Rahul",
                "Bangalore",
                "PREMIUM"
        );


// Accessors
        System.out.println("===== ACCESSORS =====");
        System.out.println("Customer ID : " + customer1.customerId());
        System.out.println("Name : " + customer1.name());
        System.out.println("City : " + customer1.city());
        System.out.println("Customer Type : " + customer1.customerType());


// toString()
        System.out.println("\n===== toString() =====");

        System.out.println(customer1);


// equals()
        System.out.println("\n===== equals() =====");

        System.out.println("customer1 equals customer2 : " + customer1.equals(customer2));


// hashCode()
        System.out.println("\n===== hashCode() =====");

        System.out.println("customer1 hashCode : " + customer1.hashCode());
        System.out.println("customer2 hashCode : " + customer2.hashCode());


// Immutability
        System.out.println("\n===== IMMUTABILITY =====");

        System.out.println("Name : " + customer1.name());

// These are NOT allowed:
//        customer1.getName();
// customer1.setName("Amit");
// customer1.name = "Amit";
    }
}
