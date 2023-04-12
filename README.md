# DevOps Microservices Demo

This repository contains a proof-of-concept (PoC) project demonstrating the implementation of a microservices architecture using SpringBoot, along with the integration of various DevOps tools and practices.

|  |
|---|
| ## Step-by-Step Guide |
| |
| This guide will walk you through running our POC with four Spring Boot applications: `app1`, `app2`, `api-gateway`, and `eureka-server`. There are two ways to run these applications: without Docker and with Docker. |
| |
| ### Option 1: Running the Applications without Docker |
| |
| 1. Ensure that you have Java 17 and Maven installed on your machine. |
| 2. Clone the repository: |
|    git clone https://github.com/sandyrai/devops-microservices-demo.git |
| |
| 3. Import the project into your favorite IDE (such as STS or IntelliJ). |
| |
| 4. Build and run each application separately: |
| |
| - For `app1` and `app2`: |
|   ``` |
|   mvn clean install |
|   mvn spring-boot:run |
|   ``` |
| - For `api-gateway` and `eureka-server`: |
|   ``` |
|   mvn clean install |
|   mvn spring-boot:run |
|   ``` |
| Alternatively, you can run the applications directly from your IDE by right-clicking the main class of each application and selecting "Run". |
| |
| ### Option 2: Running the Applications with Docker |
| |
| 1. Ensure that you have Docker and Docker Compose installed on your machine. |
| |
| 2. Clone the repository: |
|    git clone https://github.com/sandyrai/devops-microservices-demo.git |
| |
| 3. Build the Docker images for each application: |
| |
| 4. Run the Docker containers: |
| docker-compose up |


## Project Overview

The project consists of four main components::

1. **App1**: A Spring Boot microservice that performs CRUD operations.
2. **App2**: Another Spring Boot microservice that performs CRUD operations.
3. **API Gateway**: A Spring Cloud Gateway implementation that routes requests to App1 and App2 based on path patterns.
4. **Eureka Server**: A service discovery server that allows the microservices and API Gateway to discover and communicate with each other.

### Communication

- The **API Gateway** receives incoming requests and forwards them to the appropriate microservices (App1 and App2) based on path patterns.
- **App1** and **App2** register themselves with the **Eureka Server** for service discovery.
- The **API Gateways** also registers itself with the **Eureka Server** and queries it for the locations of App1 and App2.


The project also incorporates the following DevOps tools and practices::

- **GitHub**: Source code management and collaboration.
- **Docker**: Containerization of the applications.
- **Jenkins**: Continuous integration and deployment.
- **Kubernetes**: Orchestration of the containerized applications.
- **Monitoring**: A monitoring solution for the applications (e.g., Prometheus, Grafana).

## Getting Started

1. Clone this repository to your local machine.
2. Navigate to the individual component directories (App1, App2, API Gateway, Eureka Server) and follow the respective README files for instructions on building and running each application.

## Services Overview
- **App1** - A microservice running on port 8081 with the name "app1" and registered with the Eureka Server.
- **App2** - A microservice running on port 8082 with the name "app2" and registered with the Eureka Server.
- **Eureka Server** - A naming server running on port 8761, responsible for service registration and discovery.
- **API Gateway** - A gateway service running on port 8083 with the name "api-gateway", registered with the Eureka Server, and responsible for routing incoming requests to App1 and App2.

### Accessing Endpoints
Now, let's say App1 has an endpoint `/hello`, and App2 has an endpoint `/world`. With the given API Gateway configuration, you can access these endpoints through the API Gateway by appending `/app1` or `/app2` to the path:

- App1's `/hello` endpoint can be accessed at `http://localhost:8083/app1/hello`.
- App2's `/world` endpoint can be accessed at `http://localhost:8083/app2/world`.

The API Gateway will route the incoming requests to the appropriate microservices based on the path predicates defined in the configuration:

