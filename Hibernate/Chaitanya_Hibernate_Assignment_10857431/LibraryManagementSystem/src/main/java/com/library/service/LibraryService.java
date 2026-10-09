package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

public class LibraryService {

    private final BookDAO bookDAO = new BookDAO();

    public void saveBook(Book book) {
        bookDAO.saveBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book getBookById(Long id) {
        return bookDAO.getBookById(id);
    }

    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    public void deleteBook(Book book) {
        bookDAO.deleteBook(book);
    }
}
