package bfs;

public class BankingReport {
    public static void printAccountStatement(BankingService service, long accountNumber) {
        BankAccount account = service.findAccount(accountNumber);
        Customer customer = service.findCustomer(account.getCustomerId());
        String subtype = account instanceof SavingsAccount ? "Savings benefits" : account instanceof CurrentAccount ? "Current account facilities" : account instanceof LoanAccount ? "Loan account details" : "Account";
        String statement = """
                =================================
                    BANK ACCOUNT STATEMENT
                =================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : %.2f
                Status         : %s
                Information    : %s
                =================================
                """.formatted(account.getAccountNumber(), customer.getName(), account.getAccountType(), account.getBalance(), account.getStatus(), subtype);
        System.out.println(statement);
    }

    public static void printCustomerJson(Customer c) {
        System.out.println("""
                {
                  "customerId": %d,
                  "name": "%s",
                  "city": "%s",
                  "customerType": "%s"
                }
                """.formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType()));
    }
}