- Requests with paths starting with `/app1/` will be routed to App1 (http://localhost:8081).
- Requests with paths starting with `/app2/` will be routed to App2 (http://localhost:8082).

### Eureka Server Registration
All four services (App1, App2, Eureka Server, and API Gateway) are registered with the Eureka Server. This enables them to discover each other if necessary, and it also allows you to implement load balancing if you have multiple instances of any of the services running.

In this setup, client applications can send requests to the API Gateway, which will route the requests to the appropriate microservices (App1 or App2). The client applications don't need to know the specific addresses of the microservices, as the API Gateway handles the routing based on the path predicates.

## DevOps Pipeline Overview

1. **Source Code Management**: Code is stored in GitHub repositories.
2. **Containerization**: Dockerfiles define how applications are packaged into Docker containers.
3. **Docker Registry**: Docker images are pushed to Docker Hub or Amazon Elastic Container Registry (ECR).
4. **Continuous Integration (CI)**: Jenkins automates building, testing, and packaging applications.
5. **Continuous Deployment (CD)**: Kubernetes deploys applications in a production-like environment on AWS.
6. **CI/CD Integration**: Jenkins triggers deployment after successful builds and tests.
7. **Monitoring**: Implement a monitoring solution for applications and the Kubernetes cluster.
8. **Logging**: Configure a centralized logging system to aggregate logs from applications and the Kubernetes cluster.


## Dockerfile

Sample docker file :

```Dockerfile
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]
```


The `Dockerfile` in this repository is used to build a Docker container for the project. It starts from the `openjdk:17-jdk-slim` base image and copies the compiled JAR file into the container. The container exposes port `8081` and runs the JAR file using the command `java -jar /app.jar`.

To build and push the Docker image to Docker Hub, follow these steps:
1. Make sure you have an account on Docker Hub.
2. Open a terminal window and navigate to the root directory of the project.
3. Log in to Docker Hub by running the following command:
   docker login
4. Build the Docker image by running the following command:
   docker build -t <docker-hub-username>/<image-name>:<tag> 
   Replace `<docker-hub-username>` with your Docker Hub username, `<image-name>` with a name for your Docker image, and `<tag>` with a version or tag for your Docker image.
5. Push the Docker image to Docker Hub by running the following command:
   docker push <docker-hub-username>/<image-name>:<tag>
6. After the image has been pushed to Docker Hub, you can deploy the container to your environment of choice.

Note: Make sure to update the `ARG JAR_FILE` line in the `Dockerfile` to match the name of your compiled JAR file. 

For more information on how to use Docker, see the [Docker documentation](https://docs.docker.com/). 

## Docker Networking for Microservices

When deploying microservices using Docker containers, it is crucial to set up proper network communication between the containers. In this case, we have app1, app2, Eureka server, and API gateway containers that need to communicate with each other.

To set up the network configuration for these containers, follow these steps:

1. Create a Docker network:
    ```docker network create my-network```
   This command creates a new network named `my-network`.

2. Run the Eureka server container with the `--network my-network` flag and a custom hostname:
   ```docker run --network my-network --name eurekaServerContainer --hostname eureka-server -p 8761:8761 -it <eureka-server-image> -d```
    Replace `<eureka-server-image>` with the Docker image for your Eureka server.

3. Update the `application.properties` files of app1 and app2 to use the custom hostname for the Eureka server:
   eureka.client.serviceUrl.defaultZone=http://eureka-server:8761/eureka
   Rebuild the images for app1 and app2 after updating the `application.properties` files.

4. Run app1 and app2 containers with the `--network my-network` flag:
   docker run --network my-network --name app1Container -it <app1-image> -d
   docker run --network my-network --name app2Container -it <app2-image> -d
   
   Replace `<app1-image>` and `<app2-image>` with the Docker images for app1 and app2, respectively.

5. Run the API gateway container with the `--network my-network` flag:
   docker run --network my-network --name apigatewayContainer -p 8083:8083 -it <apigateway-image> -d
   
   Replace `<apigateway-image>` with the Docker image for your API gateway.
   Now, the containers are connected to the same network (`my-network`), and they can communicate with each other using their respective hostnames and ports.

For more information on Docker networking, see the [Docker networking documentation](https://docs.docker.com/network/).
 
## Continuous Integration with Jenkins

We have set up a Continuous Integration (CI) pipeline using Jenkins to automate the process of building, testing (if applicable), and pushing Docker images for our microservices. The Jenkins pipeline does the following:

1. Pulls the source code from GitHub whenever there is a new commit.
2. Builds the Docker images for our applications using the Dockerfiles.
3. Pushes the Docker images to Docker Hub.

### Custom Jenkins Docker Image

We have created a custom Jenkins Docker image that includes Java 17, Docker CLI, Docker Compose, and Maven. The Dockerfile for the custom Jenkins image can be found in the repository.

### Jenkins Pipeline

The Jenkinsfile in the repository defines the pipeline stages and steps, such as checking out the source code, updating Docker image tags, building Docker images, logging in to Docker Hub, and pushing Docker images.

To set up this CI pipeline, follow these steps:

1. Build the custom Jenkins Docker image using the provided Dockerfile.
2. Run a Jenkins container using the built image.
3. Install the required plugins in Jenkins (Git, Pipeline, and Docker Pipeline).
4. Create a new Jenkins Pipeline job.
5. In the Pipeline section, choose "Pipeline script from SCM" and provide the repository URL and the path to the Jenkinsfile.
6. Save the job configuration and trigger a new build.

The Jenkins pipeline will now automatically build, test (if applicable), and push Docker images to Docker Hub whenever there is a new commit in the GitHub repository.

For more information on setting up and configuring Jenkins, refer to the [official Jenkins documentation](https://www.jenkins.io/doc/).

## Contributing

Please follow the standard GitHub workflow for submitting pull requests to contribute to this project. Make sure to collaborate and communicate with other team members regularly, sharing updates and adjusting the plan as needed.

## License

This project is licensed under the [MIT License](LICENSE).
