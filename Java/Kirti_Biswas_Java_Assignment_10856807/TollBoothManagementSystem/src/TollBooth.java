/**
 * Author  : 10856807
 * Date    : 28-09-2026
 * Time    : 22:56
 * Project : TollBoothManagementSystem
 */
public interface TollBooth {

    double calculateToll(Truck truck);

    void processTruckArrival(Truck truck);

    void displayTotals();

    void collectReceipts();
}
