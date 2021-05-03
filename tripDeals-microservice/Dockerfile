FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD build/libs/tripDeals-microservice.jar tripDeals-microservice.jar
ENTRYPOINT ["java","-jar","tripDeals-microservice.jar"]