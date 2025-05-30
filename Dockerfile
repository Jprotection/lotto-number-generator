FROM eclipse-temurin:21-jdk

COPY ./build/libs/*SNAPSHOT.jar /lotto/lotto-number-generator-prod.jar

ENTRYPOINT ["java", "-jar", "/lotto/lotto-number-generator-prod.jar"]
