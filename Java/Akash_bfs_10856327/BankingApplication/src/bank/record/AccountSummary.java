package bank.record;

public record AccountSummary(
        String accountType,
        long numberOfAccounts,
        double totalBalance,
        double averageBalance) {
}