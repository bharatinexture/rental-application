# Rental Application

## Overview

This project implements a simple tool rental application for a store like Home Depot that rents out large tools. It's a Java-based solution using Spring Boot, demonstrating backend functionalities without a user interface. The application processes rental transactions, generates rental agreements, and applies discounts as specified by the store's policies.

## Running the Application

To run this application, ensure you have Java 17 and Maven installed. Clone the repository and navigate into the project directory. Run the following command:

```bash
mvn spring-boot:run
```
The application will start and be accessible on http://localhost:8080, unless you have specified otherwise.
You could alternatively also use Postman to test the application.

## Testing
JUnit tests are included to ensure the correctness of the application. Tests cover various scenarios including validations for discount rates and rental days, as well as the correct generation of rental agreements with appropriate charges. <br>
In order to run the tests, enter the command: 
```bash 
mvn test 
```
It will print output of test case in the console
for example:
```Console
-----------------------------------Rental Agreement-----------------------------------------
Tool code: JAKR
Tool type: Jackhammer
Tool brand: Ridgid
Rental days: 4
Checkout date: 07/02/20
Due date: 07/06/20
Daily rental charge: $2.99
Charge days: 1
Pre-discount charge: $2.99
Discount percent: 50%
Discount amount: $1.50
Final charge: $1.49
----------------------------------------------------------------------------

```



