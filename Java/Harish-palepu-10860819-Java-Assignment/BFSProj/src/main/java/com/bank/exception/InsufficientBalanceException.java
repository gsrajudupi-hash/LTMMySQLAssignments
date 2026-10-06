package com.bank.exception;

public class InsufficientBalanceException extends BankingException {
    private final double availableBalance;
    private final double requestedAmount;
    private final double minimumBalance;

    public InsufficientBalanceException(long accountNumber, double availableBalance,
                                        double requestedAmount, double minimumBalance) {
        super(String.format(
                "Account %d cannot be debited %.2f. Available balance: %.2f, minimum balance required: %.2f.",
                accountNumber, requestedAmount, availableBalance, minimumBalance));
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
        this.minimumBalance = minimumBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double getShortfall() {
        return requestedAmount - (availableBalance - minimumBalance);
    }
}
