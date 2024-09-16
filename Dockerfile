FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/springboot-clean-architecture-0.0.1-SNAPSHOT.jar /app/springboot-clean-architecture-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "springboot-clean-architecture-0.0.1-SNAPSHOT.jar"]
