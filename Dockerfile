FROM eclipse-temurin:21-jdk

COPY ./build/libs/*SNAPSHOT.jar /lotto_number_generator.jar

ENTRYPOINT ["java", "-jar", "/lotto_number_generator.jar"]
