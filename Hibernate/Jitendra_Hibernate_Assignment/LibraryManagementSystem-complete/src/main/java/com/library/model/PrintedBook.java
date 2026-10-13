package com.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "printed_book")
public class PrintedBook extends Book {
    private int pages;
    @Column(name = "shelf_no")
    private String shelfNo;
    private String edition;

    public PrintedBook() {
    }

    public PrintedBook(String t, String a, String p, String i, double pr, String c, int pages, String shelf, String edition) {
        super(t, a, p, i, pr, c);
        this.pages = pages;
        this.shelfNo = shelf;
        this.edition = edition;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int v) {
        pages = v;
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String v) {
        shelfNo = v;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String v) {
        edition = v;
    }
}
