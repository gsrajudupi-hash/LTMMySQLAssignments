package com.hibernate.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {

//import jakarta.persistence.*;



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String author;
        private String publisher;
        private String isbn;
        private double price;
        private String category;

        public Book() {
        }

        public Book(String title, String author, String publisher,
                    String isbn, double price, String category) {
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
