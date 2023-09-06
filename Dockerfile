FROM openjdk:17
WORKDIR /app
ADD ./target/service-acquisition-0.0.1-SNAPSHOT.jar /app/service-acquisition.jar
CMD ["java", "-Xmx768m", "-jar", "/app/service-acquisition.jar"]
