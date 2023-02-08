FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application jar file to the container
COPY target/client-api.jar /app/clent-api.jar

# Set the command to run the jar file
CMD ["java", "-jar", "clent-api.jar"]