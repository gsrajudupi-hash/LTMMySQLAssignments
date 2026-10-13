package com.library.dao;

import com.library.model.Book;
import org.hibernate.*;

import java.util.*;

public class BookDAO {
    public Book saveBook(Book book) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(book);
            tx.commit();
            return book;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<Book> getAllBooks() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Book order by id", Book.class).list();
        }
    }

    public Book getBookById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Book.class, id);
        }
    }

    public Book updateBook(Book book) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            Book b = s.merge(book);
            tx.commit();
            return b;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public boolean deleteBook(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            Book b = s.get(Book.class, id);
            if (b == null) {
                tx.commit();
                return false;
            }
            s.remove(b);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
