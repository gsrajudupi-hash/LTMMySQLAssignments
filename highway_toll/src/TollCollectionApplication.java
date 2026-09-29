
import domain.CollectionReceipt;
import domain.TollCalculation;
import domain.Truck;
import exception.InvalidTruckDataException;
import service.StandardTollCalculator;
import service.TollBooth;
import service.TollCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class TollCollectionApplication {

    private static final String SEPARATOR =
            "========================================";

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private TollCollectionApplication() {
        // Prevent object creation for utility-style main class.
    }

    public static void main(String[] args) {
        TollCalculator tollCalculator =
                new StandardTollCalculator();

        TollBooth tollBooth = new TollBooth(
                tollCalculator,
                Clock.systemDefaultZone()
        );

        List<Truck> arrivingTrucks = List.of(
                new Truck("TRK101", "Ford", 5, 12_500),
                new Truck("TRK102", "Tata", 4, 11_500)
        );

        try {
            for (Truck truck : arrivingTrucks) {
                processAndDisplay(tollBooth, truck);
            }

            displayTotals(tollBooth.getCurrentTotals());

            CollectionReceipt receipt =
                    tollBooth.collectReceipts();

            displayCollectionReceipt(receipt);

            System.out.println("Totals have been reset.");

            displayTotals(tollBooth.getCurrentTotals());

        } catch (InvalidTruckDataException exception) {
            System.err.println(
                    "Invalid truck information: "
                            + exception.getMessage()
            );
        } catch (ArithmeticException exception) {
            System.err.println(
                    "Toll calculation failed: "
                            + exception.getMessage()
            );
        } catch (Exception exception) {
            System.err.println(
                    "Unexpected application error: "
                            + exception.getMessage()
            );
        }
    }

    private static void processAndDisplay(
            TollBooth tollBooth,
            Truck truck
    ) {
        TollCalculation calculation =
                tollBooth.processTruck(truck);

        System.out.println(SEPARATOR);
        System.out.println("TRUCK ARRIVAL");
        System.out.println(SEPARATOR);
        System.out.printf(
                "Truck ID       : %s%n",
                truck.getId()
        );
        System.out.printf(
                "Truck Make     : %s%n",
                truck.getMake()
        );
        System.out.printf(
                "Number of Axles: %d%n",
                truck.getNumberOfAxles()
        );
        System.out.printf(
                "Total Weight   : %d kg%n",
                truck.getTotalWeightInKg()
        );
        System.out.printf(
                "Axle Charge    : $%s%n",
                formatMoney(calculation.axleCharge())
        );
        System.out.printf(
                "Weight Units   : %d%n",
                calculation.weightUnits()
        );
        System.out.printf(
                "Weight Charge  : $%s%n",
                formatMoney(calculation.weightCharge())
        );
        System.out.printf(
                "Toll Due       : $%s%n",
                formatMoney(calculation.totalToll())
        );
        System.out.println(SEPARATOR);
    }

    private static void displayTotals(
            CollectionReceipt totals
    ) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println(SEPARATOR);
        System.out.printf(
                "Total Trucks  : %d%n",
                totals.totalTrucks()
        );
        System.out.printf(
                "Total Receipts: $%s%n",
                formatMoney(totals.totalReceipts())
        );
        System.out.println(SEPARATOR);
    }

    private static void displayCollectionReceipt(
            CollectionReceipt receipt
    ) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("RECEIPT COLLECTION");
        System.out.println(SEPARATOR);
        System.out.printf(
                "Total Trucks  : %d%n",
                receipt.totalTrucks()
        );
        System.out.printf(
                "Total Receipts: $%s%n",
                formatMoney(receipt.totalReceipts())
        );
        System.out.printf(
                "Collected At  : %s%n",
                receipt.collectedAt()
                        .format(DATE_TIME_FORMATTER)
        );
        System.out.println(SEPARATOR);
    }

    private static String formatMoney(BigDecimal amount) {
        return amount
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();
    }
}
