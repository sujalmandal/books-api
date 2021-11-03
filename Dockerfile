FROM openjdk:8-jdk-alpine
MAINTAINER ss6sujal@gmail.com
COPY target/books-api-1.0.0.jar books-api-1.0.0.jar
ENTRYPOINT ["java","-jar","/books-api-1.0.0.jar"]