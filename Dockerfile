# Use the OpenJDK 21 base image to build the application
FROM openjdk:21-jdk-slim as builder

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and build files into the container
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./

# Download dependencies and build the application (without running tests)
RUN ./gradlew build -x test --no-daemon

# Copy the source code into the container
COPY . .

# Build the application (this will create the JAR file)
RUN ./gradlew build -x test

# Use a second stage to reduce the image size and use only the runtime dependencies
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder image into the runtime image
COPY --from=builder /app/build/libs/roast-me-now-*.jar /app/roast-me-now.jar

# Expose the port your app will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/roast-me-now.jar"]
