package com.hibernate.demo;

import com.hibernate.demo.dao.HibernateUtil;
import com.hibernate.demo.model.Book;
import com.hibernate.demo.model.EBook;
import com.hibernate.demo.model.PrintedBook;
import com.hibernate.demo.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class DemoApplication {

	private static final Scanner scanner = new Scanner(System.in);
	private static final LibraryService service = new LibraryService();

	public static void main(String[] args) {

		boolean running = true;

		while (running) {

			System.out.println("\n======================================");
			System.out.println("      LIBRARY MANAGEMENT SYSTEM");
			System.out.println("======================================");

			System.out.println("1. Add Printed Book");
			System.out.println("2. Add EBook");
			System.out.println("3. View All Books");
			System.out.println("4. Search Book By ID");
			System.out.println("5. Update Book");
			System.out.println("6. Delete Book");
			System.out.println("7. Exit");

			System.out.print("Enter your choice: ");

			try {

				int choice = Integer.parseInt(scanner.nextLine());

				switch (choice) {

					case 1:
						addPrintedBook();
						break;

					case 2:
						addEBook();
						break;

					case 3:
						viewAllBooks();
						break;

					case 4:
						searchBookById();
						break;

					case 5:
						updateBook();
						break;

					case 6:
						deleteBook();
						break;

					case 7:
						running = false;
						HibernateUtil.shutdown();
						System.out.println("Application closed.");
						break;

					default:
						System.out.println("Invalid choice. Please enter 1-7.");
				}

			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number.");
			}
		}

		scanner.close();
	}

	// =========================================================
	// 1. ADD PRINTED BOOK
	// =========================================================

	private static void addPrintedBook() {

		System.out.println("\n--- Add Printed Book ---");

		System.out.print("Enter Title: ");
		String title = scanner.nextLine();

		System.out.print("Enter Author: ");
		String author = scanner.nextLine();

		System.out.print("Enter Publisher: ");
		String publisher = scanner.nextLine();

		System.out.print("Enter ISBN: ");
		String isbn = scanner.nextLine();

		System.out.print("Enter Price: ");
		double price = Double.parseDouble(scanner.nextLine());

		System.out.print("Enter Category: ");
		String category = scanner.nextLine();

		System.out.print("Enter Pages: ");
		int pages = Integer.parseInt(scanner.nextLine());

		System.out.print("Enter Shelf No: ");
		String shelfNo = scanner.nextLine();

		System.out.print("Enter Edition: ");
		String edition = scanner.nextLine();

		PrintedBook book = new PrintedBook();

		book.setTitle(title);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setIsbn(isbn);
		book.setPrice(price);
		book.setCategory(category);

		book.setPages(pages);
		book.setShelfNo(shelfNo);
		book.setEdition(edition);

		service.addBook(book);

		System.out.println("Printed Book saved successfully.");
	}

	// =========================================================
	// 2. ADD EBOOK
	// =========================================================

	private static void addEBook() {

		System.out.println("\n--- Add EBook ---");

		System.out.print("Enter Title: ");
		String title = scanner.nextLine();

		System.out.print("Enter Author: ");
		String author = scanner.nextLine();

		System.out.print("Enter Publisher: ");
		String publisher = scanner.nextLine();

		System.out.print("Enter ISBN: ");
		String isbn = scanner.nextLine();

		System.out.print("Enter Price: ");
		double price = Double.parseDouble(scanner.nextLine());

		System.out.print("Enter Category: ");
		String category = scanner.nextLine();

		System.out.print("Enter File Format: ");
		String fileFormat = scanner.nextLine();

		System.out.print("Enter File Size (MB): ");
		double fileSize = Double.parseDouble(scanner.nextLine());

		System.out.print("Enter Download Link: ");
		String downloadLink = scanner.nextLine();

		EBook book = new EBook();

		book.setTitle(title);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setIsbn(isbn);
		book.setPrice(price);
		book.setCategory(category);

		book.setFileFormat(fileFormat);
		book.setFileSize(fileSize);
		book.setDownloadLink(downloadLink);

		service.addBook(book);

		System.out.println("EBook saved successfully.");
	}

	// =========================================================
	// 3. VIEW ALL BOOKS
	// =========================================================

	private static void viewAllBooks() {

		System.out.println("\n--- All Books ---");

		List<Book> books = service.getAllBooks();

		if (books.isEmpty()) {
			System.out.println("No books found.");
			return;
		}

		System.out.println(
				"---------------------------------------------------------------"
		);

		System.out.printf(
				"%-5s %-20s %-15s %-15s %-10s%n",
				"ID",
				"Title",
				"Author",
				"Type",
				"Price"
		);

		System.out.println(
				"---------------------------------------------------------------"
		);

		for (Book book : books) {

			System.out.printf(
					"%-5d %-20s %-15s %-15s %-10.2f%n",
					book.getId(),
					book.getTitle(),
					book.getAuthor(),
					book.getClass().getSimpleName(),
					book.getPrice()
			);
		}

		System.out.println(
				"---------------------------------------------------------------"
		);
	}

	// =========================================================
	// 4. SEARCH BOOK
	// =========================================================

	private static void searchBookById() {

		System.out.println("\n--- Search Book ---");

		System.out.print("Enter Book ID: ");

		Long id = Long.parseLong(scanner.nextLine());

		Book book = service.getBookById(id);

		if (book == null) {

			System.out.println("Book not found.");
			return;
		}

		System.out.println("Book found.");

		System.out.println("ID       : " + book.getId());
		System.out.println("Title    : " + book.getTitle());
		System.out.println("Author   : " + book.getAuthor());
		System.out.println("Publisher: " + book.getPublisher());
		System.out.println("ISBN     : " + book.getIsbn());
		System.out.println("Price    : " + book.getPrice());
		System.out.println("Category : " + book.getCategory());

		if (book instanceof PrintedBook) {

			PrintedBook printedBook = (PrintedBook) book;

			System.out.println("Type     : Printed Book");
			System.out.println("Pages    : " + printedBook.getPages());
			System.out.println("Shelf No : " + printedBook.getShelfNo());
			System.out.println("Edition  : " + printedBook.getEdition());

		} else if (book instanceof EBook) {

			EBook eBook = (EBook) book;

			System.out.println("Type         : EBook");
			System.out.println("File Format  : " + eBook.getFileFormat());
			System.out.println("File Size    : " + eBook.getFileSize());
			System.out.println("Download Link: " + eBook.getDownloadLink());
		}
	}

	// =========================================================
	// 5. UPDATE BOOK
	// =========================================================

	private static void updateBook() {

		System.out.println("\n--- Update Book ---");

		System.out.print("Enter Book ID: ");

		Long id = Long.parseLong(scanner.nextLine());

		Book book = service.getBookById(id);

		if (book == null) {

			System.out.println("Book not found.");
			return;
		}

		System.out.println("Current Price: " + book.getPrice());

		System.out.print("Enter New Price: ");

		double newPrice =
				Double.parseDouble(scanner.nextLine());

		book.setPrice(newPrice);

		service.updateBook(book);

		System.out.println("Book updated successfully.");
	}

	// =========================================================
	// 6. DELETE BOOK
	// =========================================================

	private static void deleteBook() {

		System.out.println("\n--- Delete Book ---");

		System.out.print("Enter Book ID: ");

		Long id = Long.parseLong(scanner.nextLine());

		Book book = service.getBookById(id);

		if (book == null) {

			System.out.println("Book not found.");
			return;
		}

		System.out.println("Book: " + book.getTitle());

		System.out.print("Are you sure you want to delete? (Y/N): ");

		String choice = scanner.nextLine();

		if (choice.equalsIgnoreCase("Y")) {

			service.deleteBook(id);

			System.out.println("Book deleted successfully.");

		} else {

			System.out.println("Delete cancelled.");
		}
	}
}
