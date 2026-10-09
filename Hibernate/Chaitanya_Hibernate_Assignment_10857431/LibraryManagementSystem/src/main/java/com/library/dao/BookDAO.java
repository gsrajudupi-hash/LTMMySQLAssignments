package com.library.dao;

import com.library.model.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookDAO {

    public void saveBook(Book book) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<Book> getAllBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book", Book.class).list();
        }
    }

    public Book getBookById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        }
    }

    public void updateBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.merge(book));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
