package com.hibernate.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "printed_book")
public class PrintedBook extends Book {



        private int pages;
        private String shelfNo;
        private String edition;

        public PrintedBook() {
        }

        public PrintedBook(String title, String author, String publisher,
                           String isbn, double price, String category,
                           int pages, String shelfNo, String edition) {

            super(title, author, publisher, isbn, price, category);

            this.pages = pages;
            this.shelfNo = shelfNo;
            this.edition = edition;
        }

    public void setPages(int pages) {
            this.pages = pages;
        }

    public void setShelfNo(String shelfNo) {
            this.shelfNo = shelfNo;
        }

    public void setEdition(String edition) {
            this.edition = edition;
        }
    }

