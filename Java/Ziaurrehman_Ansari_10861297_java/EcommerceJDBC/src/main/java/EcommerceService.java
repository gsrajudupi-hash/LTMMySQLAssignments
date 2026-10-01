

//import com.mysql.cj.protocol.Resultset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/*
Author : 
Date: 
Project : 
*/
public class EcommerceService {


    static void main() {
        try( Connection con = JDBCUtility.createConnection(); BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(System.in))){


            EcommerceDAO dao = new EcommerceDAO(con);
            int option = 0;
            do {
                displayMenu();

                System.out.print("Enter option: ");

                try {
                    option = Integer.parseInt(reader.readLine().trim());
                } catch (NumberFormatException exception) {
                    System.out.println(
                            "Invalid input. Enter a number from 1 to 11."
                    );
                    option = 0;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    switch (option) {

                        case 1:

                            dao.addOrderForUser( reader);
                            break;

                        case 2:
                            dao.getOrdersByFirstName(reader);
                            break;
                        case 3:
                            dao.updatePriceForProduct(
                                    reader
                            );
                            break;
                        case 4:
                            dao.deleteUser(reader);
                            break;

                        case 5:

                            dao.findMoreSpendingUsers(reader);
                            break;
                        case 6:
                            dao.getOrdersForUser(reader);
                            break;

                        case 7:

                            dao.totalSpend(reader);
                            break;
                        case 8:

                            dao.getAllProducts();
                            break;
                        case 9:
                            dao.demonstrateTransaction();
                            break;
                        case 10:
                            dao.addUser(reader);
                            break;
                        case 11:
                            System.out.println(
                                    "Exiting application."
                            );
                            break;

                        default:
                            System.out.println(
                                    "Select an option from 1 to 11."
                            );
                    }

                } catch (SQLException exception) {
                    System.out.println(
                            "Database error: "
                                    + exception.getMessage()
                    );
                }

                System.out.println();

            } while (option != 11);

        } catch (SQLException exception) {
            System.out.println(
                    "Could not connect to database: "
                            + exception.getMessage()
            );

        } catch (IOException exception) {
            System.out.println(
                    "Input error: " + exception.getMessage()
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

    private static void displayMenu() {

        System.out.println("================================");
        System.out.println(" JDBC ASSIGNMENT MENU");
        System.out.println("================================");
        System.out.println("1. Add order for user");
        System.out.println("2. Get orders by first name");
        System.out.println("3. Update price for product");
        System.out.println("4. Delete user");
        System.out.println("5. Find users with bill greater than threshold amount");
        System.out.println("6. Get Orders for user");
        System.out.println("7. Find total spend for user");
        System.out.println("8. Get all products");
        System.out.println("9. TransactionFailing");
        System.out.println("10. Get Orders for user");
        System.out.println("11. Exit");
        System.out.println("================================");
    }

}
