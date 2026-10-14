# Bookstore Management System

Traditional Spring MVC application using Spring Boot 4.1.1, Java 17, JSP/JSTL, JPA, MySQL 8, Maven, Bootstrap 5 and Spring Security.

## Run
1. Install JDK 17, Maven 3.6.3+ and MySQL 8.
2. Update `src/main/resources/application.properties` database username/password.
3. Run `database/bookstore.sql` once after Hibernate creates the tables, or start once with `mvn spring-boot:run`, stop, then execute the inserts.
4. Run `mvn clean test` and `mvn spring-boot:run`.
5. Open `http://localhost:8080/login`.

Users: `admin/admin123` (ADMIN) and `staff/staff123` (STAFF). Change these demo passwords before any real deployment.
