#FROM openjdk:8-jdk-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /src
COPY pom.xml pom.xml
RUN mvn -f pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /target/Lib10-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
