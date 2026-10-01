package Service;

import Exceptions.InsufficientBalance;

@FunctionalInterface
public interface BankingOperation {
    double execute(double a, double b) throws InsufficientBalance;
}
