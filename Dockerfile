# Use an official Maven image to build the project
FROM maven:3.8.1-jdk-11 as builder
WORKDIR /build
COPY src /build/src
COPY pom.xml /build/
RUN mvn package

# Use an official Java runtime image to run the built JAR
FROM openjdk:11-jre-slim
WORKDIR /app
# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/your-artifact.jar /app/
CMD ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
