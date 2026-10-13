package com.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    @Column(unique = true)
    private String isbn;
    private double price;
    private String category;

    public Book() {
    }

    public Book(String title, String author, String publisher, String isbn, double price, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String v) {
        title = v;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String v) {
        author = v;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String v) {
        publisher = v;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String v) {
        isbn = v;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double v) {
        price = v;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String v) {
        category = v;
    }

    @Override
    public String toString() {
        return id + " | " + title + " | " + author + " | " + getClass().getSimpleName() + " | " + price + " | " + category;
    }
}
