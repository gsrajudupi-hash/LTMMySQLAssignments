//import bankingdashboard.BankingAnalyticsDashboard;
import java8.*;

import java.time.LocalDateTime;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        CREATE CUSTOMERS
        Customer c1 = new Customer(
                101,
                "Rahul",
                "rahul@gmail.com",
                "Bangalore",
                "987654321",
                "PREMIUM"
        );
        Customer c2 = new Customer(
                102,
                "Priya",
                "Priya@gmail.com",
                "Mangalore",
                "987654322",
                "REGULAR"
        );
        Customer c3 = new Customer(
                103,
                "Amit",
                "ami@gmail.com",
                "Mumbai",
                "9876543230",
                "PREMIUM"
        );
        Customer c4 = new Customer(
                104,
                "Sneha",
                "sneha@gmail.com",
                "Pune",
                "9876543240",
                "REGULAR"
        );
        Customer c5 = new Customer(
                105,
                "Arjun",
                "arjun@gmail.com",
                "Chennai",
                "9876543250",
                "PREMIUM"
        );
        Customer c6 = new Customer(
                106,
                "Kavya",
                "kavya@gmail.com",
                "Hyderabad",
                "9876543260",
                "REGULAR"
        );
        Customer c7 = new Customer(
                107,
                "Vikram",
                "vikram@gmail.com",
                "Bangalore",
                "9876543270",
                "PREMIUM"
        );

        List<Customer> customers = new ArrayList<>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        customers.add(c4);
        customers.add(c5);
        customers.add(c6);
        customers.add(c7);

//        Create Bank Accounts
        BankAccount a1 = new BankAccount(
                100001L, 101, "SAVINGS", 50000, "ACTIVE"
        );
        BankAccount a2 = new BankAccount(
                100002L, 101, "CURRENT", 30000, "ACTIVE"
        );
        BankAccount a3 = new BankAccount(
                100003L, 102, "SAVINGS", 40000, "ACTIVE"
        );
        BankAccount a4 = new BankAccount(
                100004L, 102, "CURRENT", 25000, "ACTIVE"
        );
        BankAccount a5 = new BankAccount(
                100005L, 103, "SAVINGS", 60000, "ACTIVE"
        );
        BankAccount a6 = new BankAccount(
                100006L, 103, "CURRENT", 35000, "ACTIVE"
        );
        BankAccount a7 = new BankAccount(
                100007L, 104, "SAVINGS", 45000, "ACTIVE"
        );
        BankAccount a8 = new BankAccount(
                100008L, 104, "CURRENT", 20000, "ACTIVE"
        );
        BankAccount a9 = new BankAccount(
                100009L, 105, "SAVINGS", 70000, "ACTIVE"
        );
        BankAccount a10 = new BankAccount(
                100010L, 105, "CURRENT", 40000, "ACTIVE"
        );
        BankAccount a11 = new BankAccount(
                100011L, 106, "SAVINGS", 55000, "ACTIVE"
        );
        BankAccount a12 = new BankAccount(
                100012L, 106, "CURRENT", 30000, "ACTIVE"
        );
        BankAccount a13 = new BankAccount(
                100013L, 107, "SAVINGS", 80000, "ACTIVE"
        );
        BankAccount a14 = new BankAccount(
                100014L, 107, "CURRENT", 45000, "ACTIVE"
        );
        BankAccount a15 = new BankAccount(
                100015L, 107, "SAVINGS", 25000, "INACTIVE"
        );

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(a1);
        accounts.add(a2);
        accounts.add(a3);
        accounts.add(a4);
        accounts.add(a5);
        accounts.add(a6);
        accounts.add(a7);
        accounts.add(a8);
        accounts.add(a9);
        accounts.add(a10);
        accounts.add(a11);
        accounts.add(a12);
        accounts.add(a13);
        accounts.add(a14);
        accounts.add(a15);

