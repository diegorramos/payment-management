FROM openjdk:11
COPY ./receipt /receipt
WORKDIR /receipt
RUN apt-get update \
    && apt-get install -y maven \
    && mvn clean package -U
ENTRYPOINT [ "java", "-jar", "target/receipt.jar" ]
EXPOSE 8080