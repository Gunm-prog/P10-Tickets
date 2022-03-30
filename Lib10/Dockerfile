#
# Build stage
# maven:3.6.3-jdk-8-openj9
#FROM maven:3.6.0-jdk-11-slim AS build
FROM maven:3.6.3-jdk-8-openj9 AS build
COPY src /src
COPY pom.xml pom.xml
#COPY /docker/ci/wait-for.sh /newman/wait-for.sh
RUN mvn -f pom.xml clean package
  
#
# Package stage
#
FROM maven:3.6.3-jdk-8-openj9
COPY --from=build /target/Lib10-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
COPY /docker/ci/wait-for.sh .
#CMD ["java"]
#CMD ["/usr/local/lib/app.jar"]
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
#CMD ["/wait-for.sh" , "[ENTER YOUR ENDPOINT HERE]" , "--strict" , "--timeout=300" , "--" , "YOUR REAL START COMMAND"]
#ENTRYPOINT ["/wait-for.sh", "db:3307", "--", "java", "-jar", "/usr/local/lib/app.jar"]
#ENTRYPOINT ["/wait-for.sh"]
