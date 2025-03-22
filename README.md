## Service-Content Spring Boot Microservice

This is a Spring Boot microservice project called `service-content`. Below is an overview of the architectural layers, testing strategy, Docker integration, and YouTube video tutorials explaining the development process.

## 📌 YouTube Video Tutorials
🎥 [Service-Content Conceptual Design](https://youtu.be/esIHPXQXFBE)  
🎥 [Persistence Layer & Integration Testing](https://youtu.be/UjsYXY4i99k)  
🎥 [Domain Layer & Unit Testing](https://youtu.be/W0rDlPMx5lI)  
🎥 [Application Layer & Integration Testing](https://youtu.be/krppnNgl9V4)  
🎥 [Cucumber Acceptance Testing in Spring Boot | Full Coverage with BDD](https://youtu.be/myegSjyNRaA)  
🎥 [Deploying a Spring Boot Microservice with Docker](https://youtu.be/GvMGhhhHpY0)

## Conceptual Design
The conceptual design of the project was prepared using [draw.io](https://app.diagrams.net/). You can check out the YouTube video explaining the design process here:  
[Service-Content Conceptual Design](https://youtu.be/esIHPXQXFBE)

## Project Layers

The project is designed using a layered architecture consisting of three main layers:

### 1. Persistence Layer
The persistence layer handles the interaction with the database. It uses an H2 database for integration testing. In this layer, we store and retrieve data required by the application.  
For more details, watch the YouTube video explaining the persistence layer and its integration tests here:  
[Persistence Layer & Integration Testing](https://youtu.be/UjsYXY4i99k)

### 2. Domain Layer
The domain layer contains the core business logic of the application. It defines the main models, entities, and operations necessary to carry out the business requirements. The logic is encapsulated in this layer and is tested using unit tests.  
To avoid the complexities of mocking, a fake adapter is used in place of real implementations to simplify testing and ensure that the domain logic is working correctly.  
You can watch the YouTube video explaining the development and unit tests in the domain layer here:  
[Domain Layer & Unit Testing](https://youtu.be/W0rDlPMx5lI)

### 3. Application Layer
The application layer is responsible for handling requests, applying business logic, and coordinating between the domain and persistence layers. It contains the service classes and manages transaction boundaries.

During development, we used **JUnit 5** for **integration testing** in this layer. The **domain layer was simulated using mocks**, ensuring that the application layer remained isolated from other project layers. We utilized **TestRestTemplate** to send REST requests to the relevant endpoints and verify the expected behavior.

For more details, watch the YouTube video explaining the development and testing of the application layer here:  
[Application Layer & Integration Testing](https://youtu.be/krppnNgl9V4)

### 4. Acceptance Testing (BDD with Cucumber)
In addition to unit and integration tests, **Cucumber** was used to write **acceptance tests** following **Behavior-Driven Development (BDD)** principles. This ensures that the application meets all functional requirements from a business perspective.

In this project:
- **Cucumber acceptance tests** were written using **Gherkin language** to define scenarios in an easy-to-read format.
- **Full test coverage** was achieved by verifying all critical functionalities through structured acceptance tests.
- The tests validate that the microservice behaves correctly from end to end.

For a detailed explanation, watch the YouTube video covering the acceptance tests:  
[Cucumber Acceptance Testing in Spring Boot | Full Coverage with BDD](https://youtu.be/myegSjyNRaA)

## Docker Integration
This project has been containerized using **Docker** to make deployment easier and more scalable. Below is an overview of how Docker is used in this project:

### Steps to Containerize and Deploy
1. **Dockerfile**: A `Dockerfile` was created to package the Spring Boot application into a Docker image.
2. **Docker Compose**: A `docker-compose.yml` file was written to run the application in a containerized environment.
3. **Docker Hub**: The built Docker image was pushed to Docker Hub for easy distribution.
4. **Container Deployment**: The image was pulled from Docker Hub and run inside a container using Docker Compose.

For a complete guide on how the microservice was containerized and deployed, check out the YouTube video:  
[Deploying a Spring Boot Microservice with Docker](https://youtu.be/GvMGhhhHpY0)

## TDD Approach
This project follows the Test-Driven Development (TDD) methodology, ensuring that each component is tested thoroughly before implementation. The development process focuses on writing tests first, then building the required functionality, and finally refactoring the code to improve quality.

## Conclusion
This project demonstrates how to structure a Spring Boot microservice using a layered architecture, **TDD**, **BDD with Cucumber**, **Docker**, and **integration/unit tests**. Through the YouTube videos, I explain each step of the development process, including designing, testing, implementing business logic, and deploying with Docker.

