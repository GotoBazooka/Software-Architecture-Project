## Microservices-Based Library Management System

# Introduction
The Library Management System is a microservices-based project designed to handle common operations in a library, such as managing book catalogs, borrowing and returning books, and notifying users about overdue books. This project demonstrates the use of Spring Boot, REST, Kafka, and Docker to build a scalable, maintainable, and modular architecture.
 
# Use Case Overview
# Key Features:
1.	Catalog Management: Add, update, and manage book records.
2.	Borrowing Management: Allow users to borrow and return books while ensuring availability.
3.	Notifications: Notify users about overdue books via asynchronous messages.
 
## Microservices Overview

# Catalog-Service:
•	Responsibilities: Manages the book catalog, including adding, updating, and fetching book details.
•	Database: H2 (in-memory) for storing book records.
•	Communication: Provides REST APIs for other services to fetch book data.

# Borrowing-Service:
	Responsibilities: Handles the borrowing and returning of books.
	Database: MySQL for persistent storage of borrowing records.
	Communication:
•	REST with Catalog-Service to check book availability.
•	Kafka producer to publish borrowing events.

# Notification-Service :
•	Responsibilities: Listens to borrowing events and sends notifications to users.
•	Database: H2 for logging notifications.
•	Communication: Kafka consumer to receive borrowing events.
 
## System Architecture

The architecture includes synchronous communication using REST and asynchronous communication using Kafka. Below is an interaction flow :

# User borrows a book:
•	Borrowing-Service checks availability with Catalog-Service via REST.
•	Borrowing-Service updates borrowing records and publishes an event to Kafka.
•	Notification-Service consumes the Kafka event and sends a notification to the user.

# Book return:
•	Borrowing-Service updates the records.
•	Notification-Service sends a confirmation notification via Kafka.
 
## Key Architectural Decisions

# Why Kafka:
•	Ensures decoupled communication between Borrowing-Service and Notification-Service.
•	Improves scalability and resilience.

# Why REST:
•	Simplifies synchronous requests where immediate responses are needed, such as availability checks.

# Database Choices:
•	H2 for Catalog and Notification services (lightweight and in-memory).
•	MySQL for Borrowing-Service (persistent storage for critical borrowing data).

# Docker:
•	Enables consistent environments for Kafka, Zookeeper, and microservices.
•	Simplifies setup and deployment.
 
How to Run the Application

# Prerequisites

•	Install Docker and Docker Compose.
•	Clone the repository:
git clone https://github.com/your-repo/library-management-system.git
cd library-management-system

# Steps

1.	Start Kafka and Zookeeper: Run the following command to start Kafka and Zookeeper:
docker-compose up -d
2.	Build and Run Services: Build the services using Maven:
mvn clean install
Start each service:
java -jar target/catalog-service.jar
java -jar target/borrowing-service.jar
java -jar target/notification-service.jar
3.	Access the APIs:
•	Catalog-Service: http://localhost:8081
•	Borrowing-Service: http://localhost:8082
•	Notification-Service: http://localhost:8083
4.	Test the Application: Use tools like Postman to test REST APIs or verify Kafka messages using a consumer tool.
 
# Docker Compose Configuration

The docker-compose.yml file includes the following services:
•	Kafka and Zookeeper for messaging.
•	Microservices with their respective configurations.

# Diagrams
see image folder

# Legend
see image folder

 
•	Technical Explanation of the Diagram:
The user interacts with the system through REST APIs exposed by Borrowing-Service and Catalog-Service. These services allow the user to view available books, borrow books, and return borrowed books.
When a user requests to borrow a book, Borrowing-Service communicates with Catalog-Service via REST to check the book's availability. Catalog-Service updates the book's status in its database (Catalog DB) to mark it as borrowed.
After recording the borrowing transaction in its own database (Borrowing DB), Borrowing-Service publishes an event to Kafka (Producer). Kafka relays this event to subscribed services, such as Notification-Service, through a specific topic (borrow-events).Notification-Service consumes Kafka messages (Consumer) to trigger actions, such as sending notifications to the user.These notifications can be stored in its local database (Notification DB) for tracking purposes.

 
# Contributions


MEMBER  / TASK	                /DETAILS
1	      / Catalog service       /	Develop REST APIs and H2 integration
2	      / Borrowing service     /	Handle REST APIs, Kafka producer setup.
3	      / Notification service  /	Kafka consumer and logging integration.








