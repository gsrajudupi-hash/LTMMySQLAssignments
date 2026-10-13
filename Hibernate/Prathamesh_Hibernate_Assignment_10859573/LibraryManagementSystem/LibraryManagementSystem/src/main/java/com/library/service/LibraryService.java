package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.EBook;
import com.library.model.PrintedBook;

import java.util.List;

public class LibraryService {

    private final BookDAO bookDAO = new BookDAO();

    public void addPrintedBook(String title, String author, String publisher, String isbn,
                               double price, String category, int pages, String shelfNo, String edition) {
        PrintedBook printedBook = new PrintedBook(title, author, publisher, isbn, price, category, pages, shelfNo, edition);
        bookDAO.saveBook(printedBook);
        System.out.println("Book saved successfully.");
    }

    public void addEBook(String title, String author, String publisher, String isbn,
                         double price, String category, String fileFormat, double fileSize, String downloadLink) {
        EBook eBook = new EBook(title, author, publisher, isbn, price, category, fileFormat, fileSize, downloadLink);
        bookDAO.saveBook(eBook);
        System.out.println("EBook saved successfully.");
    }

    public void viewAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-15s | %-12s | %-8s | %-15s%n", "ID", "Title", "Author", "Type", "Price", "Category");
        System.out.println("----------------------------------------------------------------------------------");
        for (Book book : books) {
            String type = (book instanceof PrintedBook) ? "PrintedBook" : (book instanceof EBook) ? "EBook" : "Book";
            System.out.printf("%-5d | %-20s | %-15s | %-12s | %-8.2f | %-15s%n",
                    book.getId(), book.getTitle(), book.getAuthor(), type, book.getPrice(), book.getCategory());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    public void searchBookById(Long id) {
        Book book = bookDAO.getBookById(id);
        if (book != null) {
            System.out.println("--- Book Details ---");
            System.out.println("ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Publisher: " + book.getPublisher());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Price: " + book.getPrice());
            System.out.println("Category: " + book.getCategory());

            if (book instanceof PrintedBook pb) {
                System.out.println("Type: PrintedBook");
                System.out.println("Pages: " + pb.getPages());
                System.out.println("Shelf No: " + pb.getShelfNo());
                System.out.println("Edition: " + pb.getEdition());
            } else if (book instanceof EBook eb) {
                System.out.println("Type: EBook");
                System.out.println("File Format: " + eb.getFileFormat());
                System.out.println("File Size: " + eb.getFileSize() + " MB");
                System.out.println("Download Link: " + eb.getDownloadLink());
            }
        } else {
            System.out.println("Book not found with ID: " + id);
        }
    }

    public void updateBookPrice(Long id, double newPrice) {
        Book book = bookDAO.getBookById(id);
        if (book != null) {
            System.out.println("Current Price: " + book.getPrice());
            book.setPrice(newPrice);
            bookDAO.updateBook(book);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found with ID: " + id);
        }
    }

    public void deleteBook(Long id) {
        bookDAO.deleteBook(id);
    }
}
