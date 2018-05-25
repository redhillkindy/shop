FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/stripe-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE cloud-dev
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring-boot.run.profiles=cloud-dev","-jar","/app.jar"]
