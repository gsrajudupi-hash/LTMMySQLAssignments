package com.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * @PrimaryKeyJoinColumn(name = "id") is used to specify the primary key column of the parent entity (Book)
 * that will be used as a foreign key in the child entity (EBook).
 * In this case, the primary key column of the Book entity is "id",
 * and it will be used as the foreign key in the EBook entity.
 */
@Entity
@Table(name = "e_book")
@PrimaryKeyJoinColumn(name = "id")
public class EBook extends Book {

    @Column(name = "file_format", nullable = false, length = 20)
    private String fileFormat;

    @Column(name = "file_size", nullable = false)
    private double fileSize;

    @Column(name = "download_link", nullable = false, length = 500)
    private String downloadLink;

    public EBook() {
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public double getFileSize() {
        return fileSize;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
