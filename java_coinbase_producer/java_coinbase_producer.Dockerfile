FROM maven:3.8.6-openjdk-11-slim
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN mvn package
CMD ["java", "-jar", "target/coinbase-producer-1.jar"]
