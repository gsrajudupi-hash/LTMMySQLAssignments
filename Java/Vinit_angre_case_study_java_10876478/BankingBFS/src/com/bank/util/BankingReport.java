package com.bank.util;

import com.bank.model.*;
import com.bank.record.*;
import com.bank.service.BankingService;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public final class BankingReport {
	private BankingReport() {
	}

	private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public static void demonstrateJava8(BankingService s) {
		Predicate<Customer> premium = c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType());
		Predicate<Customer> bangalore = c -> "Bangalore".equalsIgnoreCase(c.getCity());
		Consumer<Customer> display = System.out::println;
		Function<Customer, String> name = Customer::getName;
		Supplier<Long> generator = System::currentTimeMillis;
		System.out.println("Premium/Bangalore customers:");
		s.getCustomers().stream().filter(premium.and(bangalore)).forEach(display);
		System.out.println("Names: " + s.getCustomers().stream().map(name).sorted().collect(Collectors.joining(", ")));
		System.out.println("Generated account number: " + generator.get());
		System.out.println("Distinct cities: " + s.getCustomers().stream().map(Customer::getCity).distinct().sorted()
				.collect(Collectors.joining(", ")));
		System.out.println(
				"Skip 2, limit 3: " + s.getCustomers().stream().skip(2).limit(3).map(Customer::getName).toList());
		System.out.println("FlatMap account numbers: " + s.getCustomers().stream()
				.flatMap(c -> s.getAccounts().stream().filter(a -> a.getCustomerId() == c.getCustomerId()))
				.map(BankAccount::getAccountNumber).limit(10).toList());
	}

	public static void dashboard(BankingService s) {
		DoubleSummaryStatistics stats = s.getAccounts().stream().mapToDouble(BankAccount::getBalance)
				.summaryStatistics();
		long premium = s.getCustomers().stream().filter(c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType())).count();
		double reduced = s.getAccounts().stream().map(BankAccount::getBalance).reduce(0d, Double::sum);
		String out = """
				================= BANKING ANALYTICS DASHBOARD =================
				Total customers       : %d
				Premium customers     : %d
				Total accounts        : %d
				Total balance         : %.2f
				Reduced total         : %.2f
				Average balance       : %.2f
				Highest balance       : %.2f
				Lowest balance        : %.2f
				Total transactions    : %d
				Accounts by type      : %s
				Balance by type       : %s
				Customers by city     : %s
				Premium partition     : %s
				Top three customers   : %s
				===============================================================
				""".formatted(s.getCustomers().size(), premium, s.getAccounts().size(), stats.getSum(), reduced,
				stats.getAverage(), stats.getMax(), stats.getMin(), s.getTransactions().size(), s.accountsByType(),
				s.balanceByType(),
				s.customersByCity().entrySet().stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size())),
				s.partitionPremium().entrySet().stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size())),
				s.topThreeCustomers().stream().map(Customer::getName).toList());
		System.out.println(out);
	}

	public static void customerReport(BankingService s) {
		s.getCustomers().stream()
				.sorted(Comparator.comparing(Customer::getCustomerType).thenComparing(Customer::getName))
				.forEach(c -> System.out
						.println(new CustomerRecord(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType())));
	}

	public static void accountTypeReport(BankingService s) {
		System.out.println("Count: " + s.accountsByType());
		System.out.println("Balance: " + s.balanceByType());
	}

	public static void transactionReport(BankingService s) {
		var summary = s.getTransactions().stream().collect(Collectors.groupingBy(Transaction::getTransactionType,
				Collectors.summarizingDouble(Transaction::getAmount)));
		summary.forEach((k, v) -> System.out.printf("%s count=%d total=%.2f average=%.2f%n", k, v.getCount(),
				v.getSum(), v.getAverage()));
		s.getTransactions().stream().filter(t -> t.getAmount() > 50000)
				.sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()).limit(5)
				.forEach(System.out::println);
	}

	public static void statement(BankingService s, long no) {
		BankAccount a = s.findAccount(no);
		Customer c = s.findCustomer(a.getCustomerId());
		String tx = s.history(no).stream().limit(5)
				.map(t -> t.getTransactionDate().format(F) + " | " + t.getTransactionType() + " | " + t.getAmount())
				.collect(Collectors.joining("\n"));
		String report = """
				================= BANK ACCOUNT STATEMENT =================
				Account Number : %d
				Customer       : %s
				Account Type   : %s
				Balance        : %.2f
				Status         : %s
				Recent Transactions:
				%s
				==========================================================
				""".formatted(a.getAccountNumber(), c.getName(), a.getAccountType(), a.getBalance(), a.getStatus(),
				tx.isBlank() ? "No transactions" : tx);
		System.out.println(report);
	}

	public static String classify(String type) {
		return switch (type.toUpperCase()) {
		case "DEPOSIT", "INTEREST" -> "CREDIT";
		case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
		case "TRANSFER" -> "TRANSFER";
		default -> "UNKNOWN";
		};
	}

	public static void describe(BankAccount a) {
		if (a instanceof SavingsAccount s)
			System.out.println("Savings, minimum=" + s.getMinimumBalance());
		else if (a instanceof CurrentAccount c)
			System.out.println("Current, minimum=" + c.getMinimumBalance());
		else if (a instanceof LoanAccount l)
			System.out.println("Loan, balance=" + l.getBalance());
	}

	public static String customerJson(Customer c) {
		return """
				{"customerId":%d,"name":"%s","city":"%s","customerType":"%s"}
				""".formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
	}
}
