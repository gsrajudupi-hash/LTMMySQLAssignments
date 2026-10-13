package com.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "e_book")
public class EBook extends Book {
    @Column(name = "file_format")
    private String fileFormat;
    @Column(name = "file_size")
    private double fileSize;
    @Column(name = "download_link", length = 500)
    private String downloadLink;

    public EBook() {
    }

    public EBook(String t, String a, String p, String i, double pr, String c, String f, double s, String l) {
        super(t, a, p, i, pr, c);
        fileFormat = f;
        fileSize = s;
        downloadLink = l;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String v) {
        fileFormat = v;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double v) {
        fileSize = v;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String v) {
        downloadLink = v;
    }
}
