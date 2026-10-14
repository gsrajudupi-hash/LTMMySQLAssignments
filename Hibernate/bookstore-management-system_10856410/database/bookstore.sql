CREATE DATABASE IF NOT EXISTS bookstore_db; USE bookstore_db;
INSERT INTO authors(name,biography) VALUES ('Robert C. Martin','Software author'),('Joshua Bloch','Java author');
INSERT INTO categories(name,description) VALUES ('Software Engineering','Engineering books'),('Java','Java programming');
INSERT INTO books(title,isbn,price,stock_quantity,image_url,author_id,category_id) VALUES
('Clean Code','9780132350884',599.00,20,'https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=600&q=80',1,1),
('Effective Java','9780134685991',749.00,15,'https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=600&q=80',2,2);
INSERT INTO customers(name,email,phone,address) VALUES ('Aarav Sharma','aarav@example.com','9876543210','Hyderabad');
