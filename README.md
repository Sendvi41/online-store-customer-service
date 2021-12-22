# Customer-service

Customer-service is a part of online-store project, that is based on microservices' architecture. Customer-service provides
API to store's customers to get access of functionality of online-store.

## 🚀 Getting Started

This section provides a high-level quick start guide

### Local Development

### Prerequisites

- [Docker Desktop](https://docs.docker.com/desktop/) 4.3.0
- [Java](https://www.oracle.com/java/technologies/) 17.0.1
- [Maven](https://maven.apache.org/) 3.6.3
- [PostgreSQL](https://www.postgresql.org/) 13.4 or higher
- [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/)

**Step 1. Set up environment variables**

Database

- ```CUSTOMER_USER```  - postgres user
- ```CUSTOMER_PASS```  - postgres password

RabbitMQ

- ```RABBITMQ_USER``` - rabbitMQ user
- ```RABBITMQ_PASS``` - rabbitMQ password

**Step 2. Configure DB**

You can configure DB on your local machine, or you can use Docker to run DB-instance

*Configure local on your local machine*

Create DB ```customerServiceDB``` on 5432 port

*Configure local using Docker*

Install Docker to launch environment for application

- Use the command ```docker-compose up``` in terminal
- You can also run the file```docker-compose.yml``` in project's folder

**Step 3. Build app**
> mvn compile

**Step 4. Run**

_Idea_:

- create SpringBoot configuration
- set up environment variables to configuration
- run

## ✨ Features

- Creating users
- Basket management
- Searching products
- Order management