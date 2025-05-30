FROM eclipse-temurin:21-jdk

WORKDIR /lotto

COPY ./build/libs/*SNAPSHOT.jar ./lotto-number-generator-prod.jar

ENTRYPOINT ["java", "-jar", "./lotto-number-generator-prod.jar"]
