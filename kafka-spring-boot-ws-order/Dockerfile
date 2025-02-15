FROM openjdk:21-jdk
ARG JAR_FILE=kafka-spring-boot-ws-order-infrastructure/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
