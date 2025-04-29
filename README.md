# expense-tracker
A Spring Boot–based personal expense tracker

# 📘 Expense Tracker - Developer Log

## 🗓️ Day 0: Project Setup

### ✅ Tasks Completed
- Created GitHub repo and cloned locally via SSH
- Bootstrapped Spring Boot project with structure:
  - Controller, Service, Repository, Model, DTO
- Added `/test` endpoint to verify Spring Boot works
- Wrote custom security config to expose `/test`
- Ran and verified app at `http://localhost:8080/test`

### 🧰 Tools Used
- IntelliJ IDEA
- GitHub + Git Bash
- Amazon Corretto JDK 11
- Spring Boot, H2, Thymeleaf

### 🔐 Config Highlights
- Disabled Spring Security for specific endpoints using Spring Security 6+ syntax

### 💡 Notes
- IntelliJ warned about deprecated `.csrf().disable()` style — fixed it using lambda-based config
- Learned about `SecurityFilterChain` bean

## 📅 Day 1: Database Design & DTO Implementation

### ✅ Objectives Completed

- Finalized entity classes: `Expense` and `Category`
- Established proper JPA relationships:
  - `@OneToMany` and `@ManyToOne` between `Category` and `Expense`
- Designed and implemented the `CategoryDto` class for clean data transfer
- Followed real-life software engineering practices:
  - Thoughtful database-first approach
  - Proper entity encapsulation
  - Added validation with annotations like `@NotBlank` and `@Size`

---

### 🧩 Entity Relationship Overview

**Category**
- `id` (Primary Key)
- `name` (Unique, Not Null)
- One-to-Many relationship with `Expense`

**Expense**
- `id` (Primary Key)
- `amount`, `description`, `date` (Not Null)
- Many-to-One relationship with `Category`

---

### 🛠️ Technologies Used

- **Spring Boot** (Entity & Configuration Management)
- **Lombok** for cleaner models
- **JPA / Hibernate** for ORM
- **H2 Database** (In-memory DB for local testing)
- **Jakarta Validation** annotations for input validation

---

### 📁 Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/expense/tracker/
│   │       ├── model/
│   │       │   ├── Category.java
│   │       │   └── Expense.java
│   │       └── dto/
│   │           └── CategoryDto.java
```

# 📄 Expense Tracker Backend — Updates Summary

This document tracks the incremental work done after the last commit.

---

## 🗓️ Day 2 — API Implementation & Security Setup

- Implemented **ExpenseController** with full CRUD endpoints.
- Integrated **Spring Security**:
  - Set up `SecurityConfig` with Basic Authentication.
  - Initialized default admin user in the database using `DataLoader`.
  - Implemented `UserService` for database authentication.
- Resolved API access authentication issues using Postman Basic Auth.

---

## 🗓️ Day 3 — Error Handling & Client-Side Response Structuring

- Refactored controller methods to return a consistent **ApiResponse** format.
- Added centralized `@ExceptionHandler` for `ResponseStatusException`.
- Improved client-side (Postman) visibility for success and error messages.
- Integrated server-side logging with **SLF4J Logger**.

---

## 🗓️ Day 4 — Code Quality & Testing

- Verified all API endpoints manually via Postman.
- Resolved Hibernate identifier issues during update operations.
- Cleaned and standardized all responses.
- Prepared the backend for future enhancements: Testing, Documentation, CI/CD setup.

---

## 🚀 Next Steps (Planned)

- Add **Unit Tests** and **Integration Tests**.
- Enhance **Swagger/OpenAPI** documentation.
- Implement **Pagination and Filtering** for listing APIs.
- Set up **Dockerization** and CI/CD pipelines.
- Integrate **Spring Boot Actuator** for health monitoring.

---

## 📌 Notes

- Database: **H2 In-Memory Database**.
- Authentication: **Spring Security** (Default user: `admin` / password: `password`).
- Tech Stack:
  - Spring Boot 3
  - Spring Data JPA
  - Lombok
  - ModelMapper
  - Swagger (OpenAPI 3)

---

