package java17;

public record TransactionRecord(int transactionId,
                                long accountNumber,
                                String transactionType,
                                double amount) {


//  Compact constructor
    public TransactionRecord {
        if(amount < 0){
            throw  new IllegalArgumentException("Transaction Amount must be Positive");
        }
        if(transactionType == null){
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
    }
}
