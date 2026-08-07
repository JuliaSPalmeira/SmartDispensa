# 📦 SmartDispensa API

**SmartDispensa IntelliPantry** is an intelligent ecosystem built for automation, logistical control, and smart management of household or commercial inventory.

---

## 🚀 Summary of Progress (One-Month Roadmap)

### 🧱 Week 1: Setup & Infrastructure
- Initialized Spring Boot, configured Docker, and set up H2 In-Memory Database for rapid prototyping.

### 🥩 Week 2: Domain Modeling & Persistence
- Developed the `Product` domain entity, implemented JPA for ORM, and established encapsulation principles.

### 📦 Week 3: DTO Architecture & Data Validation
- Implemented `ProductController`, validation constraints (`@NotBlank`, `@Future`), and DTO design for secure data handling.

### 🛡️ Week 4: Business Intelligence & Robustness
- Engineered `ProductService` for real-time status tracking (`SAFE`/`EXPIRED`), configured custom JSON date formatting, and implemented global exception handling (`@RestControllerAdvice`).

---

### 🚀 API Endpoints
*   **`POST /products`**: Registers a new product.
*   **`GET /products`**: Lists all products.
*   **`GET /products/{id}`**: Retrieves a specific product.
*   **`PUT /products/{id}`**: Updates existing products.
*   **`DELETE /products/{id}`**: Removes a product.
*   **`GET /products/alerts/low-stock`**: Tracks low inventory.
*   **`GET /products/alerts/expiration`**: Identifies items nearing expiration.

---

### 🔧 Tech Stack
- **Backend:** Java 21 / Spring Boot 3.x / Spring Data JPA
- **Database:** H2 Database
- **DevOps:** Docker
- **Testing:** Postman / IntelliJ HTTP Client
