FROM openjdk:8-jdk-alpine
ADD build/libs/gps-microservice.jar gps-microservice.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","gps-microservice.jar"]