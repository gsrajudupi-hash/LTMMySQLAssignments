package java17;

/**
 * Class Name : SwitchExpressionDemo
 * Created By : 10860819
 * Created Date : 10/1/2026
 * Created Time : 12:10 AM
 */
public class SwitchExpressionDemo {
    public static void main(String[] args) {

// Test different transaction types
        displayCategory("DEPOSIT");
        displayCategory("INTEREST");
        displayCategory("WITHDRAW");
        displayCategory("LOAN_PAYMENT");
        displayCategory("TRANSFER");
        displayCategory("CASHBACK");
    }

    public static void displayCategory(String transactionType) {

        String category = switch (transactionType) {
            case "DEPOSIT", "INTEREST" -> "CREDIT";
            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
            case "TRANSFER" -> "TRANSFER";
            default -> "UNKNOWN";
        };
        System.out.println(transactionType + " -> " + category);
    }
}