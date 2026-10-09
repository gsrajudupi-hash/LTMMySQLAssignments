package com.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * //PrimaryKeyJoinColumn is used to specify the primary key column of the parent entity (Book)
 * that will be used as a foreign key in the child entity (PrintedBook).
 */
@Entity
@Table(name = "printed_book")
@PrimaryKeyJoinColumn(name = "id")
public class PrintedBook extends Book {
    @Column(name = "pages", nullable = false)
    private int pages;

    @Column(name = "shelf_no", nullable = false, length = 30)
    private String shelfNo;

    @Column(name = "edition", nullable = false, length = 30)
    private String edition;
    public int getPages() { return pages; }
    public String getShelfNo() { return shelfNo; }
    public String getEdition() { return edition; }

    public void setPages(int pages) { this.pages = pages; }
    public void setShelfNo(String shelfNo) { this.shelfNo = shelfNo; }
    public void setEdition(String edition) { this.edition = edition; }
}
