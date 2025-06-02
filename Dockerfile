FROM openjdk:21-jdk
LABEL authors="silva"
VOLUME /tmp
ARG JAR_FILE=target/tastemanager-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
