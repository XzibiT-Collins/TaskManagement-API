FROM ubuntu:latest
LABEL authors="XzibiT"

ENTRYPOINT ["top", "-b"]

#JDK VERSION
FROM openjdk:24-jdk-slim

#Volume for temp files
VOLUME /temp

#Get JAR FILE
ARG JAR_FILE=target/taskmanagement-0.0.1-SNAPSHOT.jar

#Copy jar file into image
COPY ${JAR_FILE} app.jar

#Port for spring application
EXPOSE 8080

#Entrypoint
ENTRYPOINT ["java", "-jar", "/app.jar"]