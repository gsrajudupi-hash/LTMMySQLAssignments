package java8;

@FunctionalInterface
public interface BankingOperation {

    double execute(double amount, double balance);

}
