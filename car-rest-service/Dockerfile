FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/car-rest-service-1.0.jar /usr/local/lib/car-rest-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/usr/local/lib/car-rest-service.jar"]