FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/health-blog-platform-0.0.1-SNAPSHOT.jar /app/health-blog-platform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/health-blog-platform.jar"]
