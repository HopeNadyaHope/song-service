FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
EXPOSE 8010
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} song-service.jar
ENTRYPOINT ["java","-jar","/song-service.jar"]