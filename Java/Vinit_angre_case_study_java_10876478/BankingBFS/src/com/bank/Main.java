package com.bank;

import com.bank.model.*;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;
import java.util.Scanner;

public class Main {
	private static final Scanner IN = new Scanner(System.in);
	private static final BankingService SERVICE = new BankingService();

	public static void main(String[] args) {
		SERVICE.loadSampleData();
		BankingReport.demonstrateJava8(SERVICE);
		boolean run = true;
		while (run) {
			menu();
			try {
				run = handle(Integer.parseInt(IN.nextLine().trim()));
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		System.out.println("Thank you for using Banking Application.");
	}

	private static void menu() {
		System.out.println("""
				\n1. Add Customer                 2. Display All Customers
				3. Search Customer              4. Add Bank Account
				5. Display All Accounts         6. Deposit Money
				7. Withdraw Money               8. Transfer Money
				9. Check Account Balance       10. Display Transaction History
				11. Banking Analytics          12. Customer Report
				13. Account Type Report        14. Transaction Report
				15. Generate Account Statement 16. Exit
				Enter choice:
				""");
	}

	private static boolean handle(int c) {
		switch (c) {
		case 1 -> addCustomer();
		case 2 -> SERVICE.getCustomers().forEach(System.out::println);
		case 3 -> System.out.println(SERVICE.findCustomer(readInt("Customer ID: ")));
		case 4 -> addAccount();
		case 5 -> SERVICE.getAccounts().forEach(a -> {
			System.out.println(a);
			BankingReport.describe(a);
		});
		case 6 -> System.out.println("Balance: " + SERVICE.deposit(readLong("Account: "), readDouble("Amount: ")));
		case 7 -> System.out.println("Balance: " + SERVICE.withdraw(readLong("Account: "), readDouble("Amount: ")));
		case 8 -> SERVICE.transfer(readLong("Source: "), readLong("Target: "), readDouble("Amount: "));
		case 9 -> System.out.println("Balance: " + SERVICE.checkBalance(readLong("Account: ")));
		case 10 -> SERVICE.history(readLong("Account: ")).forEach(System.out::println);
		case 11 -> BankingReport.dashboard(SERVICE);
		case 12 -> BankingReport.customerReport(SERVICE);
		case 13 -> BankingReport.accountTypeReport(SERVICE);
		case 14 -> BankingReport.transactionReport(SERVICE);
		case 15 -> BankingReport.statement(SERVICE, readLong("Account: "));
		case 16 -> {
			return false;
		}
		default -> System.out.println("Invalid choice");
		}
		return true;
	}

	private static void addCustomer() {
		int id = readInt("ID: ");
		System.out.print("Name: ");
		String n = IN.nextLine();
		System.out.print("Email: ");
		String e = IN.nextLine();
		System.out.print("City: ");
		String city = IN.nextLine();
		System.out.print("Phone: ");
		String p = IN.nextLine();
		System.out.print("Type PREMIUM/REGULAR: ");
		String t = IN.nextLine();
		SERVICE.addCustomer(new Customer(id, n, e, city, p, t));
	}

	private static void addAccount() {
		long no = readLong("Account number: ");
		int cid = readInt("Customer ID: ");
		System.out.print("Type SAVINGS/CURRENT/LOAN: ");
		String type = IN.nextLine().trim().toUpperCase();
		double b = readDouble("Opening balance: ");
		BankAccount a = switch (type) {
		case "SAVINGS" -> new SavingsAccount(no, cid, b, "ACTIVE");
		case "CURRENT" -> new CurrentAccount(no, cid, b, "ACTIVE");
		case "LOAN" -> new LoanAccount(no, cid, b, "ACTIVE");
		default -> throw new IllegalArgumentException("Invalid account type");
		};
		SERVICE.addAccount(a);
	}

	private static int readInt(String p) {
		System.out.print(p);
		return Integer.parseInt(IN.nextLine());
	}

	private static long readLong(String p) {
		System.out.print(p);
		return Long.parseLong(IN.nextLine());
	}

	private static double readDouble(String p) {
		System.out.print(p);
		return Double.parseDouble(IN.nextLine());
	}
}