//        Create Transactions
        LocalDateTime transactionTime = LocalDateTime.now();
        Transaction t1 = new Transaction(
                1, 100001L, "DEPOSIT", 5000,
                transactionTime.minusDays(30),
                "Cash deposit"
        );
        Transaction t2 = new Transaction(
                2, 100001L, "WITHDRAWAL", 2000,
                transactionTime.minusDays(29),
                "ATM withdrawal"
        );
        Transaction t3 = new Transaction(
                3, 100002L, "DEPOSIT", 3000,
                transactionTime.minusDays(28),
                "Online deposit"
        );
        Transaction t4 = new Transaction(
                4, 100002L, "WITHDRAWAL", 1000,
                transactionTime.minusDays(27),
                "Cash withdrawal"
        );
        Transaction t5 = new Transaction(
                5, 100003L, "DEPOSIT", 6000,
                transactionTime.minusDays(26),
                "Salary credited"
        );
        Transaction t6 = new Transaction(
                6, 100003L, "WITHDRAWAL", 2500,
                transactionTime.minusDays(25),
                "ATM withdrawal"
        );
        Transaction t7 = new Transaction(
                7, 100004L, "DEPOSIT", 4000,
                transactionTime.minusDays(24),
                "Cash deposit"
        );
        Transaction t8 = new Transaction(
                8, 100004L, "WITHDRAWAL", 1500,
                transactionTime.minusDays(23),
                "Bill payment"
        );
        Transaction t9 = new Transaction(
                9, 100005L, "DEPOSIT", 8000,
                transactionTime.minusDays(22),
                "Online transfer received"
        );
        Transaction t10 = new Transaction(
                10, 100005L, "WITHDRAWAL", 3000,
                transactionTime.minusDays(21),
                "ATM withdrawal"
        );
        Transaction t11 = new Transaction(
                11, 100006L, "DEPOSIT", 4500,
                transactionTime.minusDays(20),
                "Cash deposit"
        );
        Transaction t12 = new Transaction(
                12, 100006L, "WITHDRAWAL", 2000,
                transactionTime.minusDays(19),
                "Vendor payment"
        );
        Transaction t13 = new Transaction(
                13, 100007L, "DEPOSIT", 7000,
                transactionTime.minusDays(18),
                "Salary credited"
        );
        Transaction t14 = new Transaction(
                14, 100007L, "WITHDRAWAL", 2500,
                transactionTime.minusDays(17),
                "Shopping payment"
        );
        Transaction t15 = new Transaction(
                15, 100008L, "DEPOSIT", 3500,
                transactionTime.minusDays(16),
                "Online transfer received"
        );
        Transaction t16 = new Transaction(
                16, 100008L, "WITHDRAWAL", 1200,
                transactionTime.minusDays(15),
                "Utility bill payment"
        );
        Transaction t17 = new Transaction(
                17, 100009L, "DEPOSIT", 9000,
                transactionTime.minusDays(14),
                "Cash deposit"
        );
        Transaction t18 = new Transaction(
                18, 100009L, "WITHDRAWAL", 4000,
                transactionTime.minusDays(13),
                "ATM withdrawal"
        );
        Transaction t19 = new Transaction(
                19, 100010L, "DEPOSIT", 5000,
                transactionTime.minusDays(12),
                "Business income"
        );
        Transaction t20 = new Transaction(
                20, 100010L, "WITHDRAWAL", 1800,
                transactionTime.minusDays(11),
                "Vendor payment"
        );
        Transaction t21 = new Transaction(
                21, 100011L, "DEPOSIT", 6500,
                transactionTime.minusDays(10),
                "Salary credited"
        );
        Transaction t22 = new Transaction(
                22, 100011L, "WITHDRAWAL", 2200,
                transactionTime.minusDays(9),
                "ATM withdrawal"
        );
        Transaction t23 = new Transaction(
                23, 100012L, "DEPOSIT", 4200,
                transactionTime.minusDays(8),
                "Cash deposit"
        );
        Transaction t24 = new Transaction(
                24, 100012L, "WITHDRAWAL", 1700,
                transactionTime.minusDays(7),
                "Office expense"
        );
        Transaction t25 = new Transaction(
                25, 100013L, "DEPOSIT", 10000,
                transactionTime.minusDays(6),
                "Investment return"
        );
        Transaction t26 = new Transaction(
                26, 100013L, "WITHDRAWAL", 5000,
                transactionTime.minusDays(5),
                "Online purchase"
        );
        Transaction t27 = new Transaction(
                27, 100014L, "DEPOSIT", 5500,
                transactionTime.minusDays(4),
                "Business income"
        );
        Transaction t28 = new Transaction(
                28, 100014L, "WITHDRAWAL", 2300,
                transactionTime.minusDays(3),
                "Vendor payment"
        );
        Transaction t29 = new Transaction(
                29, 100015L, "DEPOSIT", 3000,
                transactionTime.minusDays(2),
                "Cash deposit"
        );
        Transaction t30 = new Transaction(
                30, 100015L, "WITHDRAWAL", 1000,
                transactionTime.minusDays(1),
                "ATM withdrawal"
        );

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
        transactions.add(t3);
        transactions.add(t4);
        transactions.add(t5);
        transactions.add(t6);
        transactions.add(t7);
        transactions.add(t8);
        transactions.add(t9);
        transactions.add(t10);
        transactions.add(t11);
        transactions.add(t12);
        transactions.add(t13);
        transactions.add(t14);
        transactions.add(t15);
        transactions.add(t16);
        transactions.add(t17);
        transactions.add(t18);
        transactions.add(t19);
        transactions.add(t20);
        transactions.add(t21);
        transactions.add(t22);
        transactions.add(t23);
        transactions.add(t24);
        transactions.add(t25);
        transactions.add(t26);
        transactions.add(t27);
        transactions.add(t28);
        transactions.add(t29);
        transactions.add(t30);

