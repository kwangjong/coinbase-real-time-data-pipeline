FROM maven:latest
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN mvn package
CMD ["java", "-jar", "target/test-consumer-1.jar"]
