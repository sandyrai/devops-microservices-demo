# DevOps Microservices Demo

This repository contains a proof-of-concept (PoC) project demonstrating the implementation of a microservices architecture using Spring Boot, along with the integration of various DevOps tools and practices.

## Project Overview

The project consists of four main components:

1. **App1**: A Spring Boot microservice that performs CRUD operations.
2. **App2**: Another Spring Boot microservice that performs CRUD operations.
3. **API Gateway**: A Spring Cloud Gateway implementation that routes requests to App1 and App2 based on path patterns.
4. **Eureka Server**: A service discovery server that allows the microservices and API Gateway to discover and communicate with each other.

The project also incorporates the following DevOps tools and practices:

- **GitHub**: Source code management and collaboration.
- **Docker**: Containerization of the applications.
- **Jenkins**: Continuous integration and deployment.
- **Kubernetes**: Orchestration of the containerized applications.
- **Monitoring**: A monitoring solution for the applications (e.g., Prometheus, Grafana).

## Getting Started

1. Clone this repository to your local machine.
2. Navigate to the individual component directories (App1, App2, API Gateway, Eureka Server) and follow the respective README files for instructions on building and running each application.

## Contributing

Please follow the standard GitHub workflow for submitting pull requests to contribute to this project. Make sure to collaborate and communicate with other team members regularly, sharing updates and adjusting the plan as needed.

## License

This project is licensed under the [MIT License](LICENSE).
