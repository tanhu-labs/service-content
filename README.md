# Service-Content Spring Boot Microservice

This is a Spring Boot microservice project called `service-content`. Below is an overview of the architectural layers, testing strategy, and YouTube video tutorials explaining the development process.

## Conceptual Design
The conceptual design of the project was prepared using [draw.io](https://app.diagrams.net/). You can check out the youtube video explaining the design process here:  
[Service-Content Conceptual Design](https://youtu.be/esIHPXQXFBE)

## Project Layers

The project is designed using a layered architecture consisting of three main layers:

### 1. Persistence Layer
The persistence layer handles the interaction with the database. It uses an H2 database for integration testing. In this layer, we store and retrieve data required by the application.  
For more details, watch the youtube video explaining the persistence layer and its integration tests here:  
[Persistence Layer & Integration Testing](https://youtu.be/UjsYXY4i99k)

### 2. Domain Layer
The domain layer contains the core business logic of the application. It defines the main models, entities, and operations necessary to carry out the business requirements. The logic is encapsulated in this layer and is tested using unit tests.  
To avoid the complexities of mocking, a fake adapter is used in place of real implementations to simplify testing and ensure that the domain logic is working correctly.  
You can watch the youtube video explaining the development and unit tests in the domain layer here:  
[Domain Layer & Unit Testing](https://youtu.be/W0rDlPMx5lI)

### 3. Application Layer
The application layer is responsible for handling requests, applying business logic, and coordinating between the domain and persistence layers. It contains the service classes and manages transaction boundaries.

During development, we used **JUnit 5** for **integration testing** in this layer. The **domain layer was simulated using mocks**, ensuring that the application layer remained isolated from other project layers. We utilized **TestRestTemplate** to send REST requests to the relevant endpoints and verify the expected behavior.

For more details, watch the youtube video explaining the development and testing of the application layer here:  
[Application Layer & Integration Testing](https://youtu.be/krppnNgl9V4)

## TDD Approach
This project follows the Test-Driven Development (TDD) methodology, ensuring that each component is tested thoroughly before implementation. The development process focuses on writing tests first, then building the required functionality, and finally refactoring the code to improve quality.

## Conclusion
This project aims to demonstrate how to structure a Spring Boot microservice using a layered architecture, TDD, and integration/unit tests. Through the YouTube videos, I explain each step of the development process, including designing, testing, and implementing business logic.