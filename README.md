# Product Pagination Backend

A Spring Boot backend application that efficiently serves a catalogue of **200,000 products** using **cursor-based pagination**.

## Features

- Cursor-based pagination
- Category filtering
- Sort by newest products first
- PostgreSQL database
- Batch insertion of 200,000 generated products
- Swagger/OpenAPI documentation
- Deployed on Render
- Database hosted on Neon

---

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL
- Maven
- Swagger (springdoc-openapi)
- Render
- Neon PostgreSQL

---

## Database Schema

Product

- id
- name
- category
- price
- quantity
- created_at
- updated_at

---

## Why Cursor Pagination?

Offset pagination becomes slower as the offset increases because the database has to skip many rows.

Cursor pagination:

- Fast on large datasets
- Uses indexed lookups
- Prevents duplicate/missing records while new products are inserted
- Scales well to hundreds of thousands of rows

---

## API Endpoints

### Seed Database

POST

```
/seeding
```

Seeds the database with **200,000 products**.

---

### Get Products

GET

```
/products
```

Parameters

| Parameter | Description |
|-----------|-------------|
| cursor | Last product id from previous page |
| limit | Number of products to fetch |
| category | Optional category filter |

Example

```
GET /products?limit=20
```

Next page

```
GET /products?cursor=189532&limit=20
```

Category filter

```
GET /products?category=FOOD&limit=20
```

Cursor + Category

```
GET /products?category=FOOD&cursor=189532&limit=20
```

---

## Deployment

Backend

Render

Database

Neon PostgreSQL

---

## Swagger

```
https://pagination-wc2r.onrender.com/swagger-ui/index.html
```

---

## Project Structure

```
Controller
Service
Repository
Entity
DTO
Seeder
Configuration
```

---

## Running Locally

Clone repository

```
git clone <repository-url>
```

Configure PostgreSQL

```
application.properties
```

Run

```
./mvnw spring-boot:run
```

Seed database

```
POST /seeding
```

---

## Design Decisions

- PostgreSQL chosen for efficient indexing.
- Batch insertion using PreparedStatement for fast seeding.
- Cursor pagination implemented instead of offset pagination.
- Filtering handled at the database level.
- RESTful APIs documented using Swagger.
