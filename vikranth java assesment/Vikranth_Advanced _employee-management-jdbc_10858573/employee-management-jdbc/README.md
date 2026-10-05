# Employee Management JDBC Assessment

Java 17, Maven, JDBC and MySQL console application using Model-DAO-Service architecture.

## Setup
1. Use your existing `employee_management_system` database. If needed, compare it with `database/reference-schema.sql`.
2. Ensure referenced department rows exist before adding employees. Example department IDs 1, 2 and 3 are included in the reference script.
3. Edit `src/main/resources/db.properties` with your MySQL username/password. The URL must start exactly with `jdbc:mysql://`.
4. From the project folder run:

```bash
mvn clean compile
mvn exec:java
```

## Implemented requirements
- CRUD and primary-key lookup
- More than five PreparedStatement operations
- Parameterized salary query
- Join and aggregate query
- Two CallableStatement stored procedures
- Transaction with commit/rollback and audit entry
- Batch insert
- try-with-resources
- input validation and SQLState-based friendly errors

## Common fixes
- `No suitable driver`: run with Maven and keep the Connector/J dependency. Do not write `jdbc:mysql:db.url=...`; use `db.url=jdbc:mysql://localhost:3306/employee_management_system...`.
- Foreign-key violation when department is `2`: first verify `SELECT * FROM department WHERE department_id=2;` and insert that department if absent.
- Authentication failure: update `db.username` and `db.password`.
