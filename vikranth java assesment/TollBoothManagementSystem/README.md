# Toll Booth Management System

A complete console project for the Core Java 17 practical assignment.

## Concepts covered
- OOP, interfaces, encapsulation and constructors
- Access modifiers, polymorphism and method overriding
- `final` constants and immutable truck objects
- Collections with `ArrayList`
- Custom and standard exception handling
- Java 17 switch syntax
- JUnit 5 tests

## Requirements
- JDK 17+
- Maven 3.8+

## Run from an IDE
Import as a Maven project and run:
`com.vikranth.tollbooth.app.TollBoothApplication`

## Run from a terminal
```bash
mvn clean test
mvn exec:java
```

## Toll formula
`Toll = ($5 x axles) + ($10 x complete 500 kg units)`

Example: 5 axles and 12,500 kg = $25 + $250 = $275.
