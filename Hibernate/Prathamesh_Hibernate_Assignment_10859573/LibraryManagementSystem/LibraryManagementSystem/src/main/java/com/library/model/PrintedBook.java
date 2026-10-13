package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "printed_book")
public class PrintedBook extends Book {

    private int pages;
    private String shelfNo;
    private String edition;

    public PrintedBook() {}

    public PrintedBook(String title, String author, String publisher, String isbn, double price, String category,
                       int pages, String shelfNo, String edition) {
        super(title, author, publisher, isbn, price, category);
        this.pages = pages;
        this.shelfNo = shelfNo;
        this.edition = edition;
    }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public String getShelfNo() { return shelfNo; }
    public void setShelfNo(String shelfNo) { this.shelfNo = shelfNo; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }
}
