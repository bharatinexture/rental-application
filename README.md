# Rental Application

## Overview

This project implements a simple tool rental application for a store like Home Depot that rents out large tools. It's a Java-based solution using Spring Boot, demonstrating backend functionalities without a user interface. The application processes rental transactions, generates rental agreements, and applies discounts as specified by the store's policies.

## Technologies Used

- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web
- Flyway for database migrations
- MySQL as the database
- Lombok
- MapStruct for object mapping
- SpringDoc OpenAPI for API documentation
- Swagger Suite

## Running the Application

To run this application, ensure you have Java 17 and Maven installed. Clone the repository and navigate into the project directory. Run the following command:

```bash
mvn spring-boot:run
```
The application will start and be accessible on http://localhost:8080, unless you have specified otherwise.

## Interacting with the application

We have integrated Swagger into the app, making it easy for you to test it using a user-friendly UI.
<br>The link to the UI : http://localhost:8080/swagger-ui/index.html (change the port number as needed).

You could alternatively also use Postman to test the application.

## API Endpoints

- ### Checkout a Tool
  - #### POST 'http://localhost:8080/rental/checkout'
  - Request Body :
    ``` json
    {
        "toolCode":"CHNS",
        "rentalDayCount":10,
        "discountPercent":22,
        "checkOutDate":"2022-02-02"  
    }
     ```
  - Response Body:
    ```json
    {
        "id": 6,
        "toolCode": "CHNS",
        "toolType": "Chainsaw",
        "brand": "Stihl",
        "rentalDays": 10,
        "checkOutDate": "2022-02-02",
        "dueDate": "2024-02-12",
        "dailyRentalCharge": 1.49,
        "chargedDays": 7,
        "preDiscountCharge": 10.43,
        "discountPercent": 22,
        "discountAmount": 2.29,
        "finalCharge": 8.14
    }
    ```

## Database
The application uses three main entities: 
- Tool, 
- ToolTypes, and 
- RentalAgreement <br>
These are managed through a MySQL database, with migrations handled by Flyway.

## Testing
JUnit tests are included to ensure the correctness of the application. Tests cover various scenarios including validations for discount rates and rental days, as well as the correct generation of rental agreements with appropriate charges. <br>
In order to run the tests, enter the command: 
```bash 
mvn test 
```
in the app/IDE's terminal.



