# Build Stage
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml ./
COPY src ./src/
COPY .env ./

# Package the application
RUN mvn clean package -DskipTests

# Package Stage
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Copy the .env file from the build stage (if needed)
COPY --from=build /app/.env /app/.env

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
