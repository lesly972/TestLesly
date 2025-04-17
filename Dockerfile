FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY . .
ENV LANG C.UTF-8
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java","-jar","app.jar"]