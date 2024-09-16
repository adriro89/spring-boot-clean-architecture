# CRUD Microservice for Posts

This project is a microservice based on Java 22 that implements a basic CRUD for the `Post` entity, following the
principles of Clean Architecture. Tools such as Maven Wrapper, OpenAPI, Docker, and Docker Compose are used to manage
the project lifecycle and deployment.

## Project Structure

The project is organized into the following layers to adhere to Clean Architecture principles:

- **Application**:
    - **Mapper**: Contains the mappers that convert objects between different layers, such as from DTOs to domain
      entities and vice versa.
    - **Service**: Includes the service implementations.
    - **Usecase**: Contains the core business logic and use cases that coordinate operations between the `application`
      and `domain` layers.

- **Config**:
    - **Bean Configuration**: Defines the Spring bean configuration necessary for dependency injection and other
      application configuration aspects.

- **Domain**:
    - **Exception**: Contains custom exceptions specific to the domain and business logic.
    - **Model**: Represents the core domain objects, such as `Post`.
    - **Repository**: Defines the interfaces for repositories that are implemented in the `infrastructure` layer.
    - **Service**: Defines the interfaces for services that are implemented in the `application` layer.

- **Infrastructure**:
    - **Controller**: Defines the REST controller that handle HTTP requests and delegate business logic to the services
      in the `application` layer.
    - **Persistence**: Includes the persistence entities used by repositories to interact with the database, as well as
      the repository implementations.

## Prerequisites

Before you begin, ensure you have the following components installed:

- **Java 22**: The required version of Java to compile and run the project.
- **Docker**: To create images and containers.
- **Docker Compose**: To orchestrate containers.
- **Maven Wrapper**: Included in the project to handle dependencies and build tasks.

## Generating OpenAPI Files

The microservice uses OpenAPI to document and generate the API specification. To generate the OpenAPI files, follow
these steps:

1. Run the following command to clean and compile the project:

   ```bash
   ./mvnw clean compile
   ```
2. The OpenAPI files will be automatically generated in the **/target/generated-sources/openapi/src/main/java**
   directory as part of the compilation process.

## Building the Microservice

To build the project, use the command:

```bash
   ./mvnw clean install
   ```

This command will download all necessary dependencies, compile the code, and package the microservice into a JAR file.

## Running the Microservice

To run the microservice locally, you can use the following command:

```bash
   ./mvnw spring-boot:run
   ```

## Deploying with Docker

This project includes a Dockerfile and a docker-compose.yml file to facilitate the deployment of the microservice in a
Docker environment.

### Building the Docker Image

To build the Docker image for the microservice, use the following command:

```bash
   docker build -t springboot-clean-architecture:latest .
   ```

### Running with Docker Compose

Docker Compose orchestrates the necessary containers for the microservice to run. To deploy the microservice and its
dependencies, use:

```bash
   docker-compose up -d
   ```

This will bring up the containers defined in the docker-compose.yml file in the background.

## Testing

The project includes a comprehensive test suite that covers:

- **Unit Tests**: Validating the functionality of individual components in isolation.
- **Integration Tests**: Testing the interactions between multiple components, utilizing **Testcontainers** to provide a
  lightweight, disposable environment for testing with real databases and other services.
- **End-to-End (E2E) Tests**: Simulating real-world usage of the microservice by testing the complete flow of data and
  functionality across the system, also using **Testcontainers** for an authentic environment setup.

You can run the project's unit, integration, and E2E tests with the the following command:

```bash
   ./mvnw test
   ```

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

