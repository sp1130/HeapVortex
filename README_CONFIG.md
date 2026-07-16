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


## Controller Module

The `controller` package handles incoming HTTP requests and exposes REST API endpoints for the HeapVortex application. It acts as the communication layer between the frontend and the backend services.

### Responsibilities

- Define REST API endpoints.
- Handle HTTP requests (GET, POST, PUT, DELETE).
- Validate incoming request data.
- Receive heap dump upload requests.
- Invoke appropriate service layer methods.
- Return structured API responses.
- Handle exceptions and error responses.
- Support frontend communication through REST APIs.
- Manage request routing and endpoint mapping.

### Example Controller Classes

- `HeapController.java` – Handles heap dump upload and analysis requests.
- `AnalysisController.java` – Provides memory analysis results.
- `HealthController.java` – Checks application health status.
- `JmxController.java` – Exposes JVM monitoring endpoints.

### Request Flow

```
Frontend
    ↓
Controller
    ↓
Service
    ↓
Parser / JMX / Database
    ↓
Controller
    ↓
Frontend (JSON Response)
```

### Purpose

The Controller module serves as the entry point for all client requests. It processes incoming API calls, coordinates with the service layer, and returns appropriate responses to the frontend while maintaining a clean separation of concerns.


