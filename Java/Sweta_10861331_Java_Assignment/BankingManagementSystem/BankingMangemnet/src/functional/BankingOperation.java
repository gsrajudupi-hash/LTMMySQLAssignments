package functional;


@FunctionalInterface
public interface BankingOperation {


    double execute(double amount,
                   double balance);
}