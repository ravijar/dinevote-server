# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /build

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Runtime environment
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the WAR file from the build stage
COPY --from=builder /build/target/DineVote-0.0.1-SNAPSHOT.war app.war

# Expose port 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.war"]
