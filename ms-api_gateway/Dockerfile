# Etapa 1: Compilación (Builder)
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app
# Copiamos el pom y el source
COPY pom.xml .
COPY src ./src
# Compilamos saltando los tests para ir más rápido
RUN mvn clean package -DskipTests

# Etapa 2: Imagen Final (Runtime)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copiamos el .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
