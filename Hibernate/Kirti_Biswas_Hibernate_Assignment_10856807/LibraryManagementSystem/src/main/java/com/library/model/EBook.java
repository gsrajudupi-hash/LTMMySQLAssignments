package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "e_book")
@PrimaryKeyJoinColumn(name = "id")
public class EBook extends Book {

    private String fileFormat;
    private double fileSize;
    private String downloadLink;

    public EBook() {
    }

    public EBook(String title, String author, String publisher,
                 String isbn, double price, String category,
                 String fileFormat, double fileSize, String downloadLink) {

        super(title, author, publisher, isbn, price, category);
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
        this.downloadLink = downloadLink;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public String toString() {
        return "EBook{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", publisher='" + getPublisher() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", price=" + getPrice() +
                ", category='" + getCategory() + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", fileSize=" + fileSize +
                ", downloadLink='" + downloadLink + '\'' +
                '}';
    }
}