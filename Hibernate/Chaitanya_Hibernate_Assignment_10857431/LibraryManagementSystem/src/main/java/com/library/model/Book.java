package com.library.model;

import jakarta.persistence.*;

/**
 * @Inheritance(strategy = InheritanceType.JOINED) :
 * it specifies that the inheritance strategy for the Book class and its subclasses (PrintedBook and EBook) will be JOINED.
 * In this strategy, each class in the hierarchy will have its own table in the database,
 * and the tables will be joined using foreign keys to represent the relationships between the classes.
 * */

@Entity
@Table(name = "book")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "author", nullable = false, length = 100)
    private String author;

    @Column(name = "publisher", nullable = false, length = 100)
    private String publisher;

    @Column(name = "isbn", nullable = false, length = 30)
    private String isbn;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "category", nullable = false, length = 50)
    private String category;
    /*
     * Hibernate requires a no-argument constructor.
     */
    public Book() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
