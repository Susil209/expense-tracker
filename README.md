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

