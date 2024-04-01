# Stage 1: Build the application with Maven
FROM  maven:3.9.5-eclipse-temurin-17-alpine AS build

WORKDIR /app
#
COPY pom.xml ./pom.xml
COPY src/ ./src

#RUN mvn clean package -DskipTests

#COPY --from=build /app/target/*.jar app.jar
#
COPY target/*.jar ./app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]