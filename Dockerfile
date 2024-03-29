# FROM maven:3.8.2-jdk-8 # for Java 8
FROM maven:3.8.5-openjdk-17

WORKDIR /recipie/createRecipe
COPY . .
RUN mvn clean package

CMD mvn spring-boot:run