package com.library.app;

import com.library.dao.HibernateUtil;
import com.library.service.LibraryService;

import java.util.Scanner;

public class LibraryApplication {

    public static void main(String[] args) {
        LibraryService service = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("========== LIBRARY MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add Printed Book");
            System.out.println("2. Add EBook");
            System.out.println("3. View All Books");
            System.out.println("4. Search Book By ID");
            System.out.println("5. Update Book Price");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Publisher: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Pages: ");
                    int pages = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Shelf No: ");
                    String shelfNo = scanner.nextLine();
                    System.out.print("Enter Edition: ");
                    String edition = scanner.nextLine();

                    service.addPrintedBook(title, author, publisher, isbn, price, category, pages, shelfNo, edition);
                }
                case 2 -> {
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Publisher: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter File Format: ");
                    String format = scanner.nextLine();
                    System.out.print("Enter File Size (MB): ");
                    double size = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Download Link: ");
                    String link = scanner.nextLine();

                    service.addEBook(title, author, publisher, isbn, price, category, format, size, link);
                }
                case 3 -> service.viewAllBooks();
                case 4 -> {
                    System.out.print("Enter Book ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    service.searchBookById(id);
                }
                case 5 -> {
                    System.out.print("Enter Book ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Enter New Price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    service.updateBookPrice(id, newPrice);
                }
                case 6 -> {
                    System.out.print("Enter Book ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Are you sure? (Y/N): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        service.deleteBook(id);
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                }
                case 7 -> {
                    System.out.println("Exiting Application... Goodbye!");
                    HibernateUtil.shutdown();
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please select between 1 and 7.");
            }
        }
    }
}
