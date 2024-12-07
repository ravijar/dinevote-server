# Use an official OpenJDK runtime as the base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build artifact (WAR file) to the container
COPY target/DineVote-0.0.1-SNAPSHOT.war app.war

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.war"]
