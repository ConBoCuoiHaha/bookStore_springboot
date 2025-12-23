# Spring Boot Migration Walkthrough - BookStoreVN

This document summarizes the successful migration of the `BookStoreVN` project from ASP.NET Core to Java Spring Boot.

## 🚀 Accomplishments

### 🟢 Backend (Java/Spring Boot)
- **Framework:** Migrated to Spring Boot 3 with Maven.
- **Database:** Maintained connection to the existing SQL Server database (`BookStoreVN`).
- **ORM:** Used Spring Data JPA for all database interactions.
- **Entities:** Translated all C# Models to JPA Entities (Product, Category, Subcategory, ApplicationUser, OrderHeader, OrderDetail, ShoppingCart, ProductReview).
- **Repositories:** Replaced `IUnitOfWork` pattern with Spring Data JPA Repositories.
- **Controllers:** Replicated logic for Admin (Category, Subcategory, Product, Order) and Customer (Home, Cart, Auth) areas.
- **Security:** Implemented Spring Security with custom `UserDetailsService` for authentication and role-based authorization (Admin/Customer).

### 🔵 Frontend (Thymeleaf/Bootstrap)
- **Template Engine:** Converted all Razor Views (`.cshtml`) to Thymeleaf (`.html`).
- **Layout:** Reused the premium design, CSS, and Bootstrap framework from the original project.
- **Dynamic Content:** Integrated Thymeleaf fragments and expressions for dynamic data rendering.
- **Admin Management:** Functional CRUD for Categories, Subcategories, and Products (including image uploads).
- **Customer Experience:** Functional home page with search, product details with reviews, and a complete shopping cart to checkout flow.

## 📂 Project Structure
```
Java-Springboot/
├── pom.xml
├── src/main/java/com/bookstorevn/
│   ├── BookstoreApplication.java
│   ├── config/ (Security, UserDetails)
│   ├── models/ (JPA Entities)
│   │   └── ViewModels/ (ProductVM, CartVM, etc.)
│   ├── repositories/ (Data JPA Interfaces)
│   └── controllers/ (Web Controllers)
└── src/main/resources/
    ├── application.properties
    ├── static/ (CSS, JS, Images from wwwroot)
    └── templates/ (Thymeleaf views)
        ├── layout/
        ├── admin/
        ├── customer/
        └── auth/
```

## 🛠️ How to Run
1.  **Database:** Ensure your SQL Server is running and the `BookStoreVN` database exists.
2.  **Configuration:** Update `src/main/resources/application.properties` if your database credentials differ.
3.  **Build:** Run `mvn clean install` in the project root.
4.  **Run:** Run `mvn spring-boot:run` or execute the `BookstoreApplication.java` from your IDE.
5.  **Access:** Open [http://localhost:8080](http://localhost:8080) in your browser.

## 📌 Testing Credentials
- **Admin:** (Use an existing account from `AspNetUsers` or register a new one and manually update the role to `ADMIN` in DB).
- **Customer:** Register a new account via the UI.

The migration is complete and ready for final verification!
