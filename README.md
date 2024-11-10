# Coffee Assessment Application

This is a Spring Boot application designed to manage coffee product orders and payments. 
It provides a set of REST API endpoints that allow users to check their payment status, manage orders, and track how much they owe for coffee products.


## Prerequisites

Before running the application, make sure you have the following installed:

- **Java 17** or higher (You can check your Java version with `java -version`)
- **Maven** (for compiling and managing dependencies)

## Build the Application

- `mvn clean install`: Installs dependencies and compiles the project, generating a `.jar` file in the `target` directory.
- `mvn clean package`: In addition to compiling the project, it packages the application into an executable `.jar` file for standalone use.

## Running the Application

- `mvn spring-boot:run`: Starts the application in development mode. By default, it will run on port 8080. You can change the port by modifying the `server.port` property in `src/main/resources/application.properties` if desired.

## Application Configuration

The application can be configured via the `application.properties` file located in `src/main/resources/`. Here you can change settings like the server port, database connection, logging level, etc. Example:

```properties
server.port=8081
logging.level.org.springframework=DEBUG
```

## Test Files Location

Unit tests are located in the `src/test/java` directory, organized similarly to the main source files. You can run the tests using the following Maven command:

- `mvn test`

## License