package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.*;

import java.util.*;

public class LibraryService {
    private final BookDAO dao = new BookDAO();

    public Book addPrintedBook(String t, String a, String p, String i, double price, String c, int pages, String shelf, String ed) {
        return dao.saveBook(new PrintedBook(t, a, p, i, price, c, pages, shelf, ed));
    }

    public Book addEBook(String t, String a, String p, String i, double price, String c, String f, double size, String link) {
        return dao.saveBook(new EBook(t, a, p, i, price, c, f, size, link));
    }

    public List<Book> getAllBooks() {
        return dao.getAllBooks();
    }

    public Book getBookById(long id) {
        return dao.getBookById(id);
    }

    public boolean updatePrice(long id, double price) {
        Book b = dao.getBookById(id);
        if (b == null) return false;
        b.setPrice(price);
        dao.updateBook(b);
        return true;
    }

    public boolean deleteBook(long id) {
        return dao.deleteBook(id);
    }
}
