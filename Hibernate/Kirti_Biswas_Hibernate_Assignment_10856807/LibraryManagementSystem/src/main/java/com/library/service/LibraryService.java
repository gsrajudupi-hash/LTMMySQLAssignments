package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.EBook;
import com.library.model.PrintedBook;

import java.util.List;
import java.util.Scanner;

public class LibraryService {

    private final BookDAO bookDAO;
    private final Scanner scanner;

    public LibraryService() {
        bookDAO = new BookDAO();
        scanner = new Scanner(System.in);
    }

    // 1. Add Printed Book
    public void addPrintedBook() {

        System.out.println("\n========== ADD PRINTED BOOK ==========");

        String title = readText("Enter Title: ");
        String author = readText("Enter Author: ");
        String publisher = readText("Enter Publisher: ");
        String isbn = readText("Enter ISBN: ");
        double price = readPositiveDouble("Enter Price: ");
        String category = readText("Enter Category: ");
        int pages = readPositiveInt("Enter Number of Pages: ");
        String shelfNo = readText("Enter Shelf Number: ");
        String edition = readText("Enter Edition: ");

        PrintedBook printedBook = new PrintedBook(
                title,
                author,
                publisher,
                isbn,
                price,
                category,
                pages,
                shelfNo,
                edition
        );

        boolean saved = bookDAO.saveBook(printedBook);

        if (saved) {
            System.out.println("Printed book saved successfully.");
        } else {
            System.out.println("Unable to save the printed book.");
        }
    }

    // 2. Add EBook
    public void addEBook() {

        System.out.println("\n========== ADD EBOOK ==========");

        String title = readText("Enter Title: ");
        String author = readText("Enter Author: ");
        String publisher = readText("Enter Publisher: ");
        String isbn = readText("Enter ISBN: ");
        double price = readPositiveDouble("Enter Price: ");
        String category = readText("Enter Category: ");
        String fileFormat = readText("Enter File Format: ");
        double fileSize = readPositiveDouble("Enter File Size (MB): ");
        String downloadLink = readText("Enter Download Link: ");

        EBook eBook = new EBook(
                title,
                author,
                publisher,
                isbn,
                price,
                category,
                fileFormat,
                fileSize,
                downloadLink
        );

        boolean saved = bookDAO.saveBook(eBook);

        if (saved) {
            System.out.println("EBook saved successfully.");
        } else {
            System.out.println("Unable to save the EBook.");
        }
    }

    // 3. View All Books
    public void viewAllBooks() {

        System.out.println("\n================ ALL BOOKS ================");

        List<Book> books = bookDAO.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );

        System.out.printf(
                "%-5s %-25s %-20s %-15s %-12s %-15s%n",
                "ID",
                "Title",
                "Author",
                "Type",
                "Price",
                "Category"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );

        for (Book book : books) {

            String bookType;

            if (book instanceof PrintedBook) {
                bookType = "PrintedBook";
            } else if (book instanceof EBook) {
                bookType = "EBook";
            } else {
                bookType = "Book";
            }

            System.out.printf(
                    "%-5d %-25s %-20s %-15s %-12.2f %-15s%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    bookType,
                    book.getPrice(),
                    book.getCategory()
            );
        }

        System.out.println(
                "------------------------------------------------------------------------------------------"
        );
    }

    // 4. Search Book By ID
    public void searchBookById() {

        System.out.println("\n========== SEARCH BOOK ==========");

        long id = readPositiveLong("Enter Book ID: ");

        Book book = bookDAO.getBookById(id);

        if (book == null) {
            System.out.println("Book not found for ID: " + id);
            return;
        }

        displayBookDetails(book);
    }

    // 5. Update Book Price
    public void updateBookPrice() {

        System.out.println("\n========== UPDATE BOOK ==========");

        long id = readPositiveLong("Enter Book ID: ");

        Book book = bookDAO.getBookById(id);

        if (book == null) {
            System.out.println("Book not found for ID: " + id);
            return;
        }

        System.out.println("Book Title: " + book.getTitle());
        System.out.println("Current Price: " + book.getPrice());

        double newPrice = readPositiveDouble("Enter New Price: ");

        book.setPrice(newPrice);

        boolean updated = bookDAO.updateBook(book);

        if (updated) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Unable to update the book.");
        }
    }

    // 6. Delete Book
    public void deleteBook() {

        System.out.println("\n========== DELETE BOOK ==========");

        long id = readPositiveLong("Enter Book ID: ");

        Book book = bookDAO.getBookById(id);

        if (book == null) {
            System.out.println("Book not found for ID: " + id);
            return;
        }

        System.out.println("Book ID: " + book.getId());
        System.out.println("Book Title: " + book.getTitle());
        System.out.println("Book Author: " + book.getAuthor());

        System.out.print("Are you sure you want to delete this book? (Y/N): ");
        String confirmation = scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        boolean deleted = bookDAO.deleteBook(id);

        if (deleted) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Unable to delete the book.");
        }
    }

    // Display Complete Book Information
    private void displayBookDetails(Book book) {

        System.out.println("\n========== BOOK DETAILS ==========");
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publisher: " + book.getPublisher());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Price: " + book.getPrice());
        System.out.println("Category: " + book.getCategory());

        if (book instanceof PrintedBook printedBook) {

            System.out.println("Book Type: Printed Book");
            System.out.println("Pages: " + printedBook.getPages());
            System.out.println("Shelf Number: " + printedBook.getShelfNo());
            System.out.println("Edition: " + printedBook.getEdition());

        } else if (book instanceof EBook eBook) {

            System.out.println("Book Type: EBook");
            System.out.println("File Format: " + eBook.getFileFormat());
            System.out.println("File Size: " + eBook.getFileSize() + " MB");
            System.out.println("Download Link: " + eBook.getDownloadLink());

        } else {
            System.out.println("Book Type: Book");
        }

        System.out.println("==================================");
    }

    // Read Required Text
    private String readText(String message) {

        while (true) {

            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    // Read Positive Integer
    private int readPositiveInt(String message) {

        while (true) {

            try {
                System.out.print(message);

                int value = Integer.parseInt(
                        scanner.nextLine().trim()
                );

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    // Read Positive Long
    private long readPositiveLong(String message) {

        while (true) {

            try {
                System.out.print(message);

                long value = Long.parseLong(
                        scanner.nextLine().trim()
                );

                if (value > 0) {
                    return value;
                }

                System.out.println("ID must be greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid ID.");
            }
        }
    }

    // Read Positive Decimal
    private double readPositiveDouble(String message) {

        while (true) {

            try {
                System.out.print(message);

                double value = Double.parseDouble(
                        scanner.nextLine().trim()
                );

                if (value >= 0) {
                    return value;
                }

                System.out.println("Value cannot be negative.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric value.");
            }
        }
    }
}