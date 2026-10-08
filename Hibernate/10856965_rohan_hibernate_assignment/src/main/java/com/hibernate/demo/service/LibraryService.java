package com.hibernate.demo.service;

import com.hibernate.demo.dao.BookDAO;
import com.hibernate.demo.model.Book;

import java.util.List;

public class LibraryService {
    private final BookDAO bookDAO = new BookDAO();

    public void addBook(Book book) {
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

    public void deleteBook(Long id) {
        bookDAO.deleteBook(id);
    }

}
