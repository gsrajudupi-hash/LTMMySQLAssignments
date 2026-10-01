package Model;

import java.util.Date;

public record Transaction(long tranId, int accountNumber, String tranType, double amount, Date date) {
}
