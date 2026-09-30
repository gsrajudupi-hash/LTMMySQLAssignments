package com.bank.service;

import com.bank.exception.*;
import com.bank.model.*;

import bank.functional.BankingOperation;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class BankingService {
	private final List<Customer> customers = new ArrayList<>();
	private final List<BankAccount> accounts = new ArrayList<>();
	private final List<Transaction> transactions = new ArrayList<>();
	private int nextTransactionId = 1;

	public List<Customer> getCustomers() {
		return Collections.unmodifiableList(customers);
	}

	public List<BankAccount> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}

	public void addCustomer(Customer c) {
		if (c == null || c.getName() == null || c.getName().isBlank())
			throw new IllegalArgumentException("Valid customer required");
		if (customers.stream().anyMatch(x -> x.getCustomerId() == c.getCustomerId()))
			throw new IllegalArgumentException("Customer ID already exists");
		customers.add(c);
	}

	public Customer findCustomer(int id) {
		return customers.stream().filter(c -> c.getCustomerId() == id).findFirst()
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
	}

	public void addAccount(BankAccount a) {
		findCustomer(a.getCustomerId());
		if (accounts.stream().anyMatch(x -> x.getAccountNumber() == a.getAccountNumber()))
			throw new InvalidAccountException("Account already exists");
		accounts.add(a);
	}

	public BankAccount findAccount(long no) {
		return accounts.stream().filter(a -> a.getAccountNumber() == no).findFirst()
				.orElseThrow(() -> new InvalidAccountException("Invalid account: " + no));
	}

	private void validAmount(double amount) {
		if (amount <= 0)
			throw new InvalidTransactionException("Amount must be positive");
	}

	public double deposit(long no, double amount) {
		validAmount(amount);
		BankAccount a = findAccount(no);
		BankingOperation op = (amt, bal) -> bal + amt;
		a.credit(amount);
		record(no, "DEPOSIT", amount, "Cash deposit");
		return op.execute(amount, a.getBalance() - amount);
	}

	public double withdraw(long no, double amount) {
		validAmount(amount);
		BankAccount a = findAccount(no);
		if (a.getBalance() - amount < a.getMinimumBalance())
			throw new InsufficientBalanceException("Minimum balance required: " + a.getMinimumBalance());
		a.debit(amount);
		record(no, "WITHDRAW", amount, "Cash withdrawal");
		return a.getBalance();
	}

	public void transfer(long source, long target, double amount) {
		validAmount(amount);
		if (source == target)
			throw new InvalidTransactionException("Source and target cannot be same");
		BankAccount s = findAccount(source), t = findAccount(target);
		if (s.getBalance() - amount < s.getMinimumBalance())
			throw new InsufficientBalanceException("Insufficient source balance");
		s.debit(amount);
		t.credit(amount);
		record(source, "TRANSFER", amount, "Transfer to " + target);
		record(target, "DEPOSIT", amount, "Transfer from " + source);
	}

	public double calculateInterest(long no, double rate) {
		BankAccount a = findAccount(no);
		double interest = a.getBalance() * rate / 100;
		a.credit(interest);
		record(no, "INTEREST", interest, "Interest at " + rate + "%");
		return interest;
	}

	public double checkBalance(long no) {
		return findAccount(no).getBalance();
	}

	private void record(long no, String type, double amount, String d) {
		transactions.add(new Transaction(nextTransactionId++, no, type, amount, LocalDateTime.now(), d));
	}

	public List<Transaction> history(long no) {
		return transactions.stream().filter(t -> t.getAccountNumber() == no)
				.sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).toList();
	}

	public Map<String, Long> accountsByType() {
		return accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
	}

	public Map<String, List<Customer>> customersByCity() {
		return customers.stream().collect(Collectors.groupingBy(Customer::getCity));
	}

	public Map<String, Double> balanceByType() {
		return accounts.stream().collect(
				Collectors.groupingBy(BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance)));
	}

	public Map<Boolean, List<Customer>> partitionPremium() {
		return customers.stream()
				.collect(Collectors.partitioningBy(c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType())));
	}

	public List<Customer> topThreeCustomers() {
		Map<Integer, Double> totals = accounts.stream().collect(
				Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
		return customers.stream().sorted(
				Comparator.comparingDouble((Customer c) -> totals.getOrDefault(c.getCustomerId(), 0d)).reversed())
				.limit(3).toList();
	}

	public void loadSampleData() {
		String[][] d = { { "Rahul", "rahul@gmail.com", "Bangalore", "9876543210", "PREMIUM" },
				{ "Priya", "priya@gmail.com", "Mangalore", "9876543211", "REGULAR" },
				{ "Arun", "arun@gmail.com", "Mysore", "9876543212", "PREMIUM" },
				{ "Sneha", "sneha@gmail.com", "Udupi", "9876543213", "REGULAR" },
				{ "Kiran", "kiran@gmail.com", "Bangalore", "9876543214", "PREMIUM" },
				{ "Asha", "asha@gmail.com", "Bangalore", "9876543215", "REGULAR" },
				{ "Ravi", "ravi@gmail.com", "Mangalore", "9876543216", "PREMIUM" },
				{ "Neha", "neha@gmail.com", "Pune", "9876543217", "REGULAR" },
				{ "Vijay", "vijay@gmail.com", "Mumbai", "9876543218", "PREMIUM" },
				{ "Meera", "meera@gmail.com", "Mysore", "9876543219", "REGULAR" } };
		for (int i = 0; i < d.length; i++)
			addCustomer(new Customer(101 + i, d[i][0], d[i][1], d[i][2], d[i][3], d[i][4]));
		for (int i = 0; i < 15; i++) {
			long n = 100001L + i;
			int cid = 101 + (i % 10);
			double b = 10000 + i * 7500;
			BankAccount a = switch (i % 3) {
			case 0 -> new SavingsAccount(n, cid, b, "ACTIVE");
			case 1 -> new CurrentAccount(n, cid, b, "ACTIVE");
			default -> new LoanAccount(n, cid, b, "ACTIVE");
			};
			addAccount(a);
		}
		for (int i = 0; i < 30; i++) {
			BankAccount a = accounts.get(i % accounts.size());
			double amount = 1000 + (i * 1750);
			if (i % 2 == 0) {
				a.credit(amount);
				record(a.getAccountNumber(), "DEPOSIT", amount, "Opening sample deposit");
			} else {
				record(a.getAccountNumber(), "WITHDRAW", amount, "Sample withdrawal record");
			}
		}
	}
}
