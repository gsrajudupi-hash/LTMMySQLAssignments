package com.library.app;

import com.library.dao.HibernateUtil;
import com.library.model.Book;
import com.library.model.EBook;
import com.library.model.PrintedBook;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class LibraryApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService service = new LibraryService();

    public static void main(String[] args) {
        boolean running = true;

        try {
            while (running) {
                showMenu();
                int choice = readInt("Enter your choice: ");

                try {
                    switch (choice) {
                        case 1 -> addPrintedBook();
                        case 2 -> addEBook();
                        case 3 -> viewAllBooks();
                        case 4 -> searchBook();
                        case 5 -> updateBook();
                        case 6 -> deleteBook();
                        case 7 -> running = false;
                        default -> System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("Operation failed: " + e.getMessage());
                }
            }
        } finally {
            scanner.close();
            HibernateUtil.shutdown();
        }
    }

    private static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("       LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Printed Book");
        System.out.println("2. Add EBook");
        System.out.println("3. View All Books");
        System.out.println("4. Search Book By ID");
        System.out.println("5. Update Book");
        System.out.println("6. Delete Book");
        System.out.println("7. Exit");
    }

    private static void addPrintedBook() {
        PrintedBook book = new PrintedBook();
        readCommonDetails(book);
        book.setPages(readInt("Pages: "));
        book.setShelfNo(readString("Shelf No: "));
        book.setEdition(readString("Edition: "));

        service.saveBook(book);
        System.out.println("Printed book saved successfully.");
    }

    private static void addEBook() {
        EBook book = new EBook();
        readCommonDetails(book);
        book.setFileFormat(readString("File Format: "));
        book.setFileSize(readDouble("File Size: "));
        book.setDownloadLink(readString("Download Link: "));

        service.saveBook(book);
        System.out.println("EBook saved successfully.");
    }

    private static void readCommonDetails(Book book) {
        book.setTitle(readString("Title: "));
        book.setAuthor(readString("Author: "));
        book.setPublisher(readString("Publisher: "));
        book.setIsbn(readString("ISBN: "));
        book.setPrice(readDouble("Price: "));
        book.setCategory(readString("Category: "));
    }

    private static void viewAllBooks() {
        List<Book> books = service.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book book : books) {
            printBook(book);
            System.out.println("----------------------------------------");
        }
    }

    private static void searchBook() {
        long id = readLong("Enter Book ID: ");
        Book book = service.getBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
        } else {
            printBook(book);
        }
    }

    private static void updateBook() {
        long id = readLong("Enter Book ID: ");
        Book book = service.getBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        double price = readDouble("Enter New Price: ");
        book.setPrice(price);
        service.updateBook(book);
        System.out.println("Book updated successfully.");
    }

    private static void deleteBook() {
        long id = readLong("Enter Book ID: ");
        Book book = service.getBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        String confirmation = readString("Are you sure? (Y/N): ");
        if (confirmation.equalsIgnoreCase("Y")) {
            service.deleteBook(book);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void printBook(Book book) {
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publisher: " + book.getPublisher());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Price: " + book.getPrice());
        System.out.println("Category: " + book.getCategory());

        if (book instanceof PrintedBook printedBook) {
            System.out.println("Type: PrintedBook");
            System.out.println("Pages: " + printedBook.getPages());
            System.out.println("Shelf No: " + printedBook.getShelfNo());
            System.out.println("Edition: " + printedBook.getEdition());
        } else if (book instanceof EBook eBook) {
            System.out.println("Type: EBook");
            System.out.println("File Format: " + eBook.getFileFormat());
            System.out.println("File Size: " + eBook.getFileSize());
            System.out.println("Download Link: " + eBook.getDownloadLink());
        }
    }

    private static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static long readLong(String message) {
        while (true) {
            try {
                return Long.parseLong(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
