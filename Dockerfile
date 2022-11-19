FROM openjdk:17-jdk-slim-buster
EXPOSE 9002

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]