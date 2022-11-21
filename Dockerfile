FROM openjdk:17-jdk-slim-buster
EXPOSE 9002

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} historic-0.0.1-snapshot.jar
ENTRYPOINT ["java","-jar","/historic-0.0.1-snapshot.jar"]