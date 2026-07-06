**Summary of Progress**

- **Day 1: Setup & Infrastructure**
  - Initialized Spring Boot ecosystem and configured H2 In-Memory Database for rapid prototyping without migration overhead.
- **Day 2: Domain Blueprint**
  - Modeled the conceptual structure for the core `Product` domain, identifying essential attributes for smart pantry management.
- **Days 3 & 4: Object-Relational Mapping (ORM) & Encapsulation**
  - Transformed the conceptual model into a concrete Java persistence class using JPA (`@Entity`, `@Id`, `@GeneratedValue`).
  - Enforced Object-Oriented best practices by encapsulating all domain attributes with `private` modifiers and standardizing access via Getters/Setters.
  - Established baseline data types (e.g., `LocalDate` for expiration tracking) preparing the database schema to automatically map objects to H2 relational tables.
- **Day 5: Web Layer & End-to-End Validation (Today!)**
  - Developed the Web/REST Layer by implementing `ProductController` with `@RestController` and `@RequestMapping`.
  - Applied Dependency Injection (`@Autowired`) to bridge the web controller layer seamlessly with `ProductRepository`.
  - Exposed HTTP endpoints for data retrieval (`@GetMapping` with `.findAll()`) and payload processing (`@PostMapping` with `@RequestBody` and `.save()`).
  - Configured `application.properties` to expose the interactive H2 Web Console (`/h2-console`) and validated automatic schema generation.
  - Successfully simulated a frontend client utilizing Postman to dispatch raw JSON payloads, persisting the first live record into the database with a status code of `200 OK`.
### 🚀 API Endpoints (CRUD)

The project now supports full CRUD operations for product management:

*   **`GET /products`**: Lists all products currently in the pantry.
*   **`POST /products`**: Registers a new product.
*   **`GET /products/{id}`**: Finds a specific product by its ID (Returns `200 OK` or `404 Not Found`).
*   **`PUT /products/{id}`**: Updates details or quantities of an existing product.
*   **`DELETE /products/{id}`**: Removes a product from the pantry (Returns `204 No Content`).

**🔧 Tech Stack Applied**
- Java 21+
- Spring Boot
- Spring Data JPA
- H2 Database
- Postman (API Testing Client)
