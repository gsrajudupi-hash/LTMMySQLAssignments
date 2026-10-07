package com.library.dao;

import com.library.model.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookDAO {

    /*
     * CREATE
     * Saves either a PrintedBook or an EBook.
     */
    public boolean saveBook(Book book) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(book);

            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Error while saving book: "
                    + e.getMessage());

            return false;
        }
    }

    /*
     * READ
     * Returns every PrintedBook and EBook.
     */
    public List<Book> getAllBooks() {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery("FROM Book", Book.class)
                    .getResultList();

        } catch (Exception e) {

            System.err.println("Error while retrieving books: "
                    + e.getMessage());

            return List.of();
        }
    }

    /*
     * READ BY ID
     * Returns null when the book is not found.
     */
    public Book getBookById(Long id) {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Book.class, id);

        } catch (Exception e) {

            System.err.println("Error while searching for book: "
                    + e.getMessage());

            return null;
        }
    }

    /*
     * UPDATE
     * Updates a detached Book object.
     */
    public boolean updateBook(Book book) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(book);

            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Error while updating book: "
                    + e.getMessage());

            return false;
        }
    }

    /*
     * DELETE
     * Deletes a book using its ID.
     */
    public boolean deleteBook(Long id) {

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Book book = session.get(Book.class, id);

            if (book == null) {
                transaction.rollback();
                return false;
            }

            session.remove(book);

            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            System.err.println("Error while deleting book: "
                    + e.getMessage());

            return false;
        }
    }
}