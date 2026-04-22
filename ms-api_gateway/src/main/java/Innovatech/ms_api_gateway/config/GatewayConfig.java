package Innovatech.ms_api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de rutas del API Gateway.
 * Enruta tráfico hacia los 4 microservicios internos con Circuit Breaker (Resilience4j).
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // --- MS Autenticación (puerto 8081) ---
                .route("ms-autenticacion", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/auth"))
                                .stripPrefix(0))
                        .uri("http://localhost:8081"))

                // --- MS Gestión de Proyectos (puerto 8082) ---
                .route("ms-gestion-proyectos", r -> r
                        .path("/api/proyectos/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("proyectosCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/proyectos"))
                                .stripPrefix(0)
                                .tokenRelay())
                        .uri("http://localhost:8082"))

                // --- MS Recursos y Colaboraciones (puerto 8083) ---
                .route("ms-recursos-colaboraciones", r -> r
                        .path("/api/recursos/**", "/api/asignaciones/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("recursosCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/recursos"))
                                .stripPrefix(0)
                                .tokenRelay())
                        .uri("http://localhost:8083"))

                // --- MS Analíticas (puerto 8084) ---
                .route("ms-analiticas", r -> r
                        .path("/api/analiticas/**", "/api/kpis/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("analiticasCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/analiticas"))
                                .stripPrefix(0)
                                .tokenRelay())
                        .uri("http://localhost:8084"))

                .build();
    }
}
