package assignment3.util;
import assignment3.model.BankAccount;
import assignment3.model.Customer;
import assignment3.model.*; import java.time.*; import java.time.format.DateTimeFormatter;
public final class BankingReport { private BankingReport(){}
 public static String statement(Customer c, BankAccount a){return """
 ==================================
 BANK ACCOUNT STATEMENT
 ==================================
 Account Number : %d
 Customer       : %s
 Account Type   : %s
 Balance        : ₹%.2f
 Status         : %s
 Generated      : %s
 ==================================
 """.formatted(a.accountNumber(),c.getName(),a.accountType(),a.balance(),a.status(),LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));}
 public static String customerJson(Customer c){return """
 {"customerId":%d,"name":"%s","city":"%s","customerType":"%s"}
 """.formatted(c.getCustomerId(),c.getName(),c.getCity(),c.getCustomerType());}
 public static String accountJson(BankAccount a){return """
 {"accountNumber":%d,"customerId":%d,"accountType":"%s","balance":%.2f,"status":"%s"}
 """.formatted(a.accountNumber(),a.customerId(),a.accountType(),a.balance(),a.status());}
 public static String transactionJson(Transaction t){return """
 {"transactionId":%d,"accountNumber":%d,"transactionType":"%s","amount":%.2f,"transactionDate":"%s"}
 """.formatted(t.transactionId(),t.accountNumber(),t.transactionType(),t.amount(),t.transactionDate());}
 public static String accountDetails(BankAccount a){if(a instanceof SavingsAccount s)return "Savings minimum: ₹"+s.minimumBalance();if(a instanceof CurrentAccount c)return "Current minimum: ₹"+c.minimumBalance();if(a instanceof LoanAccount l)return "Loan account, minimum: ₹"+l.minimumBalance();return "Unknown";}
 public static String classify(String t){return switch(t){case "DEPOSIT","INTEREST","TRANSFER_IN"->"CREDIT";case "WITHDRAW","LOAN_PAYMENT","TRANSFER_OUT"->"DEBIT";case "TRANSFER"->"TRANSFER";default->"UNKNOWN";};}
}
