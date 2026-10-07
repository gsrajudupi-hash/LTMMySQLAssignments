package com.library;

import com.library.service.LibraryService;
import java.util.Scanner;

public class LibraryApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LibraryService service = new LibraryService();

        while (true) {

            System.out.println("\n====================================");
            System.out.println(" LIBRARY MANAGEMENT SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Add Printed Book");
            System.out.println("2. Add EBook");
            System.out.println("3. View All Books");
            System.out.println("4. Search Book By ID");
            System.out.println("5. Update Book Price");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> service.addPrintedBook();
                case 2 -> service.addEBook();
                case 3 -> service.viewAllBooks();
                case 4 -> service.searchBookById();
                case 5 -> service.updateBookPrice();
                case 6 -> service.deleteBook();
                case 7 -> {
                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }
}