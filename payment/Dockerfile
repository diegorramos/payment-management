FROM openjdk:11
COPY ./payment /payment
WORKDIR /payment
RUN apt-get update \
    && apt-get install -y maven \
    && mvn clean package -DskipTests=true -U
ENTRYPOINT [ "java", "-jar", "target/payment.jar" ]
EXPOSE 8080