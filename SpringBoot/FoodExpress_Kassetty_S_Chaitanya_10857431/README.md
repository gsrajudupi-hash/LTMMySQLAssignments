# FoodExpress – Spring Boot + MySQL

## Requirement
A REST API for customers, restaurants, menu items, delivery addresses and multi-item food orders. The server calculates order totals from database prices and saves orders transactionally.

## Requirements
- Java 17
- Maven 3.8+
- MySQL 8
- Postman

## Run
1. Create database (or allow the app to create it):
   ```sql
   CREATE DATABASE food_delivery_db;
   ```
2. Open `src/main/resources/application.properties` and replace `YOUR_MYSQL_PASSWORD`.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Base URL: `http://localhost:8080`

Hibernate creates/updates tables using the entity mappings.

## Main endpoints
- Customers: `/api/customers`
- Restaurants: `/api/restaurants`
- Food items: `/api/food-items`
- Addresses: `/api/addresses`
- Orders: `/api/orders`

See `postman/FoodExpress.postman_collection.json` for starter API requests.

## Sample request order
`POST /api/orders`
```json
{
  "customerId": 1,
  "restaurantId": 1,
  "addressId": 1,
  "items": [
    {"foodItemId": 1, "quantity": 2},
    {"foodItemId": 2, "quantity": 1}
  ]
}
```
