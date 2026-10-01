package Service;

import Model.Transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

/*
Author : 
Date: 
Project : 
*/
public class TransactionService {

    List<Transaction> transactions = new ArrayList<>();

    public TransactionService(){
        transactions = new ArrayList<>();

        transactions.add(new Transaction(
                10001L, 1001, "DEPOSIT", 5000,
                new Date(System.currentTimeMillis() - 10L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10002L, 1001, "WITHDRAW", 2000,
                new Date(System.currentTimeMillis() - 9L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10003L, 1002, "DEPOSIT", 10000,
                new Date(System.currentTimeMillis() - 8L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10004L, 1003, "LOAN_DISBURSEMENT", 150000,
                new Date(System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10005L, 1004, "DEPOSIT", 3000,
                new Date(System.currentTimeMillis() - 6L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10006L, 1005, "WITHDRAW", 5000,
                new Date(System.currentTimeMillis() - 5L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10007L, 1006, "LOAN_PAYMENT", 10000,
                new Date(System.currentTimeMillis() - 4L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10008L, 1007, "DEPOSIT", 2500,
                new Date(System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10009L, 1008, "TRANSFER_IN", 12000,
                new Date(System.currentTimeMillis() - 2L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10010L, 1009, "LOAN_PAYMENT", 5000,
                new Date(System.currentTimeMillis() - 1L * 24 * 60 * 60 * 1000)));

        transactions.add(new Transaction(
                10011L, 1010, "DEPOSIT", 7000,
                new Date()));

        transactions.add(new Transaction(
                10012L, 1011, "TRANSFER_OUT", 4000,
                new Date()));

        transactions.add(new Transaction(
                10013L, 1012, "WITHDRAW", 1500,
                new Date()));

        transactions.add(new Transaction(
                10014L, 1002, "TRANSFER_OUT", 8000,
                new Date()));

        transactions.add(new Transaction(
                10015L, 1008, "TRANSFER_IN", 8000,
                new Date()));
    }
    public void registerTransaction(int accountNumber, double amount, String tranType){
        long tranId = RandomGenerator.getDefault().nextLong();
        this.transactions.add(new Transaction(tranId,accountNumber,tranType,amount, Date.from(Instant.now())));
    }

    public void history(){
        this.transactions.forEach(System.out::println);
    }

    public void generateBankStatement(int accountNumber){
        transactions.stream().filter(txn -> txn.accountNumber() == accountNumber)
                .sorted(Comparator.comparing(Transaction::date).reversed())
                .forEach(System.out::println);
    }

    public void transactionReport(){
        System.out.println("Highest transaction : " + this.transactions.stream().max(Comparator.comparingDouble(Transaction::amount)));
        System.out.println("Transaction volume by type : " +
                this.transactions.stream().collect(Collectors.groupingBy(Transaction::tranType, Collectors.summingDouble(Transaction::amount)))
                );
        System.out.println("Average transaction : " +
                this.transactions.stream()
                        .mapToDouble(Transaction::amount)
                        .average().orElse(0)
                );
    }
}
