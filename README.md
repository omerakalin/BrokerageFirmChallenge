# Brokerage Firm Challenge

This project is a backend application designed for managing stock orders and customer assets in a brokerage firm. It implements various functionalities such as placing orders, listing orders, withdrawing and depositing money, and handling different asset types like 'TRY' for deposits and withdrawals.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Setup](#project-setup)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Contributing](#contributing)

## Features

- **User Management**: Customers can register, authenticate, and manage their accounts.
- **Stock Orders**: Customers can place buy or sell orders for stocks.
- **Order Status Management**: Orders have three possible statuses: PENDING, MATCHED, and CANCELED.
- **Asset Management**: Customers can deposit, withdraw, and manage their assets. The system supports the 'TRY' currency for deposits and withdrawals.
- **Real-time Transaction Updates**: The system ensures that customers' orders and assets are updated in real-time as transactions are made.

## Technologies Used

- **Java 17**: For implementing the backend services.
- **Spring Boot**: For building the RESTful API and handling the application logic.
- **Spring Security**: For securing the endpoints with JWT authentication.
- **H2 Database**: For managing the persistent data in a lightweight embedded database.
- **JPA (Hibernate)**: For database interaction and entity management.
- **Maven**: For dependency management and building the project.

## Project Setup

### Prerequisites

- Java 17
- Maven 3.x or above
- IntelliJ IDEA or another IDE of your choice


### API Endpoints
Authentication
POST /auth/login
Login with username and password.
Returns a JWT token for authentication.
Customer
GET /customers/{customerId}

Retrieve details for a specific customer by their ID.
POST /customers/register

Register a new customer.
Orders
POST /orders

Create a new stock order (BUY or SELL).
Request body should include asset name, size, price, and order side.
GET /orders/{orderId}

Get details of a specific order by its ID.
GET /orders/customer/{customerId}

Retrieve all orders for a specific customer.
DELETE /orders/{orderId}

Cancel an order by its ID.
Assets
GET /assets/{customerId}

Retrieve all assets for a specific customer.
POST /assets/deposit

Deposit an asset (e.g., 'TRY') to a customer's account.
POST /assets/withdraw

Withdraw an asset (e.g., 'TRY') from a customer's account.

## Database Schema

**ASSETS Table**
ID: BIGINT, GENERATED_STORED
ASSET_NAME: VARCHAR(255)
CUSTOMER_ID: BIGINT
SIZE: INTEGER
USABLE_SIZE: DOUBLE PRECISION
CURRENCY: VARCHAR(3)

**CUSTOMERS Table**
CUSTOMER_ID: BIGINT, GENERATED_STORED
EMAIL: VARCHAR(255)
NAME: VARCHAR(255)
PASSWORD: VARCHAR(255)
IBAN: VARCHAR(34)

**ORDERS Table**
ORDER_ID: BIGINT, GENERATED_STORED
ASSET_NAME: VARCHAR(255)
CREATE_DATE: TIMESTAMP
CUSTOMER_ID: BIGINT
ORDER_SIDE: ENUM('BUY', 'SELL')
PRICE: NUMERIC(38,2)
SIZE: INTEGER
STATUS: ENUM('CANCELED', 'MATCHED', 'PENDING')

