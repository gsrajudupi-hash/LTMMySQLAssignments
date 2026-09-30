package assignment3.model;
import java.time.LocalDateTime;
public record Transaction(long transactionId,long accountNumber,String transactionType,double amount,LocalDateTime transactionDate,String description){
 public Transaction {if(transactionId<=0||accountNumber<=0||amount<=0) throw new IllegalArgumentException("Invalid transaction"); if(transactionType==null||transactionType.isBlank()) throw new IllegalArgumentException("Transaction type required"); if(transactionDate==null) transactionDate=LocalDateTime.now();}
}
