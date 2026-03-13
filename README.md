# GraphQL REST Gateway

A simple Spring Boot project that demonstrates how **GraphQL can act as a gateway for REST APIs**.

This project uses **Java, Spring Boot, Spring for GraphQL, and WebClient** to expose a GraphQL API that internally fetches data from REST endpoints.

## Project Idea

This application shows how GraphQL can sit on top of REST services and provide a cleaner, more flexible API to clients.

Instead of calling multiple REST endpoints directly, a client can call a single GraphQL endpoint and request only the data it needs.

## Tech Stack

- Java
- Spring Boot
- Spring for GraphQL
- Spring Web
- Spring WebFlux
- WebClient
- Lombok
- Maven

## Architecture

The current version is built as a single Spring Boot application for simplicity.

It contains:

- Mock REST endpoints
- A GraphQL layer
- A service layer using `WebClient`

### Flow

Client → GraphQL Query → GraphQL Resolver → Service Layer → WebClient → REST Endpoint → Response back to GraphQL Client

## Features

- Exposes GraphQL queries for user and order data
- Uses GraphQL as a gateway over REST APIs
- Uses `WebClient` for internal REST API consumption
- Demonstrates aggregation of multiple REST responses into a single GraphQL response
- Includes basic error handling for REST failures


REST Endpoints

These mock REST endpoints are used internally by the GraphQL gateway:

```text
GET /api/users/{id}

GET /api/orders/user/{userId}

```

Sample REST Response

```text
GET /api/users/1

{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com"
}
```

GET /api/orders/user/1

```text
[
  {
    "id": 101,
    "userId": 1,
    "product": "Laptop",
    "amount": 999.99
  },
  {
    "id": 102,
    "userId": 1,
    "product": "Mouse",
    "amount": 49.99
  }
]
```

# GraphQL Schema

```text
type Query {
  user(id: ID!): User
  userOrders(userId: ID!): [Order]
  userWithOrders(id: ID!): UserWithOrders
}

type User {
  id: ID
  name: String
  email: String
}

type Order {
  id: ID
  userId: ID
  product: String
  amount: Float
}

type UserWithOrders {
  id: ID
  name: String
  email: String
  orders: [Order]
}
```
GraphQL Queries
1. Get user by id
query {
  user(id: 1) {
    id
    name
    email
  }
}
2. Get orders by user id
query {
  userOrders(userId: 1) {
    id
    userId
    product
    amount
  }
}
3. Get user with orders
query {
  userWithOrders(id: 1) {
    id
    name
    email
    orders {
      id
      userId
      product
      amount
    }
  }
}
# How It Works

> The client sends a GraphQL query to /graphql

> The GraphQL resolver receives the request

> The resolver calls a service class

> The service class uses WebClient to call REST endpoints

> REST responses are mapped into Java model classes

> The GraphQL layer returns a structured response to the client


Running the Project
Prerequisites

Java 17+

Maven

Start the application
./mvnw spring-boot:run

Open:

http://localhost:8080/graphiql
