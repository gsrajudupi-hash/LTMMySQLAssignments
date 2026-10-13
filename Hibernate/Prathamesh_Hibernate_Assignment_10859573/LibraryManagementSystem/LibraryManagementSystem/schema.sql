-- Database Creation Script for Library Management System
CREATE DATABASE IF NOT EXISTS librarydb;
USE librarydb;

-- 1. Main Table for Book (Parent Table)
CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255),
    isbn VARCHAR(50),
    price DOUBLE NOT NULL,
    category VARCHAR(100)
);

-- 2. Sub-table for PrintedBook (JOINED Inheritance)
CREATE TABLE IF NOT EXISTS printed_book (
    id BIGINT PRIMARY KEY,
    pages INT NOT NULL,
    shelfNo VARCHAR(50),
    edition VARCHAR(50),
    FOREIGN KEY (id) REFERENCES book(id) ON DELETE CASCADE
);

-- 3. Sub-table for EBook (JOINED Inheritance)
CREATE TABLE IF NOT EXISTS e_book (
    id BIGINT PRIMARY KEY,
    fileFormat VARCHAR(20),
    fileSize DOUBLE NOT NULL,
    downloadLink VARCHAR(255),
    FOREIGN KEY (id) REFERENCES book(id) ON DELETE CASCADE
);
