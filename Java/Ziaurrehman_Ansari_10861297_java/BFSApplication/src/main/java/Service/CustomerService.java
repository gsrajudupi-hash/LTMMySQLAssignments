package Service;

import Model.BankAccount;
import Model.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.random.RandomGenerator;

/*
Author : 
Date: 
Project : 
*/
public class CustomerService {

    Map<Integer, Customer> customerMap;

    public CustomerService(){
        customerMap = new HashMap<>();

        customerMap.put(1, new Customer(1, "Rahul Sharma", "rahul.sharma@gmail.com",
                "Mumbai", "9876543210", "PREMIUM"));

        customerMap.put(2, new Customer(2, "Priya Patel", "priya.patel@gmail.com",
                "Pune", "9876543211", "REGULAR"));

        customerMap.put(3, new Customer(3, "Amit Verma", "amit.verma@gmail.com",
                "Delhi", "9876543212", "PREMIUM"));

        customerMap.put(4, new Customer(4, "Sneha Reddy", "sneha.reddy@gmail.com",
                "Hyderabad", "9876543213", "REGULAR"));

        customerMap.put(5, new Customer(5, "Vikram Singh", "vikram.singh@gmail.com",
                "Bangalore", "9876543214", "PREMIUM"));

        customerMap.put(6, new Customer(6, "Neha Gupta", "neha.gupta@gmail.com",
                "Chennai", "9876543215", "REGULAR"));

        customerMap.put(7, new Customer(7, "Arjun Nair", "arjun.nair@gmail.com",
                "Kochi", "9876543216", "PREMIUM"));

        customerMap.put(8, new Customer(8, "Pooja Joshi", "pooja.joshi@gmail.com",
                "Nagpur", "9876543217", "REGULAR"));

        customerMap.put(9, new Customer(9, "Karan Mehta", "karan.mehta@gmail.com",
                "Ahmedabad", "9876543218", "PREMIUM"));

        customerMap.put(10, new Customer(10, "Anjali Kulkarni", "anjali.kulkarni@gmail.com",
                "Nashik", "9876543219", "REGULAR"));

    }

    public void addCustomer(){
        Customer customer;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter customer name:");
        String name = sc.nextLine();

        System.out.println("Enter email:");
        String email = sc.nextLine();

        System.out.println("Enter city:");
        String city = sc.nextLine();

        System.out.println("Enter phone:");
        String phone = sc.nextLine();

        System.out.println("Enter customer type:");
        String customerType = sc.nextLine();

        int customerId = RandomGenerator.getDefault().nextInt(1000,10000);
        customer = new Customer(
                customerId,
                name,
                email,
                city,
                phone,
                customerType
        );
        customerMap.put(customerId, customer);
        System.out.println(customer);
        System.out.println(customer);
    }

    public void displayAllCustomers(){
        this.customerMap.values().forEach(System.out::println);
    }

    public void findCustomer(String email){
        Optional<Customer> customer=
        this.customerMap.values().stream().filter(c-> c.email().equals(email)).findFirst();

        if(customer.isPresent()) System.out.println(customer);
        if(customer.isEmpty()) throw new RuntimeException("CustomerNotfound");
    }

    public Customer getCustomer(int customerId){

        if(!customerMap.containsKey(customerId)) throw new RuntimeException("CustomerNotfound");
        return customerMap.get(customerId);
    }

}
