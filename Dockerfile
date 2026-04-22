# ==============================================================================
# Dockerfile Multi-Stage para Spring Boot 3 - Innovatech Solutions
# Uso: docker build -t innovatech/<nombre-ms> --build-arg MODULE=<ms-dir> .
# Ejemplo: docker build -t innovatech/ms-gestion-proyectos --build-arg MODULE=ms-gestion_proyectos .
# ==============================================================================

# --- Stage 1: Build ---
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Argumento para seleccionar el microservicio a construir
ARG MODULE=ms-gestion_proyectos

# Copiar archivos del microservicio
COPY ${MODULE}/mvnw .
COPY ${MODULE}/.mvn .mvn
COPY ${MODULE}/pom.xml .

# Dar permisos de ejecución al Maven Wrapper
RUN chmod +x mvnw

# Descargar dependencias (cacheables si pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY ${MODULE}/src src

# Construir el JAR (sin ejecutar tests)
RUN ./mvnw package -DskipTests -B

# --- Stage 2: Runtime ---
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar al usuario no-root
USER appuser

# Exponer puerto (se configura por variable de entorno)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Ejecutar la aplicación con optimizaciones de contenedor
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
