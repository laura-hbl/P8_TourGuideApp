<h1 align="center">TourGuide</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
</p>

TourGuide is a Spring Boot application of TripMaster's applications. Usable on mobile and pc, It allows you to :
- discover attractions near your location;
- acquire reward points for each attraction visited;
- obtain personalized travel offers with discounts on hotel stays and reductions on ticket prices for shows.

Following its success, TourGuide is faltering and suffers of poor performances.  This new version was developed with the aim of correcting problems and optimizing performance.

## Author

**Laura HABDUL**

## Prerequisites to run

- Java 1.8 JDK
- Gradle 6.3-all
- Docker

## Installing

1. Install **Java**: https://www.oracle.com/java/technologies/javase-downloads.html

2. Install **Gradle**: https://gradle.org/install/

3. Install Docker Desktop:
https://docs.docker.com/docker-for-windows/ or https://docs.docker.com/docker-for-mac/

## Technical Specifications

TourGuide application is composed of 4 microservices:

1. **TourGuide**

Used on port 8080, it’s the principal microservice of the application, as it communicates, using Feign Client, with the 3 other microservices (gps-microservice, rewards-microservice, tripDeals-microservice) to request informations. 
- Java 8
- Gradle 6.3-all
- Spring Boot 2.2.0

2. **gps-microservice**

Used on port 8081, it exposes 2 endpoints that allows to request the location of an user and the list of attractions.
- Java 8
- Gradle 6.3-all
- Spring Boot 2.2.0

3. **rewards-microservice**

Used on port 8082, it exposes 1 endpoint that permits to request the reward points acquired by the user, after visiting a tourist attraction.
- Java 8
- Gradle 6.3-all
- Spring Boot 2.2.0

4. **tripDeals-microservice**

Used on port 8083, it exposes 1 endpoint that permits to request trip deals by providers based on user's reward points and preferences.
- Java 8
- Gradle 6.3-all
- Spring Boot 2.2.0

## Run app

Gradle
```
gradle bootRun
```

## Docker deploiement

Use the **Dockerfile** on the package root:
- `docker build -t name of image .`
- `docker run -d -p name of image`

To deploy all TourGuide microservices, use the **docker-compose.yml** on the package root

- `docker-compose up -d`

TourGuide service depends on the 3 others services and will be launched after them.

## Testing

The app has unit tests and integration tests written. <br/>
You must launch `gradle test`

## Metrics
Through the class called TestPerformance, we are able to effectively monitor gpsUtil and rewardsCentral metrics over time, in minutes. these tests allow you to track all user location and calculate the rewards for each user. The number of users generated for testing can be easily adjusted via test.user.numbers in the application-test.properties file.
