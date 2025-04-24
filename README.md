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
