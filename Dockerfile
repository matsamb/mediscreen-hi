FROM openjdk:17-jdk-slim-buster
EXPOSE 9002

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} historic.jar
ENTRYPOINT ["java","-jar","/historic.jar"]