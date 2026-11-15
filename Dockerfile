FROM eclipse-temurin:17-jdk AS build

WORKDIR /workspace

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests --no-transfer-progress

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/target/dgap-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
