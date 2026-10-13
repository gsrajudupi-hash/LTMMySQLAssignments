package com.library.app;

import com.library.dao.HibernateUtil;
import com.library.model.Book;
import com.library.service.LibraryService;

import java.util.*;

public class LibraryApplication {
    private static final Scanner SC = new Scanner(System.in);
    private static final LibraryService S = new LibraryService();

    public static void main(String[] args) {
        try {
            boolean run = true;
            while (run) {
                menu();
                int c = readInt("Enter your choice: ");
                try {
                    switch (c) {
                        case 1 -> addPrinted();
                        case 2 -> addEbook();
                        case 3 -> viewAll();
                        case 4 -> search();
                        case 5 -> update();
                        case 6 -> delete();
                        case 7 -> run = false;
                        default -> System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("Operation failed: " + rootMessage(e));
                }
            }
        } finally {
            HibernateUtil.shutdown();
            SC.close();
        }
        System.out.println("Application closed.");
    }

    private static void menu() {
        System.out.println("\n==================================================\n          LIBRARY MANAGEMENT SYSTEM\n==================================================\n1. Add Printed Book\n2. Add EBook\n3. View All Books\n4. Search Book By ID\n5. Update Book\n6. Delete Book\n7. Exit\n");
    }

    private static String read(String p) {
        System.out.print(p);
        return SC.nextLine().trim();
    }

    private static int readInt(String p) {
        while (true) {
            try {
                return Integer.parseInt(read(p));
            } catch (Exception e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private static long readLong(String p) {
        while (true) {
            try {
                return Long.parseLong(read(p));
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static double readDouble(String p) {
        while (true) {
            try {
                return Double.parseDouble(read(p));
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static Object[] common() {
        return new Object[]{read("Enter Title       : "), read("Enter Author      : "), read("Enter Publisher   : "), read("Enter ISBN        : "), readDouble("Enter Price       : "), read("Enter Category    : ")};
    }

    private static void addPrinted() {
        Object[] x = common();
        S.addPrintedBook((String) x[0], (String) x[1], (String) x[2], (String) x[3], (double) x[4], (String) x[5], readInt("Enter Pages       : "), read("Enter Shelf No    : "), read("Enter Edition     : "));
        System.out.println("Book saved successfully.");
    }

    private static void addEbook() {
        Object[] x = common();
        S.addEBook((String) x[0], (String) x[1], (String) x[2], (String) x[3], (double) x[4], (String) x[5], read("Enter File Format     : "), readDouble("Enter File Size (MB)  : "), read("Enter Download Link   : "));
        System.out.println("EBook saved successfully.");
    }

    private static void viewAll() {
        List<Book> books = S.getAllBooks();
        System.out.println("ID | Title | Author | Type | Price | Category");
        System.out.println("---------------------------------------------------------------");
        if (books.isEmpty()) System.out.println("No books found.");
        else books.forEach(System.out::println);
    }

    private static void search() {
        Book b = S.getBookById(readLong("Enter Book ID: "));
        System.out.println(b == null ? "Book not found." : b);
    }

    private static void update() {
        long id = readLong("Enter Book ID: ");
        Book b = S.getBookById(id);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.println("Current Price: " + b.getPrice());
        double p = readDouble("Enter New Price: ");
        System.out.println(S.updatePrice(id, p) ? "Book updated successfully." : "Book not found.");
    }

    private static void delete() {
        long id = readLong("Enter Book ID: ");
        if (!read("Are you sure? (Y/N): ").equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }
        System.out.println(S.deleteBook(id) ? "Book deleted successfully." : "Book not found.");
    }

    private static String rootMessage(Throwable e) {
        Throwable t = e;
        while (t.getCause() != null) t = t.getCause();
        return t.getMessage() == null ? t.getClass().getSimpleName() : t.getMessage();
    }
}