//      Display Customers
        System.out.println("========== CUSTOMER DETAILS ==========");
        for (Customer customer : customers) {
            customer.displayCustomer();
            System.out.println("--------------------------------------");
        }

//      Display Bank Accounts
        System.out.println("\n========== BANK ACCOUNT DETAILS ==========");

        for (BankAccount account : accounts) {
            account.displayAccount();
            System.out.println("------------------------------------------");
        }

//      Display Transactions
        System.out.println("\n========== TRANSACTION DETAILS ==========");

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
            System.out.println("-----------------------------------------");
        }

//      TEST GETTER, SETTER AND EQUALS
        System.out.println("\n========== GETTER TEST ==========");
        System.out.println("Customer name: " + c1.getName());

        System.out.println("\n========== SETTER TEST ==========");
        c1.setCity("Hyderabad");
        System.out.println(c1);

        Customer duplicateCustomer = new Customer(
                101,
                "Rahul",
                "different@gmail.com",
                "Delhi",
                "9999999999",
                "REGULAR"
        );

        System.out.println("\n========== EQUALS TEST ==========");
        System.out.println("c1 equals duplicateCustomer: " +c1.equals(duplicateCustomer));

        System.out.println("---------------------------------------------------");

//      PREDICATE
        PredicateDemo.testPredicates(customers);

//      CONSUMER
        ConsumerDemo.testConsumer(customers);

//      FUNCTION
        FunctionDemo.testFunction(customers);

//      SUPPLIER
        SupplierDemo.testSupplier();

       FilterDemo.filterCustomers(customers);

//      MAP
        MapDemo.displayCustomerNames(customers);

//      Sort
        SortDemo.sortCustomers(customers);

//      MIN & MAX
        MinMaxDemo.findMinMaxBalance(accounts);

//      Task 12,13 and 14
        StreamCalculationDemo.calculate(accounts, customers);

        AdvancedStreamDemo.analyze(
                customers,
                accounts,
                transactions
        );

//     Optional
        OptionalDemo.testOptional(customers);

//      Method References
        MethodReferenceDemo.demonstrate(customers);

        System.out.println("================================");

////     Banking Analytics Dashboard
//        BankingAnalyticsDashboard.generateBankingDashboard(
//                customers,
//                accounts,
//                transactions
//        );




    }
    }
