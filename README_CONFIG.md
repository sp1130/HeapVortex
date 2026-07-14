## Config Module

The `config` package contains all application configuration classes required to initialize and manage the backend environment.

### Responsibilities

- Configure Cross-Origin Resource Sharing (CORS) to allow secure communication between the React frontend and Spring Boot backend.
- Define application-wide configuration settings.
- Configure WebSocket connections for real-time communication.
- Set up Swagger/OpenAPI documentation (if enabled).
- Configure JSON serialization and deserialization.
- Manage environment-specific properties.
- Register beans required across the application.
- Configure application startup settings.
- Support Docker deployment configurations.
- Centralize reusable application configurations.

### Example Configuration Classes

- `CorsConfig.java` – Configures CORS policies.
- `WebSocketConfig.java` – Enables WebSocket communication.
- `SecurityConfig.java` – Configures authentication and authorization.
- `SwaggerConfig.java` – Configures API documentation.
- `AppConfig.java` – Defines common application beans.

### Purpose

The Config module ensures that the backend services are properly initialized and provides a centralized place to manage application settings, security, networking, and other framework-level configurations.


