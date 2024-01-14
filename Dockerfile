# FROM maven:3.8.2-jdk-8 # for Java 8
FROM maven:3.8.5-openjdk-17

WORKDIR /easy-rento-backend
COPY . .
RUN mvn clean install -X

CMD mvn spring-boot:run
